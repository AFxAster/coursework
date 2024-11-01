package model.base

import GDXTexture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import common.Texture

class BaseHealthBar : Texture {
    override val originalWidth: Int = 200
    override val originalHeight: Int = 50
    override val textureCenter = Vector2(originalWidth / 2f, originalHeight / 2f)

    private val inset = 5
    var status: Float = 1f
        set(value) {
            val pixmap = Pixmap(originalWidth, originalHeight, Pixmap.Format.RGBA8888).apply {
                setColor(Color.LIGHT_GRAY)
                fillRectangle(0, 0, this@BaseHealthBar.originalWidth, this@BaseHealthBar.originalHeight)
                when {
                    value > 2 / 3f -> setColor(Color.GREEN)
                    value > 1 / 3f -> setColor(Color.YELLOW)
                    else -> setColor(Color.RED)
                }
                fillRectangle(
                    inset,
                    inset,
                    ((this@BaseHealthBar.originalWidth - 2 * inset) * value).toInt(),
                    this@BaseHealthBar.originalHeight - 2 * inset
                )
            }
            Gdx.app.postRunnable {
                texture.dispose()
                texture = GDXTexture(pixmap)
            }
            field = value
        }
    private var texture = GDXTexture(0, 0, Pixmap.Format.RGBA8888)

    init {
        status = 1f
    }

    override fun render(batch: SpriteBatch, coordinates: Vector2) {
        batch.draw(texture, coordinates.x, coordinates.y)
    }

    override fun dispose() {
        texture.dispose()
    }
}
