package pane

import GDXTexture
import atlas.TowerAtlas
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import common.BASIC_TOWER_DEFAULT_COST
import common.TILE_SIZE
import common.TOWER_SELECT_PANE_BACKGROUND_COLOR
import common.minus
import model.tower.BasicTower
import model.tower.BasicTowerTexture
import model.tower.Tower

class TowerSelectPane {
    private val coordinatesToPlace = Vector2()
    private val padding = 5f
    private val textStyle = Label.LabelStyle().apply {
        val size = 16
        val generator = FreeTypeFontGenerator(Gdx.files.internal("font.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = size
        val bitmapFont: BitmapFont = generator.generateFont(parameter)
        font = bitmapFont
    }
    private val basicTowerCostLabel = Label("$BASIC_TOWER_DEFAULT_COST", textStyle)

    val group = Table().apply {
        val towersNumber = 1
        val basicTowerTexture = TowerAtlas.BASIC_TOWER_BASE_TO_SELECT
        setSize(
            basicTowerTexture.regionWidth * towersNumber + padding * (towersNumber + 1),
            basicTowerTexture.regionHeight + 20 + padding * 2
        )
        pad(padding)
        val bgPixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        bgPixmap.setColor(TOWER_SELECT_PANE_BACKGROUND_COLOR)
        bgPixmap.fill()
        background = TextureRegionDrawable(GDXTexture(bgPixmap))
        bgPixmap.dispose()

        add(Image(basicTowerTexture))

        row()

        add(basicTowerCostLabel)
    }

    fun getClickedTower(coordinates: Vector2): Tower? {
        val coordinatesInPane = coordinates - Vector2(group.x, group.y)
        if (coordinatesInPane.y <= padding || coordinatesInPane.y >= group.height - padding) return null
        var x = 0f
        group.children.forEach {
            x += padding
            if (coordinatesInPane.x <= x) return null
            x += it.width
            if (coordinatesInPane.x <= x) return it.zIndex.toTower(coordinatesToPlace)
        }
        return null
    }

    fun isClickInside(coordinates: Vector2): Boolean {
        return coordinates.x >= group.x &&
            coordinates.x <= group.x + group.width &&
            coordinates.y >= group.y &&
            coordinates.y <= group.y + group.height
    }

    fun getCostOf(modifier: Tower): Int {
        return when (modifier) {
            is BasicTower -> {
                basicTowerCostLabel.text.toString().toInt()
            }

            else -> -1
        }
    }

    fun updateCoordinates(coordinates: Vector2) {
        coordinatesToPlace.set(coordinates)
        val paneCoordinates = coordinates - Vector2(group.width / 2 - TILE_SIZE / 2, group.height)
        group.setPosition(paneCoordinates.x, paneCoordinates.y)
    }
}

private fun Int.toTower(coordinates: Vector2): Tower? {
    return when (this) {
        0 -> BasicTower(Vector2(coordinates), BasicTowerTexture())
        else -> null
    }
}
