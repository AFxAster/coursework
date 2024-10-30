package model.enemy

import common.Texture

interface EnemyTexture : Texture {
    var direction: Direction
    val healthBar: EnemyHealthBar
}
