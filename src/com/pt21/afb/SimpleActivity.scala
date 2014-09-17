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
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.glass.media.Sounds

/**
 * Created by prt2121 on 9/12/14.
 */
abstract class SimpleActivity extends BaseGlassActivity {

  var next: Class[_ <: Activity] = null

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(buildView)
  }

  def buildView: View

  //  def buildView(text : String):View = {
  //    val builder = new CardBuilder(this, CardBuilder.Layout.TEXT)
  //    builder.setText(text)
  //    builder.getView
  //  }

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