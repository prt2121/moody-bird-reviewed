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

import android.content.{Context, Intent}
import android.graphics.Typeface
import android.os.{Bundle, Handler}
import android.view._
import android.widget.{RelativeLayout, TextView}
import com.prt2121.glass.widget.SliderView

/**
 * Created by prt2121 on 8/30/14.
 */
class LandingActivity extends SimpleActivity {

  lazy val delay = 4 * 1000
  lazy val handler = new Handler()
  lazy val slider = findViewById(R.id.slider).asInstanceOf[SliderView]
  var textView: TextView = null

  val startGame = new Runnable {
    override def run(): Unit = {
      val sharedPref = getSharedPreferences("AngryFlappyBird", Context.MODE_PRIVATE)
      val firstLaunch = sharedPref.getBoolean("firstLaunch", true)
      if (firstLaunch) {
        val intent = new Intent(LandingActivity.this, classOf[InstructionsActivity])
        intent.putExtra("newGame", true)
        startActivity(intent)
      } else {
        startActivity(new Intent(LandingActivity.this, classOf[MainActivity]))
      }
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
    textView = findViewById(R.id.tv_simple).asInstanceOf[TextView]
    textView.setText("Moody Bird")
    textView.setTypeface(textView.getTypeface, Typeface.BOLD)
  }

  override def buildView: View = {
    new TugView(this, R.layout.activity_simple)
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
    getMenuInflater.inflate(R.menu.menu_main, menu)
    true
  }

  //New game, Instructions, View score, View credits
  override def onOptionsItemSelected(item: MenuItem): Boolean = item.getItemId match {
    case R.id.menu_main_new_game => {
      startActivity(classOf[MainActivity])
      true
    }
    case R.id.menu_main_high_score => {
      startActivity(classOf[HighScoreActivity])
      true
    }
    case R.id.menu_main_instructions => {
      startActivity(classOf[InstructionsActivity])
      true
    }
    case R.id.menu_main_credits => {
      startActivity(classOf[CreditActivity])
      true
    }
    case _ => super.onOptionsItemSelected(item)
  }

  def startActivity[T](c: Class[T]): Unit = {
    new Handler().post(new Runnable() {
      override def run(): Unit = {
        startActivity(new Intent(LandingActivity.this, c))
      }
    })
  }

}

