package model.tower

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.Texture

interface TowerTexture : Texture {
    val weaponCenterX: Float
    val weaponCenterY: Float
    var playAnimation: Boolean
    fun renderRadius(batch: SpriteBatch, x: Float, y: Float)
    fun rotateTo(x: Float, y: Float)
}
