package common

import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface Texture {
    val originalWidth: Int
    val originalHeight: Int
    val textureCenterX: Float
    val textureCenterY: Float
    fun render(batch: SpriteBatch, x: Float, y: Float)
    fun dispose()
}
