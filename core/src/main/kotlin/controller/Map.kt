package controller

import MapMaker
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
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
    override val textureCenter = Vector2(originalWidth / 2f, originalHeight / 2f)

    private var mapMaker = MapMaker()
    private var field: List<List<Tile>> = createField()
    val firstPathCoords: Vector2 = mapMaker.firstPathCoords

    override fun render(batch: SpriteBatch, coordinates: Vector2) {
        field.forEachIndexed { i, row ->
            row.forEachIndexed { j, tile ->
                tile.render(batch, Vector2(i.toFloat() * tile.originalWidth, j.toFloat() * tile.originalHeight))
            }
        }
    }

    override fun dispose() {
        field.forEach { row ->
            row.forEach { it.dispose() } // todo может достаточно одну текстуру освободить чем в каждой клетке
        }
        base.dispose()
    }

    fun getTile(coordinates: Vector2): Tile { // todo
        return field[coordinates.x.toInt() / TILE_SIZE][coordinates.y.toInt() / TILE_SIZE]
    }

    fun getDirectionOnPath(coordinates: Vector2): Direction {
        if (coordinates.x < 0) return Direction.Right
        val xIndex = coordinates.x.toInt() / TILE_SIZE
        val yIndex = coordinates.y.toInt() / TILE_SIZE
        return mapMaker.directionList.find {
            it.first.x.toInt() == xIndex && it.first.y.toInt() == yIndex
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

    fun reset() {
        mapMaker = MapMaker()
        field = createField()
        firstPathCoords.set(mapMaker.firstPathCoords)
    }
}
