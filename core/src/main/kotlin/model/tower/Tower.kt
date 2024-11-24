package model.tower

import com.badlogic.gdx.math.Vector2
import controller.ProjectileController
import kotlinx.coroutines.Job
import model.enemy.Enemy
import modifier.Modifier

abstract class Tower(
    val coordinates: Vector2,
    val texture: TowerTexture
) : TowerTexture by texture {

    open var targets: List<Enemy> = emptyList()

    abstract var damage: Float
    abstract var attackSpeed: Float
    abstract var radius: Float

    var damageLevel = 1
    var attackSpeedLevel = 1
    var radiusLevel = 1

    abstract val damageMultiplier: Float
    abstract val attackSpeedMultiplier: Float
    abstract val radiusMultiplier: Float
    var attackingJob: Job? = null

    abstract fun startAttacking(projectileController: ProjectileController)
    fun stopAttacking() {
        attackingJob?.cancel()
    }
    fun applyModifier(modifier: Modifier) {
        when (modifier) {
            is Modifier.Attack -> {
                damage *= damageMultiplier
                damageLevel++
            }

            is Modifier.AttackSpeed -> {
                attackSpeed *= attackSpeedMultiplier
                attackSpeedLevel++
            }

            is Modifier.Range -> {
                radius *= radiusMultiplier
                radiusLevel++
            }
        }
    }
}
