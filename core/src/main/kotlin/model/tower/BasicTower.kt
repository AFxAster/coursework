package model.tower

import atlas.TowerAtlas.BASIC_TOWER_FRAME_NUMBER
import com.badlogic.gdx.math.Vector2
import common.*
import controller.ProjectileController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.enemy.Enemy
import model.projectile.Arrow
import model.projectile.ArrowTexture

class BasicTower(
    coordinates: Vector2,
    towerTexture: BasicTowerTexture,
) : Tower(coordinates, towerTexture) {

    override var targets: List<Enemy> = emptyList()
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                val firstEnemy = value.first()
                val direction = firstEnemy.center - coordinates - weaponCenter
                texture.rotateTo(direction)
                texture.playAnimation = true
            } else {
                texture.playAnimation = false
            }
        }
    override var damage: Float = BASIC_TOWER_DEFAULT_DAMAGE
    override var attackSpeed: Float = BASIC_TOWER_DEFAULT_ATTACK_SPEED
        set(value) {
            texture.animationDuration = 1 / attackSpeed
            field = value
        }
    override var radius: Float = BASIC_TOWER_DEFAULT_RADIUS
        set(value) {
            texture.textureRadius = value
            field = value
        }


    override val damageMultiplier: Float = BASIC_TOWER_DEFAULT_DAMAGE_MULTIPLIER
    override val attackSpeedMultiplier: Float = BASIC_TOWER_DEFAULT_ATTACK_SPEED_MULTIPLIER
    override val radiusMultiplier: Float = BASIC_TOWER_DEFAULT_RADIUS_MULTIPLIER

    init {
        towerTexture.textureRadius = radius
    }

    override fun startAttacking(projectileController: ProjectileController) {
        if (targets.isEmpty() || attackingJob != null && attackingJob!!.isActive) return
        attackingJob = CoroutineScope(Dispatchers.Default).launch {
            var enemy = targets.firstOrNull()
            delay((1000 / attackSpeed / BASIC_TOWER_FRAME_NUMBER * 4).toLong())
            while (enemy != null && enemy.isAlive) {
                // todo чекнуть что цикл не кончается когда
                val arrowTexture = ArrowTexture()
                val projectile = Arrow(
                    coordinates + weaponCenter - arrowTexture.textureCenter,
                    damage,
                    enemy,
                    arrowTexture
                )
                projectile.startMoving()
                projectileController.addProjectile(projectile)
                delay((1000 / attackSpeed).toLong())
                enemy = targets.firstOrNull()
            }
        }
    }
}

