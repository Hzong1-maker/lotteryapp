package com.example.lotteryapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val lotteryNumberEditText: EditText = findViewById(R.id.lotteryNumberEditText)
        val startButton: Button = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val lotteryNumber = lotteryNumberEditText.text.toString()

            if (lotteryNumber.isNotEmpty() && lotteryNumber.toInt() in 0..9) {
                val intent = Intent(this, LotteryResultActivity::class.java).apply {
                    putExtra("USERNAME", username)
                    putExtra("LOTTERY_NUMBER", lotteryNumber.toInt())
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter a valid draw number (0-9)", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
