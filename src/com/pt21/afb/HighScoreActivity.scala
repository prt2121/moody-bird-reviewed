package com.pt21.afb

import android.content.Context
import android.os.Bundle
import android.text.{SpannableString, Spannable}
import android.text.style.RelativeSizeSpan
import android.view.{MotionEvent, ViewGroup, View}
import android.widget.AdapterView
import com.google.android.glass.widget.{CardBuilder, CardScrollAdapter, CardScrollView}

/**
 * Created by prt2121 on 9/8/14.
 */
class HighScoreActivity extends SimpleActivity {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    val sharedPref = getSharedPreferences("AngryFlappyBird", Context.MODE_PRIVATE)
    val highScore = sharedPref.getInt("highScore", 0)
    val prefix = "Your highest score is\n"
    val text = new SpannableString(prefix + highScore.toString)
    text.setSpan(new RelativeSizeSpan(0.8f), 0, prefix.length, 33) //33 Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    text.setSpan(new RelativeSizeSpan(2.0f), prefix.length, text.length, 33)
    textView.setText(text)
  }

}
