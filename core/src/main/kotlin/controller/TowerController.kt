package controller

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.TILE_SIZE
import model.enemy.Enemy
import model.tower.Tower

class TowerController(
    private val enemyController: EnemyController
) {
    private val towers: MutableList<Tower> = mutableListOf()

    fun addTower(tower: Tower) {
        towers.addSorted(tower)
    }

    fun render(batch: SpriteBatch) {
        towers.forEach {
            it.targets = getTargetsTo(it)
            it.render(batch, it.xIndex * TILE_SIZE.toFloat(), it.yIndex * TILE_SIZE.toFloat())
        }
    }

    fun showRadiusOn(xIndex: Int, yIndex: Int) {
        towers.find { it.xIndex == xIndex && it.yIndex == yIndex }?.showRadius = true
    }

    fun hideRadii() {
        towers.forEach { it.showRadius = false }
    }

    private fun getTargetsTo(tower: Tower): List<Enemy> = tower.run {
        enemyController.getEnemiesInRadius(xIndex * TILE_SIZE + centerX, yIndex * TILE_SIZE + centerY, radius)
    }

    fun dispose() {
        towers.forEach { it.dispose() }
    }
}

private fun MutableList<Tower>.addSorted(tower: Tower) {
    add(tower)
    sortByDescending { it.yIndex }
}
