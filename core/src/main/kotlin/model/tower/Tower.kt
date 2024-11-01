package model.tower

import com.badlogic.gdx.math.Vector2
import controller.ProjectileController
import kotlinx.coroutines.Job
import model.enemy.Enemy

abstract class Tower(
    val coordinates: Vector2,
    val texture: TowerTexture
) : TowerTexture by texture {

    open var targets: List<Enemy> = emptyList()

    abstract var radius: Float
    abstract var attackSpeed: Float
    abstract var damage: Float
    var attackingJob: Job? = null

    abstract fun startAttacking(projectileController: ProjectileController)
    fun stopAttacking() {
        attackingJob?.cancel()
    }
}
