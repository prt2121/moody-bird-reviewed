package com.pt21.afb

import android.os.{Handler, Bundle}
import android.view.View
import com.prt2121.glass.widget.SliderView

/**
 * Created by prt2121 on 9/11/14.
 */
class SplashScreenActivity extends SimpleActivity {

  lazy val delay = 3 * 1000
  val handler = new Handler()

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

  val startGame = new Runnable {
    override def run(): Unit = {
      next = classOf[MainActivity]
      startNextActivity()
    }
  }
}
