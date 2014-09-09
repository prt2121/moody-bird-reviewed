package com.pt21.afb

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import com.google.android.glass.touchpad.{Gesture, GestureDetector}

/**
 * Created by prt2121 on 9/8/14.
 */
class BaseGlassActivity extends Activity {
  var mGestureDetector: Option[GestureDetector] = None


  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    mGestureDetector = Some(createGestureDetector(this))
  }

  override def onGenericMotionEvent(event: MotionEvent): Boolean = {
    mGestureDetector.exists(_.onMotionEvent(event))
  }

  def createGestureDetector(context: Context): GestureDetector = {
    new GestureDetector(context).setBaseListener(new GestureDetector.BaseListener() {
      override def onGesture(g: Gesture): Boolean = g match {
        case Gesture.TAP => onTap()
        case Gesture.TWO_TAP => onTwoTap()
        case Gesture.SWIPE_RIGHT => onSwipeRight()
        case Gesture.SWIPE_LEFT => onSwipeLeft()
        case Gesture.LONG_PRESS => onLongPress()
        case _ => false
      }
    })
  }

  protected def onTap(): Boolean = false

  protected def onTwoTap(): Boolean = false

  protected def onSwipeRight(): Boolean = false

  protected def onSwipeLeft(): Boolean = false

  protected def onLongPress(): Boolean = false

}
