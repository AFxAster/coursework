package model.enemy

import com.badlogic.gdx.math.Vector2
import common.Texture

abstract class Enemy(
    var x: Float, // todo на Vector2 поменять??
    var y: Float,
    open var direction: Direction
) : Texture {
    val lastPoint = Vector2(x, y)
    abstract fun moveTo(direction: Direction)
}
