package controller

import GameOverScreen
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import common.*
import kotlinx.coroutines.*
import level.EndlessLevel
import model.base.Base
import model.base.CashPane
import model.tile.TileState
import model.tower.BasicTower
import model.tower.BasicTowerTexture
import model.tower.TowerStatsPane
import modifier.ModifierPane
import kotlin.math.pow
import kotlin.math.roundToInt

class GameController {
    private val cashPane = CashPane()
    private val base = Base(cashPane = cashPane)
    private val map = Map(base)
    private val enemyController = EnemyController()
    private val projectileController = ProjectileController()
    private val towerController = TowerController(enemyController, projectileController)
    private val endlessLevel = EndlessLevel(map)
    private val gameOverScreen = GameOverScreen()

    private val modifierPane = ModifierPane()
    private val towerStatsPane = TowerStatsPane()
    private val bottomStage = Stage()

    private val topStage = Stage()
    private var wavesJob: Job? = null

    init {
        modifierPane.group.setPosition(SCREEN_WIDTH.toFloat() - modifierPane.group.width, 0f)
        towerStatsPane.group.setPosition(0f, 0f)
        bottomStage.addActor(modifierPane.group)
        bottomStage.addActor(towerStatsPane.group)

        topStage.addActor(cashPane.group)
    }

    fun startWaves() {
        wavesJob = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                endlessLevel.getEnemies().collect {
                    it.startMoving()
                    enemyController.addEnemy(it)
                }
                delay(10000)
            }
        }
    }

    fun onClick(coordinates: Vector2) {
        if (towerController.selectedTower != null && modifierPane.isClickInside(coordinates)) {
            towerController.selectedTower?.let {
                val modifier = modifierPane.getClickedModifier(
                    coordinates - Vector2(
                        SCREEN_WIDTH.toFloat() - modifierPane.group.width,
                        0f
                    )
                )!!
                val cost = modifierPane.getCostOf(modifier)
                if (base.cash >= cost) {
                    base.cash -= cost
                    it.applyModifier(modifier)
                }
            }
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
        base.render(batch, Vector2(10f, SCREEN_HEIGHT - 10f - base.originalHeight))
        towerController.selectedTower?.let {
            towerStatsPane.updateStats(it.damage, it.attackSpeed, it.radius)
            modifierPane.updateCosts(
                (DAMAGE_MODIFIER_DEFAULT_COST * COST_MULTIPLIER.pow(it.damageLevel - 1)).roundToInt(),
                (ATTACK_SPEED_MODIFIER_DEFAULT_COST * COST_MULTIPLIER.pow(it.attackSpeedLevel - 1)).roundToInt(),
                (RANGE_MODIFIER_DEFAULT_COST * COST_MULTIPLIER.pow(it.radiusLevel - 1)).roundToInt(),
            )
            bottomStage.draw()
        }
        topStage.draw()
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
