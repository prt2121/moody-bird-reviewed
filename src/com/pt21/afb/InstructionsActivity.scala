package com.pt21.afb

import android.app.Activity
import android.os.Bundle
import android.view._
import android.widget.AdapterView
import com.google.android.glass.widget.{CardBuilder, CardScrollAdapter, CardScrollView}

/**
 * Created by prt2121 on 9/3/14.
 */
class InstructionsActivity extends Activity {

  private var mScrollView: Option[CardScrollView] = None
  private var mView: List[View] = List[View]()

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    mView = buildViews

    mScrollView = Some(new CardScrollView(this) {
      override def dispatchGenericFocusedEvent(event: MotionEvent): Boolean = {
        super.dispatchGenericFocusedEvent(event)
        false
      }
    })
    mScrollView.map { scrollView =>
      scrollView.setAdapter(new CardScrollAdapter() {
        override def getCount: Int = 2

        override def getPosition(item: scala.Any): Int =
          if (mView.contains(item)) mView.indexOf(item)
          else AdapterView.INVALID_POSITION

        override def getView(position: Int, convertView: View, parent: ViewGroup): View = mView(position)

        override def getItem(position: Int): AnyRef = mView(position)
      })

    }

    setContentView(mScrollView.get)
  }

  def buildViews: List[View] = {
    val views: List[View] = List[View]()
    val v1 = new CardBuilder(this, CardBuilder.Layout.TEXT)
      .addImage(R.drawable.background)
      .setText("Squat or jump to fly")
      .getView
    val v2 = new CardBuilder(this, CardBuilder.Layout.TEXT)
      .addImage(R.drawable.background)
      .setText("Tap to poop")
      .getView
    v1 :: v2 :: views
  }

  override def onResume(): Unit = {
    super.onResume()
    mScrollView.map(_.activate)
  }

  override def onPause(): Unit = {
    super.onPause()
    mScrollView.map(_.deactivate)
  }

}
