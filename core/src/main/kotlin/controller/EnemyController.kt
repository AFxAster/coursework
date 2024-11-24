package controller

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import model.enemy.Enemy
import kotlin.math.pow

class EnemyController {
    private val enemies: MutableList<Enemy> = mutableListOf()

    fun addEnemy(enemy: Enemy) {
        enemies.add(enemy)
    }

    fun getEnemiesInRadius(center: Vector2, radius: Float): List<Enemy> {
        val appropriateEnemies = mutableListOf<Enemy>()
        enemies.forEach {
            if ((it.center.x - center.x).pow(2) + (it.center.y - center.y).pow(2) <= radius.pow(2)) {
                appropriateEnemies.add(it)
            }
        }
        return appropriateEnemies
    }

    fun render(batch: SpriteBatch) {
        update()
        enemies.forEach {
            it.render(batch, it.coordinates)
        }
    }

    private fun update() {
        enemies.removeAll {
            !it.isAlive
        }
    }

    fun stopMoving() {
        enemies.forEach { it.stopMoving() }
    }

    fun clear() {
        stopMoving()
        enemies.clear()
    }
}
