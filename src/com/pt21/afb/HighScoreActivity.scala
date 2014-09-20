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
import android.view.View
import com.google.android.glass.media.Sounds
import com.google.android.glass.widget.CardBuilder

/**
 * Created by prt2121 on 9/8/14.
 */
class HighScoreActivity extends SimpleActivity {

  override def buildView: View = {
    val sharedPref = getSharedPreferences("AngryFlappyBird", Context.MODE_PRIVATE)
    val highScore = sharedPref.getInt("highScore", 0)
    val builder = new CardBuilder(this, CardBuilder.Layout.TEXT)
    builder.setText("Your highest score is " + highScore.toString + ".")
           .addImage(R.drawable.background)
    new TugView(this, builder.getView)
  }

  override def onTap(): Boolean = {
    playSound(this, Sounds.DISALLOWED)
    true
  }
}
