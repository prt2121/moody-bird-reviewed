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
