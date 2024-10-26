package controller

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import model.enemy.Direction
import model.enemy.Scorpion
import model.tile.TileState
import model.tower.BasicTower

class GameController {
    private val map = Map()
    private val towerController = TowerController()
    private val enemyController = EnemyController(map)

    fun selectTile(x: Int, y: Int) {
        val tile = map.getTile(x, y)
        if (tile.state == TileState.Empty) {
            tile.state = TileState.Occupied
            towerController.addTower(BasicTower(tile.xIndex, tile.yIndex))
            enemyController.addEnemy(Scorpion(0f, 0f, Direction.Right))
        }
    }

    fun render(batch: SpriteBatch) {
        enemyController.render(batch)
        towerController.render(batch)
    }

    fun renderMap(batch: SpriteBatch) {
        map.render(batch, 0f, 0f)
    }

    fun dispose() {

    }
}
