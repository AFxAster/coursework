package controller

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.TILE_SIZE
import model.enemy.Enemy
import model.tower.Tower

class TowerController(
    private val enemyController: EnemyController,
    private val projectileController: ProjectileController
) {
    private val towers: MutableList<Tower> = mutableListOf()
    private var selectedTower: Tower? = null

    fun addTower(tower: Tower) {
        towers.addSorted(tower)
    }

    fun render(batch: SpriteBatch) {
        selectedTower?.let {
            it.texture.renderRadius(batch, it.x, it.y)
        }
        towers.forEach {
            it.targets = getTargetsTo(it)
            it.startAttack(projectileController)
            it.render(batch, it.x, it.y)
        }
        projectileController.render(batch)
    }

    fun selectTower(xIndex: Int, yIndex: Int) {
        selectedTower = towers.find { it.x == xIndex * TILE_SIZE.toFloat() && it.y == yIndex * TILE_SIZE.toFloat() }
    }

    fun removeSelect() {
        selectedTower = null
    }

    private fun getTargetsTo(tower: Tower): List<Enemy> = tower.run {
        enemyController.getEnemiesInRadius(x + textureCenterX, y + textureCenterY, radius)
    }

    fun dispose() {
        towers.forEach { it.dispose() }
    }
}

private fun MutableList<Tower>.addSorted(tower: Tower) {
    add(tower)
    sortByDescending { it.y }
}
