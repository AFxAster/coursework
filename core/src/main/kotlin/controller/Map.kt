package controller

import MapMaker
import atlas.CommonAtlas
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.COLUMNS_SIZE
import common.ROWS_SIZE
import common.TILE_SIZE
import common.Texture
import model.base.Base
import model.enemy.Direction
import model.tile.Tile
import model.tile.TileState

class Map(
    val base: Base
) : Texture {
    override val originalWidth: Int = ROWS_SIZE * TILE_SIZE
    override val originalHeight: Int = COLUMNS_SIZE * TILE_SIZE
    override val textureCenterX: Float = originalWidth / 2f
    override val textureCenterY: Float = originalHeight / 2f

    private val mapMaker = MapMaker()
    private val field: List<List<Tile>> = createField()

    override fun render(batch: SpriteBatch, x: Float, y: Float) {
        field.forEachIndexed { i, row ->
            row.forEachIndexed { j, tile ->
                tile.render(batch, i.toFloat() * tile.originalWidth, j.toFloat() * tile.originalHeight)
            }
        }
    }

    fun renderHealthBar(batch: SpriteBatch, x: Float, y: Float) {
        base.render(batch, x, y)
    }

    override fun dispose() {
        field.forEach { row ->
            row.forEach { it.dispose() } // todo может достаточно одну текстуру освободить чем в каждой клетке
        }
        base.dispose()
    }

    fun getTile(x: Float, y: Float): Tile {
        return field[x.toInt() / CommonAtlas.TILE.width][y.toInt() / CommonAtlas.TILE.height]
    }

    fun getDirectionOnPath(x: Float, y: Float): Direction {
        val tile = getTile(x, y)
        return mapMaker.directionList.find {
            it.first.x.toInt() == tile.xIndex && it.first.y.toInt() == tile.yIndex
        }?.second!!
    }

    private fun createField(): List<List<Tile>> {
        val matrix = mapMaker.matrix
        return matrix.toList().mapIndexed { i, rows ->
            rows.toList().mapIndexed { j, isPath ->
                if (isPath == 1)
                    Tile(TileState.Path, i, j)
                else
                    Tile(TileState.Empty, i, j)
            }
        }
    }

}
