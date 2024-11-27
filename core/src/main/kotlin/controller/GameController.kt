package controller

import GameInputAdapter
import GameOverScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import common.*
import kotlinx.coroutines.*
import level.EndlessLevel
import model.base.Base
import model.tile.TileState
import pane.CashPane
import pane.ModifierPane
import pane.TowerSelectPane
import pane.TowerStatsPane
import kotlin.math.pow
import kotlin.math.roundToInt

class GameController {
    private val cashPane = CashPane()
    private val base = Base(cashPane)
    private val map = Map(base)
    private val enemyController = EnemyController()
    private val projectileController = ProjectileController()
    private val towerController = TowerController(enemyController, projectileController)
    private val endlessLevel = EndlessLevel(map)
    private val gameOverScreen = GameOverScreen(this)
    private val inputAdapter = GameInputAdapter(this)

    private val modifierPane = ModifierPane()
    private val towerStatsPane = TowerStatsPane()
    private val bottomStage = Stage()

    private val towerSelectPane = TowerSelectPane()
    private var isTowerSelectPaneActive = false


    private val topStage = Stage()

    private var wavesJob: Job? = null

    init {
        modifierPane.group.setPosition(SCREEN_WIDTH.toFloat() - modifierPane.group.width, 0f)
        towerStatsPane.group.setPosition(0f, 0f)
        bottomStage.addActor(modifierPane.group)
        bottomStage.addActor(towerStatsPane.group)

        topStage.addActor(cashPane.group)

        newGame()
    }

    private fun startWaves() {
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
        map.removeSelect()
        if (towerController.selectedTower != null && modifierPane.isClickInside(coordinates)) {
            towerController.selectedTower?.let { selectedTower ->
                val modifier = modifierPane.getClickedModifier(coordinates)
                modifier?.let {
                    val cost = modifierPane.getCostOf(modifier)
                    if (base.cash >= cost) {
                        base.cash -= cost
                        selectedTower.applyModifier(modifier)
                    }
                }
            }
        } else if (isTowerSelectPaneActive && towerSelectPane.isClickInside(coordinates)) {
            val tower = towerSelectPane.getClickedTower(coordinates)
            tower?.let {
                val cost = towerSelectPane.getCostOf(tower)
                if (base.cash >= cost) {
                    base.cash -= cost
                    towerController.addTower(tower)
                    map.getTile(tower.coordinates).state = TileState.Occupied
                    isTowerSelectPaneActive = false
                }
            }
        } else
            selectTile(coordinates)
    }

    private fun selectTile(coordinates: Vector2) {
        val tile = map.getTile(coordinates)
        towerController.removeSelect()
        isTowerSelectPaneActive = false
        if (tile.state is TileState.Empty) {
            towerSelectPane.updateCoordinates(
                Vector2(
                    tile.xIndex * TILE_SIZE.toFloat(),
                    tile.yIndex * TILE_SIZE.toFloat()
                )
            )
            isTowerSelectPaneActive = true
            map.selectTile(coordinates)
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
        if (isTowerSelectPaneActive) {
            towerSelectPane.group.draw(batch, 1f)
        }
        if (!base.isAlive) {
            wavesJob?.cancel()
            towerController.stopAttacking()
            enemyController.stopMoving()
            projectileController.stopMoving()
            gameOverScreen.init()
            gameOverScreen.render(batch, Vector2(0f, 0f))
        }
    }

    fun newGame() {
        base.cash = INITIAL_CASH
        base.health = BASE_MAX_HP
        wavesJob?.cancel()
        map.reset()
        enemyController.clear()
        projectileController.clear()
        towerController.clear()
        Gdx.input.inputProcessor = inputAdapter
        startWaves()
    }

    fun dispose() {
        towerController.dispose()
        projectileController.dispose()
    }
}
