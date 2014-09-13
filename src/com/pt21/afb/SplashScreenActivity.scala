package com.pt21.afb

import android.os.Bundle

/**
 * Created by prt2121 on 9/11/14.
 */
class SplashScreenActivity extends SimpleActivity {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    next = classOf[LandingActivity]
  }

}
