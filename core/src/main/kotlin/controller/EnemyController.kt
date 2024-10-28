package controller

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import model.enemy.Enemy
import kotlin.math.pow

class EnemyController(
    private val map: Map
) {
    private val enemies: MutableList<Enemy> = mutableListOf()

    fun addEnemy(enemy: Enemy) {
        enemies.add(enemy)
        enemy.startMove(map)
    }

    fun getEnemiesInRadius(centerX: Float, centerY: Float, radius: Float): List<Enemy> {
        val appropriateEnemies = mutableListOf<Enemy>()
        enemies.forEach {
            if ((it.centerX - centerX).pow(2) + (it.centerY - centerY).pow(2) <= radius.pow(2)) {
                appropriateEnemies.add(it)
            }
        }
        return appropriateEnemies
    }

    private fun update() {
        enemies.removeAll { it.movingJob!!.isCompleted }
    }

    fun render(batch: SpriteBatch) {
        update()
        enemies.forEach {
            it.render(batch, it.x, it.y)
        }
    }
}
