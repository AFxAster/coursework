package model.tile

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.Texture

class Tile(
    var state: TileState,
    val xIndex: Int,
    val yIndex: Int,
) : Texture {
    override val originalWidth: Int = state.texture.width
    override val originalHeight: Int = state.texture.height
    override val centerX: Float
        get() = (xIndex + 0.5f) * originalWidth
    override val centerY: Float
        get() = (yIndex + 0.5f) * originalHeight

    override fun render(batch: SpriteBatch, x: Float, y: Float) {
        batch.draw(state.texture, x, y)
    }

    override fun dispose() {
        state.texture.dispose()
    }
}
