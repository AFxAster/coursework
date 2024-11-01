package model.projectile

import com.badlogic.gdx.math.Vector2
import kotlinx.coroutines.Job
import model.enemy.Enemy


abstract class Projectile(
    val coordinates: Vector2,
    val damage: Float,
    val target: Enemy,
    val texture: ProjectileTexture
) : ProjectileTexture by texture {

    val center: Vector2
        get() = Vector2(coordinates.x + texture.textureCenter.x, coordinates.y + texture.textureCenter.y)
    var isActive = true
    protected var movingJob: Job? = null

    abstract fun startMoving()
    fun stopMoving() {
        movingJob?.cancel()
    }
}
