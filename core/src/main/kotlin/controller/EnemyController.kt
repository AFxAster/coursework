package controller

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.SCREEN_HEIGHT
import common.SCREEN_WIDTH
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.enemy.Enemy
import kotlin.math.pow

class EnemyController(
    private val map: Map
) {
    private val enemies: MutableList<Enemy> = mutableListOf()

    fun addEnemy(enemy: Enemy) {
        enemies.add(enemy)
        startMoveTo(enemy)
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

    private fun startMoveTo(enemy: Enemy) {
        CoroutineScope(Dispatchers.Default).launch {
            enemy.run {
                while (0 <= lastPoint.x && lastPoint.x < SCREEN_WIDTH && 0 <= lastPoint.y && lastPoint.y < SCREEN_HEIGHT) {
                    val direction = map.getDirectionOnPath(lastPoint.x, lastPoint.y)
                    moveTo(direction)
                    delay(10)
                }
                dispose()
                enemies.remove(enemy)
            }
        }
    }

    fun render(batch: SpriteBatch) {
        enemies.forEach {
            it.render(batch, it.x, it.y)
        }
    }
}
