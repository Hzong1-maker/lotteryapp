package com.example.lotteryapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlin.random.Random
import android.widget.*

class LotteryResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery_result)
        val username = intent.getStringExtra("USERNAME") ?: ""
        val lotteryNumber = intent.getIntExtra("LOTTERY_NUMBER", -1)

        val firstPrize = Random.nextInt(0, 10)
        val secondPrize = Random.nextInt(0, 10)
        val thirdPrize = Random.nextInt(0, 10)

        val resultMessage = when (lotteryNumber) {
            firstPrize -> "You have successfully drawn the first prize"
            secondPrize -> "You have successfully drawn the second prize"
            thirdPrize -> "You have successfully drawn the third prize"
            else -> "You didn't win, please come again next time."
        }

        val notificationMessage = "Welcome，$username！$resultMessage"
        showNotification(notificationMessage)

        val displayButton: Button = findViewById(R.id.display)
        displayButton.setOnClickListener {
            val prizeType = when (lotteryNumber) {
                firstPrize -> 1
                secondPrize -> 2
                thirdPrize -> 3
                else -> 0
            }

            if (prizeType > 0) {
                val fragment = PrizeFragment.newInstance(prizeType)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            } else {
                Toast.makeText(this, "You did not win, no prizes displayed", Toast.LENGTH_SHORT).show()
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lottery_result, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.again -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.exit -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showNotification(message: String) {
        val notificationId = 102
        val channelId = "lottery_notification_channel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        resultTextView.text = message

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Lottery Notification",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = getColor(R.color.pink)
                enableVibration(true)
                description = "Lottery result notification"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_lottery)
            .setContentTitle("Result of lucky draw")
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        notificationManager.notify(notificationId, notification)
    }

}
