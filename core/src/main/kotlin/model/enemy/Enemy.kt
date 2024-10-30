package model.enemy

import com.badlogic.gdx.math.Vector2
import controller.Map
import kotlinx.coroutines.Job

abstract class Enemy(
    var x: Float, // todo на Vector2 поменять??
    var y: Float,
    val texture: EnemyTexture
) : EnemyTexture by texture {

    abstract val maxHealth: Float
    abstract var health: Float
    abstract var speed: Float
    abstract var damage: Float

    val centerX: Float
        get() = x + textureCenterX
    val centerY: Float
        get() = y + textureCenterY

    val lastPoint = Vector2(x, y)
    val isAlive: Boolean
        get() = health > 0
    protected var movingJob: Job? = null

    protected abstract fun moveTo(direction: Direction)

    abstract fun startMoving(map: Map)

    fun stopMoving() {
        movingJob?.cancel()
    }
}
