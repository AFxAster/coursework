import com.badlogic.gdx.math.Vector2
import common.COLUMNS_SIZE
import common.ROWS_SIZE
import model.enemy.Direction

class MapMaker {
    val firstPathCoords = Vector2()
    var matrix: Array<IntArray> = generateMap()
    private val pathTiles = createPathTiles()
    val directionList: List<Pair<Vector2, Direction>> = createDirectionList()

    private fun generateMap(): Array<IntArray> {
        var a: Array<IntArray>?
        do {
            matrix = Array(ROWS_SIZE) { IntArray(COLUMNS_SIZE) }
            a = fillMap()
        } while (a == null)
        return a
    }

    private fun fillMap(): Array<IntArray>? {
        var i = 0
        var j = (0 until COLUMNS_SIZE).random()
        firstPathCoords.set(i.toFloat(), j.toFloat())
        while (true) {
            val n = getAppropriateNeighbours(i, j)
            if (n.isEmpty()) return null
            val path = n.random()
            matrix[i][j] = 1
            i = path.first
            j = path.second
            if (i == ROWS_SIZE)
                break
        }
        return matrix
    }

    private fun getAppropriateNeighbours(i: Int, j: Int): List<Pair<Int, Int>> {
        val neighbours = getNeighbours(i, j)
        return neighbours.mapNotNull { (i, j) ->
            if (i >= ROWS_SIZE) i to j
            else if (matrix[i][j] == 1) null
            else {
                val currentNeighbours = getNeighbours(i, j)

                if (currentNeighbours.indexOfFirst { it.first < ROWS_SIZE && matrix[it.first][it.second] == 1 } != -1) {
                    null
                } else {
                    i to j
                }
            }
        }
    }

    private fun getNeighbours(i: Int, j: Int): List<Pair<Int, Int>> {
        return listOf(
            i - 1 to j,
            i + 1 to j,
            i to j - 1,
            i to j + 1
        ).mapNotNull { (i, j) ->
            if (i < 0 || j < 0 || j > COLUMNS_SIZE - 1)
                null
            else
                i to j
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

    private fun createDirectionList(): List<Pair<Vector2, Direction>> {
        val directionList: MutableList<Pair<Vector2, Direction>> = mutableListOf()
        var x = firstPathCoords.x
        var y = firstPathCoords.y
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
        directionList.add(Vector2(x, y) to Direction.Right)

        return directionList
    }
}
