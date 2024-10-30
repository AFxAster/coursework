package controller

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import model.projectile.Projectile

class ProjectileController {
    private val projectiles: MutableList<Projectile> = mutableListOf()

    fun addProjectile(projectile: Projectile) {
        projectiles.add(projectile)
    }

    fun render(batch: SpriteBatch) {
        update()
        projectiles.forEach {
            val direction = Vector2(it.target.centerX, it.target.centerY).sub(it.centerX, it.centerY)
            it.rotateTo(direction.x, direction.y)
            it.render(batch, it.x, it.y)
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
