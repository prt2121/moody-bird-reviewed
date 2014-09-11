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
import android.view._
import android.widget.AdapterView
import com.google.android.glass.app.Card
import com.google.android.glass.media.Sounds
import com.google.android.glass.touchpad.GestureDetector
import com.google.android.glass.widget.{CardBuilder, CardScrollAdapter, CardScrollView}

/**
 * Created by prt2121 on 8/30/14.
 */
class LandingActivity extends BaseGlassActivity {

  private var mScrollView: Option[CardScrollView] = None
  private var mView: Option[View] = None

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    mView = Some(buildView)

    mScrollView = Some(new CardScrollView(this))
    mScrollView.map { scrollView =>
      scrollView.setAdapter(new CardScrollAdapter() {
        override def getCount: Int = 1

        override def getPosition(item: scala.Any): Int =
          if (item == mView) 0
          else AdapterView.INVALID_POSITION

        override def getView(position: Int, convertView: View, parent: ViewGroup): View = mView.get

        override def getItem(position: Int): AnyRef = mView
      })

    }

    setContentView(mScrollView.get)
  }

  override def onResume(): Unit = {
    super.onResume()
    mScrollView.map(_.activate)
  }

  override def onPause(): Unit = {
    super.onPause()
    mScrollView.map(_.deactivate)
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

  override def onGenericMotionEvent(event: MotionEvent): Boolean =
    mGestureDetector.exists(_.onMotionEvent(event))

  def buildView: View = {
    try {
      val cardBuilderName = "com.google.android.glass.widget.CardBuilder"
      Class.forName(cardBuilderName)
      new CardBuilder(this, CardBuilder.Layout.TEXT)
        .addImage(R.drawable.background)
        .getView
    } catch {
      case e: Exception =>
        new Card(this)
          .setImageLayout(Card.ImageLayout.FULL)
          .addImage(R.drawable.background)
          .getView
    }
  }

  override def onTap(): Boolean = {
    playSound(this, Sounds.TAP)
    openOptionsMenu()
    true
  }

  override def onLongPress(): Boolean = {
    playSound(this, Sounds.TAP)
    openOptionsMenu()
    true
  }

}

