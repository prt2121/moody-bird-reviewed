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
import android.view.{LayoutInflater, MotionEvent, View, ViewGroup}
import com.google.android.glass.widget.{CardScrollAdapter, CardScrollView}

/**
 * Created by prt2121 on 9/12/14.
 */
class TugView(context: Context, view: View) extends CardScrollView(context) {
  val mContentView = view
  setAdapter(new SingleCardAdapter())
  activate()

  def this(context: Context, layoutResId: Int) = this(context, LayoutInflater.from(context).inflate(layoutResId, null))

  override def dispatchGenericFocusedEvent(event: MotionEvent): Boolean = {
    super.dispatchGenericFocusedEvent(event)
    false
  }

  private class SingleCardAdapter extends CardScrollAdapter {
    override def getPosition(item: Object) = 0

    override def getCount = 1

    override def getItem(position: Int) = mContentView

    override def getView(position: Int, recycleView: View, parent: ViewGroup) = mContentView
  }

}
