package common

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

interface Texture {
    val originalWidth: Int
    val originalHeight: Int
    val textureCenter: Vector2
    fun render(batch: SpriteBatch, coordinates: Vector2)
    fun dispose()
}
