package controller

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.BASIC_TOWER_ATTACK_SPEED
import common.BASIC_TOWER_RADIUS_DEFAULT
import common.SCORPION_DEFAULT_SPEED
import common.TILE_SIZE
import model.enemy.Direction
import model.enemy.Scorpion
import model.tile.TileState
import model.tower.BasicTower
import model.tower.BasicTowerTexture

class GameController {
    private val map = Map()
    private val enemyController = EnemyController(map)
    private val projectileController = ProjectileController()
    private val towerController = TowerController(enemyController, projectileController)

    fun selectTile(x: Int, y: Int) {
        val tile = map.getTile(x.toFloat(), y.toFloat())
        towerController.removeSelect()
        if (tile.state is TileState.Empty) {
            val tower = BasicTower(
                tile.xIndex * TILE_SIZE.toFloat(),
                tile.yIndex * TILE_SIZE.toFloat(),
                BASIC_TOWER_RADIUS_DEFAULT,
                BASIC_TOWER_ATTACK_SPEED,
                BasicTowerTexture()
            )
            towerController.addTower(tower)
            tile.state = TileState.Occupied
            enemyController.addEnemy(Scorpion(0f, 0f, SCORPION_DEFAULT_SPEED, Direction.Right))
        } else if (tile.state is TileState.Occupied) {
            towerController.selectTower(tile.xIndex, tile.yIndex)
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
