package model.enemy

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import common.*
import controller.Map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Scorpion(
    coordinates: Vector2,
    texture: ScorpionTexture,
    map: Map
) : Enemy(coordinates, texture, map) {

    override val maxHp: Float = SCORPION_DEFAULT_MAX_HP
    override var hp: Float = maxHp
        set(value) {
            if (value <= 0) {
                map.base.cash += CASH_FROM_ENEMY
                isAlive = false
                stopMoving()
            }

            texture.healthBar.status = value / maxHp
            field = value
        }
    override var speed: Float = SCORPION_DEFAULT_SPEED
    override var damage: Float = SCORPION_DEFAULT_DAMAGE

    override fun startMoving() {
        movingJob = CoroutineScope(Dispatchers.Default).launch {
            // todo еще стоит это условие цикла вынести в мапу
            while (lastPoint.x < Gdx.graphics.width && lastPoint.y < Gdx.graphics.height) {
                val direction = map.getDirectionOnPath(lastPoint)
                moveTo(direction)
                delay(RENDER_TIME)
            }
            map.base.health -= damage
            isAlive = false
            stopMoving()
            Gdx.app.postRunnable {
                dispose()
            }
        }
    }

    override fun moveTo(direction: Direction) {
        this.direction = direction
        when (direction) {
            is Direction.Left -> {
                coordinates.x -= speed
                lastPoint.x = coordinates.x + originalWidth
            }

            is Direction.Right -> {
                coordinates.x += speed
                lastPoint.x = coordinates.x
            }

            is Direction.Up -> {
                coordinates.y += speed
                lastPoint.y = coordinates.y
            }

            is Direction.Down -> {
                coordinates.y -= speed
                lastPoint.y = coordinates.y + originalHeight
            }
        }
    }

    override fun dispose() {
        texture.dispose()
    }
}
