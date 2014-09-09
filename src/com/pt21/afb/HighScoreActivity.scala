package com.pt21.afb

import android.content.Context
import android.os.Bundle
import android.view.{ViewGroup, View}
import android.widget.AdapterView
import com.google.android.glass.widget.{CardBuilder, CardScrollAdapter, CardScrollView}

/**
 * Created by prt2121 on 9/8/14.
 */
class HighScoreActivity extends BaseGlassActivity {

  private var mScrollView: Option[CardScrollView] = None
  private var mView: Option[View] = None

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)

    val sharedPref = getSharedPreferences("AngryFlappyBird", Context.MODE_PRIVATE)
    val highScore = sharedPref.getInt("highScore", 0)

    mView = Some(buildViews(highScore))
    mScrollView = Some(new CardScrollView(this))
    mScrollView.map { scrollView =>
      scrollView.setAdapter(new CardScrollAdapter() {
        override def getCount: Int = 1

        override def getPosition(item: scala.Any): Int =
          if (mView.exists(_.equals(item))) 0
          else AdapterView.INVALID_POSITION

        override def getView(position: Int, convertView: View, parent: ViewGroup): View = mView.get

        override def getItem(position: Int): AnyRef = mView.get
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

  def buildViews(highScore : Int): View = {
    new CardBuilder(this, CardBuilder.Layout.TEXT)
      .addImage(R.drawable.background)
      .setText("Your highest score is " + highScore.toString + ".")
      .getView
  }


}
