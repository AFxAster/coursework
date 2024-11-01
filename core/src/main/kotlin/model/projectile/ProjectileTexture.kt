package model.projectile

import com.badlogic.gdx.math.Vector2
import common.Texture

interface ProjectileTexture : Texture {
    fun rotateTo(to: Vector2)
}
