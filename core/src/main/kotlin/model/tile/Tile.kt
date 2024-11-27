package model.tile

import GDXTexture
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import common.SELECT_TILE_COLOR
import common.TILE_SIZE
import common.Texture

class Tile(
    var state: TileState,
    val xIndex: Int,
    val yIndex: Int,
) : Texture {
    override val originalWidth: Int = state.texture.width
    override val originalHeight: Int = state.texture.height
    override val textureCenter = Vector2(originalWidth / 2f, originalHeight / 2f)
    var isActive = false
    private val shading: GDXTexture by lazy {
        val pixmap = Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGBA8888)
        pixmap.setColor(SELECT_TILE_COLOR)
        pixmap.fill()
        GDXTexture(pixmap)
    }

    override fun render(batch: SpriteBatch, coordinates: Vector2) {
        batch.draw(state.texture, coordinates.x, coordinates.y)
        if (isActive)
            batch.draw(shading, coordinates.x, coordinates.y)
    }

    override fun dispose() {
        state.texture.dispose()
    }
}
