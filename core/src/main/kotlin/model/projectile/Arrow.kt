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
    coordinates: Vector2,
    damage: Float,
    target: Enemy, // todo если используется только в startMove() то перенести в параметр метода
    texture: ArrowTexture
) : Projectile(coordinates, damage, target, texture) {
//    val firstPoint = Vector2(
//        x + textureCenterX,
//        y + originalHeight
//    ) // todo возможно вращение нужно уже делать от этой точки а не от центра

    override fun startMoving() {
        movingJob = CoroutineScope(Dispatchers.Default).launch {
            val targetCoordinates = target.center
//            var targetCoordinates.x = target.centerX
//            var targetCoordinates.y = target.centerY
            while (target.isAlive && abs(targetCoordinates.y - center.y) + abs(targetCoordinates.x - center.x) > 10) {
//                println("im work")
                val direction = Vector2(targetCoordinates.x, targetCoordinates.y).sub(center)
//                firstPoint.rotateAroundDeg(Vector2(centerX, centerY), direction.angleDeg() + 180) // todo
                direction.nor().scl(ARROW_DEFAULT_SPEED)
                coordinates.add(direction)
//                firstPoint.add(direction)
                delay(RENDER_TIME)
                // todo почекать как все выглядит если сократить до считывания поля прямо из target
                targetCoordinates.set(target.center)
            }
            this@Arrow.isActive = false
            target.hp -= damage // todo может использовать атомарные, потокобезопасные струкутуры
//            println("end of moving $targetCoordinates.x $targetCoordinates.y")
            // todo dispose() вызывать или нет???
        }
    }
}
