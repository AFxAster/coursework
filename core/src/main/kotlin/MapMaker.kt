import com.badlogic.gdx.math.Vector2
import common.COLUMNS_SIZE
import common.ROWS_SIZE
import model.enemy.Direction
import java.util.*

class MapMaker { // todo переделать это говно
    var matrix: Array<IntArray> = generateMap(ROWS_SIZE, COLUMNS_SIZE)
    val pathTiles: List<Vector2> = createPathTiles()
    val directionList: List<Pair<Vector2, Direction>> = createDirectionList()

    private fun generateMap(row: Int, col: Int): Array<IntArray> {
        matrix = Array(row) { IntArray(col) }
        var start = 0
        var finish = 0
        val rand = Random()

        for (i in 0 until col) {
            if (i % 2 == 0) {
                finish = rand.nextInt(row)
                fillMap(i, start, finish)
                start = finish
            } else {
                fillMap(i, start, finish)
            }
        }
        return matrix
    }

    private fun createDirectionList(): List<Pair<Vector2, Direction>> {
        val directionList: MutableList<Pair<Vector2, Direction>> = mutableListOf()
        var x = 0f
        var y = 0f
        val pathTilesCopy = pathTiles.toMutableList()
        while (pathTilesCopy.size > 1) {
            val isPathLeft = pathTilesCopy.indexOfFirst { x - 1 == it.x && y == it.y } != -1
            if (isPathLeft) {
                directionList.add(Vector2(x, y) to Direction.Left)
                pathTilesCopy.removeIf { it.x == x && it.y == y }
                x--
                continue
            }

            val isPathRight = pathTilesCopy.indexOfFirst { x + 1 == it.x && y == it.y } != -1
            if (isPathRight) {
                directionList.add(Vector2(x, y) to Direction.Right)
                pathTilesCopy.removeIf { it.x == x && it.y == y }
                x++
                continue
            }

            val isPathUp = pathTilesCopy.indexOfFirst { x == it.x && y + 1 == it.y } != -1
            if (isPathUp) {
                directionList.add(Vector2(x, y) to Direction.Up)
                pathTilesCopy.removeIf { it.x == x && it.y == y }
                y++
                continue
            }

            val isPathDown = pathTilesCopy.indexOfFirst { x == it.x && y - 1 == it.y } != -1
            if (isPathDown) {
                directionList.add(Vector2(x, y) to Direction.Down)
                pathTilesCopy.removeIf { it.x == x && it.y == y }
                y--
            }
        }
        directionList.add(Vector2(x, y) to directionList.last().second)

        return directionList
    }

    private fun fillMap(col: Int, start: Int, finish: Int) {
        var start = start
        var finish = finish
        if (start > finish) {
            val temp = finish
            finish = start
            start = temp
        }
        for (i in start until finish + 1) {
            matrix[i][col] = 1
        }
    }

    private fun createPathTiles(): List<Vector2> {
        val pathTiles: MutableList<Vector2> = mutableListOf()
        for (i in 0 until ROWS_SIZE) {
            for (j in 0 until COLUMNS_SIZE) {
                if (matrix[i][j] == 1) {
                    pathTiles.add(Vector2(i.toFloat(), j.toFloat()))
                }
            }
        }

        return pathTiles
    }
}
