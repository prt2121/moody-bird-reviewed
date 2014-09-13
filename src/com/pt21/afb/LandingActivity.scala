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

import android.content.Intent
import android.graphics.Typeface
import android.os.{Bundle, Handler}
import android.view._
import android.widget.RelativeLayout
import com.prt2121.glass.widget.SliderView
import com.pt21.afb.helper.AssetLoader

/**
 * Created by prt2121 on 8/30/14.
 */
class LandingActivity extends SimpleActivity {

  lazy val delay = 3 * 1000
  lazy val handler = new Handler()
  lazy val slider = findViewById(R.id.slider).asInstanceOf[SliderView]
  val startGame = new Runnable {
    override def run(): Unit = {
      startActivity(new Intent(LandingActivity.this, classOf[MainActivity]))
    }
  }

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    if (savedInstanceState == null) {
      slider.setVisibility(View.VISIBLE)
      slider.startProgress(delay)
      handler.postDelayed(startGame, delay)
    }
    findViewById(R.id.layout_simple).asInstanceOf[RelativeLayout].setBackground(getResources.getDrawable(R.drawable.background))
    textView.setText("Moody Bird")
    textView.setTypeface(textView.getTypeface, Typeface.BOLD)
  }

  override def onTap(): Boolean = {
    super.onTap()
    slider.setVisibility(View.GONE)
    handler.removeCallbacks(startGame)
    openOptionsMenu()
    true
  }

  override def onPause(): Unit = {
    super.onPause()
    slider.setVisibility(View.GONE)
    handler.removeCallbacks(startGame)
  }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    getMenuInflater().inflate(R.menu.menu_main, menu)
    true
  }

  //New game, Instructions, View score, View credits
  override def onOptionsItemSelected(item: MenuItem): Boolean = item.getItemId match {
    case R.id.menu_main_new_game => {
      startActivity(new Intent(LandingActivity.this, classOf[MainActivity]))
      true
    }
    case R.id.menu_main_high_score => {
      startActivity(new Intent(LandingActivity.this, classOf[HighScoreActivity]))
      true
    }
    case R.id.menu_main_instructions => {
      startActivity(new Intent(LandingActivity.this, classOf[InstructionsActivity]))
      true
    }
    case R.id.menu_main_credit => println("Credit"); true
    case _ => super.onOptionsItemSelected(item)
  }

}

