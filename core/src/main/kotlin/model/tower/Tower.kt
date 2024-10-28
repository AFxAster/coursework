package model.tower

import controller.ProjectileController
import model.enemy.Enemy

abstract class Tower(
    val x: Float,
    val y: Float,
    var radius: Float,
    var attackSpeed: Float,
    val texture: TowerTexture
) : TowerTexture by texture {
    abstract var targets: List<Enemy>

    abstract fun startAttack(projectileController: ProjectileController)
}
