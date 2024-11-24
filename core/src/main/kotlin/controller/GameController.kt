package controller

import GameOverScreen
import TowerStatsPane
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import common.SCREEN_HEIGHT
import common.SCREEN_WIDTH
import common.TILE_SIZE
import common.minus
import kotlinx.coroutines.*
import level.EndlessLevel
import model.base.Base
import model.tile.TileState
import model.tower.BasicTower
import model.tower.BasicTowerTexture
import modifier.ModifierPane

class GameController {
    private val base = Base()
    private val map = Map(base)
    private val enemyController = EnemyController()
    private val projectileController = ProjectileController()
    private val towerController = TowerController(enemyController, projectileController)
    private val endlessLevel = EndlessLevel(map)
    private val gameOverScreen = GameOverScreen()
    private val modifierPane = ModifierPane()
    private val towerStatsPane = TowerStatsPane()
    private val bottomStage = Stage()
    private var wavesJob: Job? = null

    init {
        modifierPane.group.setPosition(SCREEN_WIDTH.toFloat() - modifierPane.group.width, 0f)
        towerStatsPane.group.setPosition(0f, 0f)
        bottomStage.addActor(modifierPane.group)
        bottomStage.addActor(towerStatsPane.group)
    }

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

    fun onClick(coordinates: Vector2) {
        if (towerController.selectedTower != null && modifierPane.isClickInside(coordinates)) {
            towerController.applyModifierToSelected(
                modifierPane.getClickedModifier(
                    coordinates - Vector2(
                        SCREEN_WIDTH.toFloat() - modifierPane.group.width,
                        0f
                    )
                )!!
            )
        } else
            selectTile(coordinates)
    }

    private fun selectTile(coordinates: Vector2) {
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
        enemyController.render(batch)
        towerController.render(batch)
        projectileController.render(batch)
        map.renderHealthBar(batch, Vector2(10f, SCREEN_HEIGHT - 10f - map.base.originalHeight))
        if (towerController.selectedTower != null) {
            towerController.selectedTower?.let {
                towerStatsPane.updateStats(it.damage, it.attackSpeed, it.radius)
            }
            bottomStage.draw()
        }
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
