package model.enemy

import GDXTexture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.Texture

class EnemyHealthBar : Texture {

    override val originalWidth: Int = 50
    override val originalHeight: Int = 8
    override val textureCenterX: Float = originalWidth / 2f
    override val textureCenterY: Float = originalHeight / 2f

    private val inset = 1
    var status: Float = 1f
        set(value) {
            val pixmap = Pixmap(originalWidth, originalHeight, Pixmap.Format.RGBA8888).apply {
                setColor(Color.LIGHT_GRAY)
                fillRectangle(0, 0, this@EnemyHealthBar.originalWidth, this@EnemyHealthBar.originalHeight)
                setColor(Color.RED)
                fillRectangle(
                    inset,
                    inset,
                    ((this@EnemyHealthBar.originalWidth - 2 * inset) * value).toInt(),
                    this@EnemyHealthBar.originalHeight - 2 * inset
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

    override fun render(batch: SpriteBatch, x: Float, y: Float) {
        batch.draw(texture, x, y)
    }

    override fun dispose() {
        texture.dispose()
    }
}
