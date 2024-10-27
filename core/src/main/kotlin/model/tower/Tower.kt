package model.tower

import model.enemy.Enemy

abstract class Tower(
    val xIndex: Int,
    val yIndex: Int,
    var radius: Float,
    val towerTexture: TowerTexture
) : TowerTexture by towerTexture {
    abstract var targets: List<Enemy>
}
