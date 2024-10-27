package controller

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.BASIC_TOWER_RADIUS_DEFAULT
import model.enemy.Direction
import model.enemy.Scorpion
import model.tile.TileState
import model.tower.BasicTower
import model.tower.BasicTowerTexture

class GameController {
    private val map = Map()
    private val enemyController = EnemyController(map)
    private val towerController = TowerController(enemyController)

    fun selectTile(x: Int, y: Int) {
        val tile = map.getTile(x, y)
        towerController.hideRadii()
        if (tile.state is TileState.Empty) {
            val tower = BasicTower(
                tile.xIndex,
                tile.yIndex,
                BASIC_TOWER_RADIUS_DEFAULT,
                BasicTowerTexture()
            )
            towerController.addTower(tower)
            tile.state = TileState.Occupied
            enemyController.addEnemy(Scorpion(0f, 0f, Direction.Right))
        } else if (tile.state is TileState.Occupied) {
            towerController.showRadiusOn(tile.xIndex, tile.yIndex)
        }
    }

    fun render(batch: SpriteBatch) {
        towerController.render(batch)
        enemyController.render(batch)
    }

    fun renderMap(batch: SpriteBatch) {
        map.render(batch, 0f, 0f)
    }

    fun dispose() {
        towerController.dispose()
    }
}
