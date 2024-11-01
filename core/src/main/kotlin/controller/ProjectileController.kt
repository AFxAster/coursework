package controller

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.minus
import model.projectile.Projectile

class ProjectileController {
    private val projectiles: MutableList<Projectile> = mutableListOf()

    fun addProjectile(projectile: Projectile) {
        projectiles.add(projectile)
    }

    fun render(batch: SpriteBatch) {
        update()
        projectiles.forEach {
//            val direction = Vector2(it.target.centerX, it.target.centerY).sub(it.centerX, it.centerY)
            val direction = it.target.center - it.center
            it.rotateTo(direction)
            it.render(batch, it.coordinates)
        }
//        println(projectiles)
    }

    private fun update() {
        projectiles.removeAll { !it.isActive }
    }

    fun stopMoving() {
        projectiles.forEach { it.stopMoving() }
    }

    fun dispose() {
        projectiles.forEach { it.dispose() }
    }
}
