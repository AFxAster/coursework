package model.projectile

import common.Texture

interface ProjectileTexture : Texture {
    fun rotateTo(x: Float, y: Float)
}
