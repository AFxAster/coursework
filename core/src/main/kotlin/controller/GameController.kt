package controller

import EndlessLevel
import GameOverScreen
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import common.SCREEN_HEIGHT
import common.TILE_SIZE
import kotlinx.coroutines.*
import model.base.Base
import model.tile.TileState
import model.tower.BasicTower
import model.tower.BasicTowerTexture

class GameController {
    private val base = Base()
    private val map = Map(base)
    private val enemyController = EnemyController()
    private val projectileController = ProjectileController()
    private val towerController = TowerController(enemyController, projectileController)
    private val endlessLevel = EndlessLevel(map)
    private val gameOverScreen = GameOverScreen()
    private var wavesJob: Job? = null

    fun startWaves() {
        wavesJob = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                endlessLevel.getEnemies().collect {
                    it.startMoving(map)
                    enemyController.addEnemy(it)
                }
                delay(10000)
            }
        }
    }

    fun selectTile(coordinates: Vector2) {
        val tile = map.getTile(coordinates)
        towerController.removeSelect()
        if (tile.state is TileState.Empty) {

            val tower = BasicTower(
                Vector2(tile.xIndex * TILE_SIZE.toFloat(), tile.yIndex * TILE_SIZE.toFloat()),
                BasicTowerTexture()
            )
            towerController.addTower(tower)
            tile.state = TileState.Occupied
        } else if (tile.state is TileState.Occupied) {
            towerController.selectTower(tile.xIndex, tile.yIndex)
        }
    }

    fun render(batch: SpriteBatch) {
        map.render(batch, Vector2(0f, 0f))
        towerController.render(batch)
        enemyController.render(batch)
        projectileController.render(batch)
        map.renderHealthBar(batch, Vector2(10f, SCREEN_HEIGHT - 10f - map.base.originalHeight))
        if (!base.isAlive) {
            wavesJob?.cancel()
            towerController.stopAttacking()
            enemyController.stopMoving()
            projectileController.stopMoving()
            gameOverScreen.render(batch, Vector2(0f, 0f))
            // todo и может менять инпутадаптер чтобы кликалось только на нужную кнопку
        }
    }

    fun dispose() {
        towerController.dispose()
    }
}
