package model.projectile

import com.badlogic.gdx.math.Vector2
import common.ARROW_SPEED
import common.RENDER_TIME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.enemy.Enemy
import kotlin.math.abs

class Arrow(
    x: Float,
    y: Float,
    target: Enemy, // todo если используется только в startMove() то перенести в параметр метода
    texture: ArrowTexture
) : Projectile(x, y, target, texture) {
    val firstPoint = Vector2()

    //    override fun dispose() {
//        texture.dispose()
//        movingJob?.cancel()
//    }

    init {
        startMove()
    }

    override fun startMove() {
        movingJob = CoroutineScope(Dispatchers.Default).launch {
            var currentTarget = target ?: return@launch
            var targetX = currentTarget.centerX
            var targetY = currentTarget.centerY
            while (abs(targetY - centerY) + abs(targetX - centerX) > 10) {
                val direction = Vector2(targetX, targetY).sub(centerX, centerY).nor().scl(ARROW_SPEED)
                x += direction.x
                y += direction.y
                delay(RENDER_TIME)
                currentTarget =
                    target // todo почекать как все выглядит если сократить до считывания поля прямо из target
                targetX = currentTarget.centerX
                targetY = currentTarget.centerY
            }
            // todo dispose() вызывать или нет???
        }
    }
}
