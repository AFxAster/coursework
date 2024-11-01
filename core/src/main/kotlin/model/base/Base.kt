package model.base

import common.BASE_MAX_HP
import common.Texture

class Base(
    private val baseHealthBar: BaseHealthBar = BaseHealthBar()
) : Texture by baseHealthBar {

    private val maxHealth = BASE_MAX_HP
    var health = maxHealth
        set(value) {
            baseHealthBar.status = value / maxHealth
            field = value
        }
    val isAlive: Boolean
        get() = health > 0
}
