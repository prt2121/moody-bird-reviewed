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

package com.pt21.afb.helper

import android.media.AudioManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Animation, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.{Gdx, utils}
import com.google.android.glass.media.Sounds

/**
 * Created by pt2121 on 5/26/14.
 */

object AssetLoader {
  val prefs = Gdx.app.getPreferences("AngryFlappyBird")
  var background: TextureRegion = _
  var pipeHeadBigBottom: TextureRegion = _
  var pipeHeadBigTop: TextureRegion = _
  var pipeBig: TextureRegion = _
  var pipeHeadSmallBottom: TextureRegion = _
  var pipeHeadSmallTop: TextureRegion = _
  var pipeSmall: TextureRegion = _
  var brick: TextureRegion = _
  var boar0: TextureRegion = _
  var boar1: TextureRegion = _
  var boar2: TextureRegion = _
  var boars: utils.Array[TextureRegion] = _
  var boarAnimation: Animation = _
  var bird0: TextureRegion = _
  var bird1: TextureRegion = _
  var bird2: TextureRegion = _
  var bird3: TextureRegion = _
  var birds: utils.Array[TextureRegion] = _
  var birdAnimation: Animation = _
  var poop0: TextureRegion = _
  var poop1: TextureRegion = _
  var poop2: TextureRegion = _
  var poops: utils.Array[TextureRegion] = _
  var poopAnimation: Animation = _
  var texture: Texture = _
  //var skin: Skin = _
  //var smallSkin: Skin = _
  private var deadSound: Sound = _
  private var flapSound: Sound = _
  private var oinkSound: Sound = _
  private var dingSound: Sound = _
  private var mTracker: Tracker = _
  private var mAudioManager: AudioManager = _

//  var fontTexture: Texture = _
  var font: BitmapFont = _

  def init() {
    if (!prefs.contains("highScore")) prefs.putInteger("highScore", 0)
    texture = new Texture(Gdx.files.internal("images/atlas-glass.png"))
    texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest)
    deadSound = Gdx.audio.newSound(Gdx.files.internal("sounds/dead.mp3"))
    flapSound = Gdx.audio.newSound(Gdx.files.internal("sounds/flap.mp3"))
    dingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"))
    oinkSound = Gdx.audio.newSound(Gdx.files.internal("sounds/oink.mp3"))

    // font
    val generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/robotoRegular.ttf"))
    val parameter = new FreeTypeFontParameter()
    parameter.size = 34
    font = generator.generateFont(parameter)
    font.setColor(0f, 0f, 0, 1)
    font.setScale(1.0f, -1.0f)

    background = new TextureRegion(texture, 0, 12 + 16, 640, 360)
    pipeHeadBigBottom = new TextureRegion(texture, 0, 0, 64, 16)
    pipeHeadBigTop = new TextureRegion(texture, 0, 0, 64, 16)
    pipeBig = new TextureRegion(texture, 64, 0, 64, 16)
    pipeHeadSmallBottom = new TextureRegion(texture, 128, 0, 40, 16)
    pipeHeadSmallTop = new TextureRegion(texture, 128, 0, 40, 16)
    pipeSmall = new TextureRegion(texture, 168, 0, 40, 16)
    brick = new TextureRegion(texture, 0, 16, 360, 12)
    boar0 = new TextureRegion(texture, 360, 16 + 12 - 26, 26, 26)
    boar1 = new TextureRegion(texture, 360 + 26, 16 + 12 - 26, 26, 26)
    boar2 = new TextureRegion(texture, 360 + 26 + 26, 16 + 12 - 26, 26, 26)
    boars = new utils.Array(Array(boar0, boar1, boar2))
    boarAnimation = new Animation(0.2f, boars)
    bird0 = new TextureRegion(texture, 360 + 26 + 26 + 26, 16 + 12 - 24, 24, 24)
    bird1 = new TextureRegion(texture, 360 + 26 + 26 + 26 + 24, 16 + 12 - 24, 24, 24)
    bird2 = new TextureRegion(texture, 360 + 26 + 26 + 26 + 24 + 24, 16 + 12 - 24, 24, 24)
    bird3 = new TextureRegion(texture, 360 + 26 + 26 + 26 + 24 + 24 + 24, 16 + 12 - 24, 24, 24)
    birds = new utils.Array(Array(bird0, bird1, bird2, bird3))
    birdAnimation = new Animation(0.1f, birds)
    poop0 = new TextureRegion(texture, 360 + 26 + 26 + 26 + 24 + 24 + 24 + 24, 16 + 12 - 24, 24, 24)
    poop1 = new TextureRegion(texture, 360 + 26 + 26 + 26 + 24 + 24 + 24 + 24 + 24, 16 + 12 - 24, 24, 24)
    poop2 = new TextureRegion(texture, 360 + 26 + 26 + 26 + 24 + 24 + 24 + 24 + 24 + 24, 16 + 12 - 24, 24, 24)
    poops = new utils.Array(Array(poop0, poop1, poop2))
    poopAnimation = new Animation(0.2f, poops)

    background.flip(false, true)
    pipeHeadBigBottom.flip(false, true)
    pipeBig.flip(false, true)
    pipeHeadSmallBottom.flip(false, true)
    pipeSmall.flip(false, true)
    brick.flip(false, true)
    boar0.flip(false, true)
    boar1.flip(false, true)
    boar2.flip(false, true)
    boarAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG)
    bird0.flip(false, true)
    bird1.flip(false, true)
    bird2.flip(false, true)
    bird3.flip(false, true)
    birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG)
    poop0.flip(false, true)
    poop1.flip(false, true)
    poop2.flip(false, true)
    poopAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG)


  }

  def playDeadSound(): Unit = {
    deadSound.play()
    report("Game", "playDeadSound", "Died", None)
    ()
  }

  def report(category: String, action: String, label: String, value: Option[Long]): Unit = {
    if (mTracker != null)
      mTracker.send(category, action, label, value)
  }

  def playFlapSound(): Unit = {
    flapSound.play()
    ()
  }

  def playDingSound(): Unit = {
    dingSound.play()
    ()
  }

  def playOinkSound(): Unit = {
    oinkSound.play()
    ()
  }

  def highScore: Int = prefs.getInteger("highScore")

  def setHighScore(value: Int): Unit = {
    prefs.putInteger("highScore", value)
    prefs.flush()
    report("Game", "setHighScore", "High Score", Some(value.toLong))
  }

  def tracker(t: Tracker): Unit = mTracker = t

  def audioManager(a: AudioManager): Unit = mAudioManager = a

  def playTap(): Unit = mAudioManager.playSoundEffect(Sounds.TAP)

  def playDisallowed(): Unit = mAudioManager.playSoundEffect(Sounds.DISALLOWED)

  def playDismissed(): Unit = mAudioManager.playSoundEffect(Sounds.DISMISSED)

  def dispose(): Unit = {
    texture.dispose()
    deadSound.dispose()
    flapSound.dispose()
    dingSound.dispose()
    oinkSound.dispose()

    //font.dispose() somehow it's already disposed
  }
}