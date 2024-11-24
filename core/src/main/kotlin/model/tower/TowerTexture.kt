package model.tower

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import common.Texture

interface TowerTexture : Texture {
    val weaponCenter: Vector2
    var textureRadius: Float
    var animationDuration: Float
    var playAnimation: Boolean
    fun renderRadius(batch: SpriteBatch, coordinates: Vector2)
    fun rotateTo(to: Vector2)
}
