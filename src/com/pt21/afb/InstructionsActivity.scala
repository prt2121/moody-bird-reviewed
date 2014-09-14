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
import android.os.Bundle
import com.google.android.glass.media.Sounds

/**
 * Created by prt2121 on 9/3/14.
 */
class InstructionsActivity extends SimpleActivity {

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    textView.setText("Squat or jump to fly")
  }

  override def onTap(): Boolean = {
    playSound(this, Sounds.TAP)
    val instruction = textView.getText.toString
    if(instruction.equalsIgnoreCase("Squat or jump to fly"))
       textView.setText("Tap to poop")
    if(instruction.equalsIgnoreCase("Tap to poop")) {
      //TODO work?
      Some(getIntent).map(intent => {
        if(intent.getBooleanExtra("newGame", false))
          startActivity(new Intent(InstructionsActivity.this, classOf[MainActivity]))
      })
      finish()
    }
    true
  }

}
