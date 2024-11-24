package model.enemy

import com.badlogic.gdx.math.Vector2
import controller.Map
import kotlinx.coroutines.Job

abstract class Enemy(
    val coordinates: Vector2,
    val texture: EnemyTexture,
    protected val map: Map
) : EnemyTexture by texture {

    abstract val maxHp: Float
    abstract var hp: Float
    abstract var speed: Float
    abstract var damage: Float

    val center: Vector2
        get() = Vector2(coordinates.x + textureCenter.x, coordinates.y + textureCenter.y)

    val lastPoint = Vector2(coordinates)
    var isAlive: Boolean = true
    protected var movingJob: Job? = null

    protected abstract fun moveTo(direction: Direction)

    abstract fun startMoving()

    fun stopMoving() {
        movingJob?.cancel()
    }
}
