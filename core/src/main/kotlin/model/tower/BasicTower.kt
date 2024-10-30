package model.tower

import atlas.TowerAtlas.BASIC_TOWER_FRAME_NUMBER
import com.badlogic.gdx.math.Vector2
import common.BASIC_TOWER_DEFAULT_ATTACK_SPEED
import common.BASIC_TOWER_DEFAULT_DAMAGE
import common.BASIC_TOWER_DEFAULT_RADIUS
import controller.ProjectileController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.enemy.Enemy
import model.projectile.Arrow
import model.projectile.ArrowTexture

class BasicTower(
    x: Float,
    y: Float,
    towerTexture: BasicTowerTexture,
) : Tower(x, y, towerTexture) {

    // todo если делать rxJava то можно это наблюдать и радиус, то обсервером будет текстура
    override var targets: List<Enemy> = emptyList()
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                val firstEnemy = value.first()
                val direction = Vector2(firstEnemy.centerX, firstEnemy.centerY).sub(
                    x + weaponCenterX,
                    y + weaponCenterY
                )
                texture.rotateTo(direction.x, direction.y)
                texture.playAnimation = true
            } else {
                texture.playAnimation = false
            }
        }
    override var radius: Float = BASIC_TOWER_DEFAULT_RADIUS
    override var attackSpeed: Float = BASIC_TOWER_DEFAULT_ATTACK_SPEED
    override var damage: Float = BASIC_TOWER_DEFAULT_DAMAGE

    init {
        towerTexture.radius = radius
    }

    override fun startAttacking(projectileController: ProjectileController) {
        if (targets.isEmpty() || attackJob != null && attackJob!!.isActive) return
        attackJob = CoroutineScope(Dispatchers.Default).launch {
            var enemy = targets.firstOrNull()
            delay((1000 / attackSpeed / BASIC_TOWER_FRAME_NUMBER * 4).toLong())
            while (enemy != null && enemy.isAlive) {
                // todo чекнуть что цикл не кончается когда
                val arrowTexture = ArrowTexture()
                val projectile = Arrow(
                    x + weaponCenterX - arrowTexture.textureCenterX,
                    y + weaponCenterY - arrowTexture.textureCenterY,
                    damage,
                    enemy,
                    arrowTexture
                )
                projectileController.addProjectile(projectile)
                delay((1000 / attackSpeed).toLong())
                enemy = targets.firstOrNull()
            }
        }
    }
}
