package common

import com.badlogic.gdx.math.Vector2

operator fun Vector2.plus(other: Vector2): Vector2 {
    return Vector2(this).add(other)
}

operator fun Vector2.minus(other: Vector2): Vector2 {
    return Vector2(this).sub(other)
}
