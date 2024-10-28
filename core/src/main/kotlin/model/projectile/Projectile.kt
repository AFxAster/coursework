package model.projectile

import kotlinx.coroutines.Job
import model.enemy.Enemy


abstract class Projectile(
    var x: Float,
    var y: Float,
    val target: Enemy?,
    val texture: ProjectileTexture
) : ProjectileTexture by texture {

    val centerX: Float
        get() = x + texture.textureCenterX
    val centerY: Float
        get() = y + texture.textureCenterY
    var movingJob: Job? = null

    protected abstract fun startMove()
}
