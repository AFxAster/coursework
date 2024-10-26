package controller

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.TILE_SIZE
import model.tower.Tower

class TowerController {
    private val towers: MutableList<Tower> = mutableListOf()

    fun addTower(tower: Tower) {
        towers.addSorted(tower)
    }

    fun render(batch: SpriteBatch) {
        towers.forEach {
            it.render(batch, it.xIndex * TILE_SIZE.toFloat(), it.yIndex * TILE_SIZE.toFloat())
        }
    }
}

private fun MutableList<Tower>.addSorted(tower: Tower) {
    add(tower)
    sortByDescending { it.yIndex }
}
