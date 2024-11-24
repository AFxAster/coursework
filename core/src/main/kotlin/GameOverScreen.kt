import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import common.*
import controller.GameController


class GameOverScreen(
    private val gameController: GameController
) : Texture {
    override val originalWidth: Int = Gdx.graphics.width
    override val originalHeight: Int = Gdx.graphics.height
    override val textureCenter = Vector2(originalWidth / 2f, originalHeight / 2f)
    private val textStyle = TextButton.TextButtonStyle().apply {
        val size = 20
        val generator = FreeTypeFontGenerator(Gdx.files.internal("font.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = size
        val bitmapFont: BitmapFont = generator.generateFont(parameter)
        font = bitmapFont
    }
    private val newGameButton = Table().apply {
        setSize(120f, 60f)
        val bgPixmap = Pixmap(10, 10, Pixmap.Format.RGBA8888)
        bgPixmap.setColor(NEW_GAME_BUTTON_COLOR)
        bgPixmap.fill()
        background = TextureRegionDrawable(GDXTexture(bgPixmap))
        add(TextButton("New game", textStyle))
        x = SCREEN_WIDTH / 2 - width / 2
        y = SCREEN_HEIGHT / 2 - height / 2
    }
    private val inputAdapter = GameOverInputAdapter(gameController, newGameButton)

    private val pixmap = Pixmap(originalWidth, originalHeight, Pixmap.Format.RGBA8888).apply {
        setColor(GAME_OVER_COLOR)
        fillRectangle(0, 0, originalWidth, originalHeight)
    }
    private val texture = GDXTexture(pixmap)

    fun init() {
        Gdx.input.inputProcessor = inputAdapter
    }

    override fun render(batch: SpriteBatch, coordinates: Vector2) {
        batch.draw(texture, coordinates.x, coordinates.y)
        newGameButton.draw(batch, 1f)
    }

    override fun dispose() {
        texture.dispose()
    }

}
