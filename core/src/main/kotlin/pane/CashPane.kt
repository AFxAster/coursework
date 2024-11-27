package pane

import GDXTexture
import atlas.CommonAtlas
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import common.INITIAL_CASH
import common.SCREEN_HEIGHT
import common.SCREEN_WIDTH
import common.TOP_PANE_BACKGROUND_COLOR

class CashPane {

    private val padding = 5f
    private val textStyle = Label.LabelStyle().apply {
        val size = 24
        val generator = FreeTypeFontGenerator(Gdx.files.internal("font.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = size
        val bitmapFont: BitmapFont = generator.generateFont(parameter)
        font = bitmapFont
    }
    var cash = INITIAL_CASH
        set(value) {
            cashLabel.setText("$value")
            field = value
        }
    private val cashLabel = Label("0", textStyle)
    val group = Table().apply {
        pad(padding)
        setSize(150f, 64 + 2 * padding)
        val bgPixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        bgPixmap.setColor(TOP_PANE_BACKGROUND_COLOR)
        bgPixmap.fill()
        background = TextureRegionDrawable(GDXTexture(bgPixmap))
        bgPixmap.dispose()
        y = SCREEN_HEIGHT - height
        x = SCREEN_WIDTH - width

        cashLabel.setPosition(SCREEN_WIDTH - 50f, 0f)

        add(Image(CommonAtlas.COIN))
        add(cashLabel).padLeft(10f)
    }
}
