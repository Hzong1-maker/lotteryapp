package com.example.lotteryapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class PrizeFragment : Fragment() {

    companion object {
        const val PRIZE_TYPE = "prize_type"

        fun newInstance(prizeType: Int): PrizeFragment {
            val fragment = PrizeFragment()
            val args = Bundle()
            args.putInt(PRIZE_TYPE, prizeType)
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_prize, container, false)
        val prizeImageView: ImageView = rootView.findViewById(R.id.prizeImageView)
        val prizeType = arguments?.getInt(PRIZE_TYPE)

        when (prizeType) {
            1 -> prizeImageView.setImageResource(R.drawable.first_award)
            2 -> prizeImageView.setImageResource(R.drawable.second_award)
            3 -> prizeImageView.setImageResource(R.drawable.third_award)
            else -> prizeImageView.setImageDrawable(null)
        }

        return rootView
    }
}
