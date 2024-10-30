package controller

import GameOverScreen
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.SCREEN_HEIGHT
import common.TILE_SIZE
import model.base.Base
import model.base.BaseHealthBar
import model.enemy.Direction
import model.enemy.Scorpion
import model.enemy.ScorpionTexture
import model.tile.TileState
import model.tower.BasicTower
import model.tower.BasicTowerTexture

class GameController {
    private val base = Base(BaseHealthBar())
    private val map = Map(base)
    private val enemyController = EnemyController(map)
    private val projectileController = ProjectileController()
    private val towerController = TowerController(enemyController, projectileController)

    private val gameOverScreen = GameOverScreen()

    fun selectTile(x: Int, y: Int) {
        val tile = map.getTile(x.toFloat(), y.toFloat())
        towerController.removeSelect()
        if (tile.state is TileState.Empty) {
            val tower = BasicTower(
                tile.xIndex * TILE_SIZE.toFloat(),
                tile.yIndex * TILE_SIZE.toFloat(),
                BasicTowerTexture()
            )
            towerController.addTower(tower)
            tile.state = TileState.Occupied
            enemyController.addEnemy(Scorpion(0f, 0f, ScorpionTexture(Direction.Right)))
        } else if (tile.state is TileState.Occupied) {
            towerController.selectTower(tile.xIndex, tile.yIndex)
        }
    }

    fun render(batch: SpriteBatch) {
        map.render(batch, 0f, 0f)
        towerController.render(batch)
        enemyController.render(batch)
        projectileController.render(batch)
        map.renderHealthBar(batch, 10f, SCREEN_HEIGHT - 10f - map.base.originalHeight)
        if (!base.isAlive) {
            towerController.stopAttacking()
            enemyController.stopMoving()
            projectileController.stopMoving()
            gameOverScreen.render(batch, 0f, 0f)
            // todo и может менять инпутадаптер чтобы кликалось только на нужную кнопку
        }
    }

    fun dispose() {
        towerController.dispose()
    }
}
