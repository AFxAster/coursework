package model.tile

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import common.Texture

class Tile(
    var state: TileState,
    val xIndex: Int,
    val yIndex: Int,
) : Texture {
    override val originalWidth: Int = state.texture.width
    override val originalHeight: Int = state.texture.height
    override val textureCenter = Vector2(originalWidth / 2f, originalHeight / 2f)

    override fun render(batch: SpriteBatch, coordinates: Vector2) {
        batch.draw(state.texture, coordinates.x, coordinates.y)
    }

    override fun dispose() {
        state.texture.dispose()
    }
}
