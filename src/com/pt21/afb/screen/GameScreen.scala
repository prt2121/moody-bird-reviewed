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

package com.pt21.afb.screen

import com.badlogic.gdx.{Gdx, Screen}
import com.pt21.afb.helper.{AssetLoader, GestureHandler}
import com.pt21.afb.{AngryFlappyBird, GameState}


/**
 * Created by pt2121 on 6/1/14.
 */
class GameScreen(val game: AngryFlappyBird, val gesture: Option[GestureHandler]) extends Screen {

  private var runTime: Float = _

  private var shouldDispose = false

  override def render(delta: Float): Unit = {
    runTime = runTime + delta
    game.world.update(delta)
    game.renderer.render(runTime)

    if(shouldDispose) {
      // have to call on the right thread
      AssetLoader.dispose()
    }
  }

  override def hide(): Unit = {}

  override def resize(width: Int, height: Int): Unit = {}

  override def dispose(): Unit = {
  }

  override def pause(): Unit = {}

  override def show(): Unit = {
    gesture.map({ controller =>
      controller.onTap(() => {
        game.world.state() match {
          case GameState.GameOver => {
            AssetLoader.playTap()
            game.world.restart()
          }
          case GameState.HighScore => {
            AssetLoader.playTap()
            game.world.restart()
          }
          case GameState.Running => game.world.bird.poop()
          case GameState.Ready =>
        }
        true
      })
      controller.onSwipeForward(() => {
        false
      })
      controller.onSwipeBackward(() => {
        false
      })
      controller.onSwipeDown(() => {
        AssetLoader.playDismissed()
        shouldDispose = true
        true
      })
    })
    ()
  }

  override def resume(): Unit = {}
}
