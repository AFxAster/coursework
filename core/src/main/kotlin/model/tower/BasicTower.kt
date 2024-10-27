package model.tower

import com.badlogic.gdx.math.Vector2
import common.TILE_SIZE
import model.enemy.Enemy

class BasicTower(
    xIndex: Int,
    yIndex: Int,
    radius: Float,
    towerTexture: BasicTowerTexture
) : Tower(xIndex, yIndex, radius, towerTexture) {

    override var targets: List<Enemy> =
        emptyList() // todo если делать rxJava то можно это наблюдать и радиус, то обсервером будет текстура
        set(value) {
            if (value.isNotEmpty()) {
                val firstEnemy = value.first()
                towerTexture.rotation =
                    Vector2(xIndex * TILE_SIZE.toFloat(), yIndex * TILE_SIZE + 40f).sub(firstEnemy.x, firstEnemy.y)
                        .angleDeg() + 90f
                towerTexture.playAnimation = true
            } else {
                towerTexture.playAnimation = false
            }
            field = value
        }

    init {
        towerTexture.radius = radius
    }
}
