package model.tower

import GDXTexture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import common.MODIFIER_PANE_BACKGROUND_COLOR
import java.math.BigDecimal
import java.math.RoundingMode

class TowerStatsPane {
    private val padding = 5f
    private val textStyle = Label.LabelStyle().apply {
        val size = 20
        val generator = FreeTypeFontGenerator(Gdx.files.internal("font.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = size
        val bitmapFont: BitmapFont = generator.generateFont(parameter)
        font = bitmapFont
    }
    val damageLabel = Label("", textStyle)
    val attackSpeedLabel = Label("", textStyle)
    val radiusLabel = Label("", textStyle)
    val group = Table().apply {
        pad(padding)
        setSize(500f, 100f)
        val bgPixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        bgPixmap.setColor(MODIFIER_PANE_BACKGROUND_COLOR)
        bgPixmap.fill()
        background = TextureRegionDrawable(GDXTexture(bgPixmap))
        bgPixmap.dispose()

        add(Label("Damage: ", textStyle))
        add(damageLabel)
        add(Label("Attack speed: ", textStyle)).padLeft(padding)
        add(attackSpeedLabel)
        add(Label("Radius: ", textStyle)).padLeft(padding)
        add(radiusLabel)
    }

    fun updateStats(damage: Float, attackSpeed: Float, radius: Float) {
        damageLabel.setText("${BigDecimal(damage.toDouble()).setScale(2, RoundingMode.HALF_EVEN)}")
        attackSpeedLabel.setText("${BigDecimal(attackSpeed.toDouble()).setScale(2, RoundingMode.HALF_EVEN)}")
        radiusLabel.setText("${BigDecimal(radius.toDouble()).setScale(2, RoundingMode.HALF_EVEN)}")
    }
}
