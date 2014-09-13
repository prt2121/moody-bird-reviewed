package com.pt21.afb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.google.android.glass.media.Sounds

/**
 * Created by prt2121 on 9/12/14.
 */
class SimpleActivity extends BaseGlassActivity {

  var next: Class[_ <: Activity] = null
  var textView: TextView = null

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(new TugView(this, R.layout.activity_simple))
    textView = findViewById(R.id.tv_simple).asInstanceOf[TextView]
  }

  def nextActivity(activity: Class[Activity]): Unit = next = activity

  override def onLongPress(): Boolean = {
    onTap()
  }

  override def onTap(): Boolean = {
    playSound(this, Sounds.TAP)
    startNextActivity()
    true
  }

  def startNextActivity(): Unit = {
    if (next != null) {
      startActivity(new Intent(this, next).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
      finish()
    }
  }
}