import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.GAME_OVER_COLOR
import common.Texture

class GameOverScreen : Texture {
    override val originalWidth: Int = Gdx.graphics.width
    override val originalHeight: Int = Gdx.graphics.height
    override val textureCenterX: Float = originalWidth / 2f
    override val textureCenterY: Float = originalHeight / 2f
    private val pixmap = Pixmap(originalWidth, originalHeight, Pixmap.Format.RGBA8888).apply {
        setColor(GAME_OVER_COLOR)
        fillRectangle(0, 0, originalWidth, originalHeight)
    }
    private val texture = GDXTexture(pixmap)

    override fun render(batch: SpriteBatch, x: Float, y: Float) {
        batch.draw(texture, x, y)
    }

    override fun dispose() {
        texture.dispose()
    }

}
