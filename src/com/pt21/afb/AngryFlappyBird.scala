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

import android.media.AudioManager
import com.badlogic.gdx.Game
import com.pt21.afb.helper.{AssetLoader, GestureHandler, LinearAcceleration, Tracker}
import com.pt21.afb.screen._

class AngryFlappyBird(val ySensor: LinearAcceleration,
                      val gesture: Option[GestureHandler],
                      val tracker: Tracker,
                      val audioManager: AudioManager) extends Game {

  private var _gameScreen: GameScreen = _
  private var _world: GameWorld = _
  private var _renderer: GameRenderer = _

  override def create(): Unit = {
    _gameScreen = new GameScreen(this, gesture)
    _world = new GameWorld(this)
    _renderer = new GameRenderer(_world)
    setScreen(_gameScreen)

    // have to call if we dispose()
    AssetLoader.init()

    AssetLoader.tracker(tracker)
    AssetLoader.audioManager(audioManager)
  }

  override def dispose(): Unit = {
    super.dispose()
  }

  def gameScreen = _gameScreen

  def world = _world

  def renderer = _renderer
}