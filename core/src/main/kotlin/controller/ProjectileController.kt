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
            if (it.target == null) return
            val direction = Vector2(it.target.centerX, it.target.centerY).sub(
                it.x + it.texture.textureCenterX,
                it.y + it.texture.textureCenterY
            )
            it.rotateTo(direction.x, direction.y)
            it.render(batch, it.x, it.y)
        }
    }

    private fun update() {
        projectiles.removeAll { it.movingJob!!.isCompleted }
    }

    fun dispose() {
        projectiles.forEach { it.dispose() }
    }
}
