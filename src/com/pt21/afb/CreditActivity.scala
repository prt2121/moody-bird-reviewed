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

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.{View, ViewGroup}
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import com.google.android.glass.media.Sounds
import com.google.android.glass.widget.{CardBuilder, CardScrollAdapter, CardScrollView}

/**
 * Created by prt2121 on 9/13/14.
 */
class CreditActivity extends BaseGlassActivity {

  private var mScrollView: Option[CardScrollView] = None
  private var mView: List[View] = List[View]()

  val credits = List(
    new Tuple2("qubodup", "freesound.org/people/qubodup/sounds/174097"),
    new Tuple2("Adam_N", "freesound.org/people/Adam_N/sounds/164680"),
    new Tuple2("yottasounds", "freesound.org/people/yottasounds/sounds/176731"),
    new Tuple2("kilobolt", "kilobolt.com"),
    new Tuple2("Austin Andrews", "modernuiicons.com"),
    new Tuple2("libGDX", "libgdx.badlogicgames.com")
  )

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    mView = buildViews

    mScrollView = Some(new CardScrollView(this))
    mScrollView.map { scrollView =>
      scrollView.setOnItemClickListener(new OnItemClickListener {
        override def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long): Unit = {
          val am = CreditActivity.this.getSystemService(Context.AUDIO_SERVICE).asInstanceOf[AudioManager]
          am.playSoundEffect(Sounds.DISALLOWED)
        }
      })

      scrollView.setAdapter(new CardScrollAdapter() {
        override def getCount: Int = credits.length

        override def getPosition(item: scala.Any): Int =
          if (mView.contains(item)) mView.indexOf(item)
          else AdapterView.INVALID_POSITION

        override def getView(position: Int, convertView: View, parent: ViewGroup): View = mView(position)

        override def getItem(position: Int): AnyRef = mView(position)
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

  def buildViews: List[View] = {
    credits.foldLeft(List[View]()) {
      (vs, t) => {
        val builder = new CardBuilder(this, CardBuilder.Layout.TEXT)
        val text = new SpannableString(t._1 + '\n' + t._2)
        text.setSpan(new RelativeSizeSpan(1.0f), 0, t._1.length, 33)
        text.setSpan(new RelativeSizeSpan(0.7f), t._1.length, text.length, 33)
        builder.setText(text)
          .addImage(R.drawable.background)
        builder.getView :: vs
      }
    }
  }
}
