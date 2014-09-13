/*
 * Copyright (c) 2014.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pt21.afb

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan

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
