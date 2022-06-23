package com.example.takeout.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class TalkFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val inflateView = View.inflate(context, R.layout.fragment_talk, null)
//        return inflateView
        val text= TextView(activity)
        text.setText("评论")
        text.setTextColor(Color.RED)
        text.gravity= Gravity.CENTER
        return text
    }
}