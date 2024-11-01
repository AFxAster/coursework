import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import common.GAME_OVER_COLOR
import common.Texture

class GameOverScreen : Texture {
    override val originalWidth: Int = Gdx.graphics.width
    override val originalHeight: Int = Gdx.graphics.height
    override val textureCenter = Vector2(originalWidth / 2f, originalHeight / 2f)

    private val pixmap = Pixmap(originalWidth, originalHeight, Pixmap.Format.RGBA8888).apply {
        setColor(GAME_OVER_COLOR)
        fillRectangle(0, 0, originalWidth, originalHeight)
    }
    private val texture = GDXTexture(pixmap)

    override fun render(batch: SpriteBatch, coordinates: Vector2) {
        batch.draw(texture, coordinates.x, coordinates.y)
    }

    override fun dispose() {
        texture.dispose()
    }

}
