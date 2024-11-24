package controller

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.TILE_SIZE
import common.plus
import model.enemy.Enemy
import model.tower.Tower
import modifier.Modifier

class TowerController(
    private val enemyController: EnemyController,
    private val projectileController: ProjectileController
) {
    private val towers: MutableList<Tower> = mutableListOf()
    var selectedTower: Tower? = null

    fun addTower(tower: Tower) {
        towers.addSorted(tower)
    }

    fun render(batch: SpriteBatch) {
        selectedTower?.let {
            it.renderRadius(batch, it.coordinates)
        }
        towers.forEach {
            it.targets = getTargetsTo(it)
            it.startAttacking(projectileController)
            it.render(batch, it.coordinates)
        }
    }

    fun stopAttacking() {
        towers.forEach { it.stopAttacking() }
    }

    fun applyModifierToSelected(modifier: Modifier) {
        selectedTower?.applyModifier(modifier)
    }

    fun selectTower(xIndex: Int, yIndex: Int) {
        selectedTower =
            towers.find { it.coordinates.x == xIndex * TILE_SIZE.toFloat() && it.coordinates.y == yIndex * TILE_SIZE.toFloat() }
    }

    fun removeSelect() {
        selectedTower = null
    }

    private fun getTargetsTo(tower: Tower): List<Enemy> = tower.run {
        enemyController.getEnemiesInRadius(coordinates + textureCenter, radius)
    }

    fun dispose() {
        towers.forEach { it.dispose() }
    }

    fun clear() {
        stopAttacking()
        towers.clear()
        removeSelect()
    }
}

private fun MutableList<Tower>.addSorted(tower: Tower) {
    add(tower)
    sortByDescending { it.coordinates.y }
}
