package common

import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface Texture {
    val originalWidth: Int
    val originalHeight: Int
    val centerX: Float
    val centerY: Float
    fun render(batch: SpriteBatch, x: Float, y: Float)
    fun dispose()
}
