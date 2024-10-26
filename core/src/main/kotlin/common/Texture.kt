package common

import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface Texture {
    val originalWidth: Int
    val originalHeight: Int
    fun render(batch: SpriteBatch, x: Float, y: Float)
    fun dispose()
}
