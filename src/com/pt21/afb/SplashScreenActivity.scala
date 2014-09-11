package com.pt21.afb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.{ViewGroup, View}
import android.widget.AdapterView
import com.google.android.glass.app.Card
import com.google.android.glass.media.Sounds
import com.google.android.glass.widget.{CardBuilder, CardScrollAdapter, CardScrollView}

/**
 * Created by prt2121 on 9/11/14.
 */
class SplashScreenActivity extends BaseGlassActivity {

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
    startLandingActivity()
    true
  }

  override def onLongPress(): Boolean = {
    onTap()
  }

  def startLandingActivity():Unit = {
    startActivity(new Intent(this, classOf[LandingActivity]).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    finish()
  }
}
