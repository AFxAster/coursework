package model.tower

import controller.ProjectileController
import kotlinx.coroutines.Job
import model.enemy.Enemy

abstract class Tower(
    val x: Float,
    val y: Float,
    val texture: TowerTexture
) : TowerTexture by texture {
    open var targets: List<Enemy> = emptyList()

    abstract var radius: Float
    abstract var attackSpeed: Float
    abstract var damage: Float
    protected var attackJob: Job? = null

    abstract fun startAttacking(projectileController: ProjectileController)
    fun stopAttacking() {
        attackJob?.cancel()
    }
}
