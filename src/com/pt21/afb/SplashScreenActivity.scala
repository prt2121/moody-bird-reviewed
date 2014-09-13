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

import android.os.{Bundle, Handler}
import android.view.View
import com.prt2121.glass.widget.SliderView

/**
 * Created by prt2121 on 9/11/14.
 */
class SplashScreenActivity extends SimpleActivity {

  lazy val delay = 3 * 1000
  val handler = new Handler()
  val startGame = new Runnable {
    override def run(): Unit = {
      next = classOf[MainActivity]
      startNextActivity()
    }
  }

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    textView.setText("Moody Bird")
    val slider = findViewById(R.id.slider).asInstanceOf[SliderView]
    slider.setVisibility(View.VISIBLE)
    slider.startProgress(delay)
    handler.postDelayed(startGame, delay)
  }

  override def onTap(): Boolean = {
    super.onTap()
    handler.removeCallbacks(startGame)
    true
  }
}
