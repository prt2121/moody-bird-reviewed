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

import android.app.Activity
import android.content.Context
import android.media.AudioManager
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

  override def onGenericMotionEvent(event: MotionEvent): Boolean = {
    mGestureDetector.exists(_.onMotionEvent(event))
  }

  def playSound(context: Context, sound: Int): Unit = {
    val am = context.getSystemService(Context.AUDIO_SERVICE).asInstanceOf[AudioManager]
    am.playSoundEffect(sound)
  }

}
