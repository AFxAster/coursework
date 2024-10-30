package model.projectile

import com.badlogic.gdx.math.Vector2
import common.ARROW_DEFAULT_SPEED
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
    damage: Float,
    target: Enemy, // todo если используется только в startMove() то перенести в параметр метода
    texture: ArrowTexture
) : Projectile(x, y, damage, target, texture) {
//    val firstPoint = Vector2(
//        x + textureCenterX,
//        y + originalHeight
//    ) // todo возможно вращение нужно уже делать от этой точки а не от центра


    init {
        startMoving()
    }

    override fun startMoving() {
        movingJob = CoroutineScope(Dispatchers.Default).launch {
            var targetX = target.centerX
            var targetY = target.centerY
            while (target.isAlive && abs(targetY - centerY) + abs(targetX - centerX) > 10) {
                val direction = Vector2(targetX, targetY).sub(centerX, centerY)
//                firstPoint.rotateAroundDeg(Vector2(centerX, centerY), direction.angleDeg() + 180) // todo
                direction.nor().scl(ARROW_DEFAULT_SPEED)
                x += direction.x
                y += direction.y
//                firstPoint.add(direction)
                delay(RENDER_TIME)
                // todo почекать как все выглядит если сократить до считывания поля прямо из target
                targetX = target.centerX
                targetY = target.centerY
            }
            this@Arrow.isActive = false
            target.health -= damage // todo может использовать атомарные, потокобезопасные струкутуры
            println("end of moving $targetX $targetY")
            // todo dispose() вызывать или нет???
        }
    }
}
