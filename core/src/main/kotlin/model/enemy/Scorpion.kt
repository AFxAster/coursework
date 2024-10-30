package model.enemy

import com.badlogic.gdx.Gdx
import common.RENDER_TIME
import common.SCORPION_DEFAULT_DAMAGE
import common.SCORPION_DEFAULT_MAX_HEALTH
import common.SCORPION_DEFAULT_SPEED
import controller.Map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Scorpion(
    x: Float,
    y: Float,
    // todo не в конструктор отдавать, а в полях инитить дефолтным значением, потому что в конструктор всегда буду передавать дефолтную константу
    texture: ScorpionTexture
) : Enemy(x, y, texture) {

    override val maxHealth: Float = SCORPION_DEFAULT_MAX_HEALTH
    override var health: Float = maxHealth
        set(value) {
            texture.healthBar.status = value / maxHealth
            field = value
        }
    override var speed: Float = SCORPION_DEFAULT_SPEED
    override var damage: Float = SCORPION_DEFAULT_DAMAGE

    override fun startMoving(map: Map) {
        movingJob = CoroutineScope(Dispatchers.Default).launch {
            // todo пока жив еще наверное, если при смерти ОТМЕНЯТЬ эту корутину, то когда убираю этого крипа нужно
            //  добавить проверку что именно отменена корутина была, а не завершена
            while (0 <= lastPoint.x && lastPoint.x < Gdx.graphics.width && 0 <= lastPoint.y && lastPoint.y < Gdx.graphics.height) {
                val direction = map.getDirectionOnPath(lastPoint.x, lastPoint.y)
                moveTo(direction)
                delay(RENDER_TIME)
            }
            health = 0f
            map.base.health -= damage
            Gdx.app.postRunnable {
                dispose()
            }
        }
    }

    override fun moveTo(direction: Direction) {
        this.direction = direction
        when (direction) {
            is Direction.Left -> {
                x -= speed
                lastPoint.x = x + originalWidth
            }

            is Direction.Right -> {
                x += speed
                lastPoint.x = x
            }

            is Direction.Up -> {
                y += speed
                lastPoint.y = y
            }

            is Direction.Down -> {
                y -= speed
                lastPoint.y = y + originalHeight
            }
        }
    }

    override fun dispose() {
        texture.dispose()
    }
}
