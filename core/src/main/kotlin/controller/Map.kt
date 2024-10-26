package controller

import MapMaker
import atlas.CommonAtlas
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import common.COLUMNS_SIZE
import common.ROWS_SIZE
import common.TILE_SIZE
import common.Texture
import model.enemy.Direction
import model.tile.Tile
import model.tile.TileState

class Map : Texture {
    override val originalWidth: Int = ROWS_SIZE * TILE_SIZE
    override val originalHeight: Int = COLUMNS_SIZE * TILE_SIZE

    private val mapMaker = MapMaker()
    private val field: List<List<Tile>> = createField()

    override fun render(batch: SpriteBatch, x: Float, y: Float) {
        field.forEachIndexed { i, row ->
            row.forEachIndexed { j, tile ->
                tile.render(batch, i.toFloat() * tile.originalWidth, j.toFloat() * tile.originalHeight)
            }
        }
    }

    override fun dispose() {
        field.forEach { row ->
            row.forEach { it.dispose() } // todo может достаточно одну текстуру освободить чем в каждой клетке
        }
    }

    fun getTile(x: Int, y: Int): Tile {
        return field[x / CommonAtlas.TILE.width][y / CommonAtlas.TILE.height]
    }

    fun getDirectionOnPath(x: Float, y: Float): Direction {
        val tile = getTile(x.toInt(), y.toInt())
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
