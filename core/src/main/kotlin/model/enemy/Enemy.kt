package model.enemy

import com.badlogic.gdx.math.Vector2
import common.Texture
import controller.Map
import kotlinx.coroutines.Job

abstract class Enemy(
    var x: Float, // todo на Vector2 поменять??
    var y: Float,
    var speed: Float,
    open var direction: Direction
) : Texture {

    val centerX: Float
        get() = x + textureCenterX
    val centerY: Float
        get() = y + textureCenterY

    val lastPoint = Vector2(x, y)
    var isAlive = true
    var movingJob: Job? = null

    protected abstract fun moveTo(direction: Direction)
    abstract fun startMove(map: Map)
}
