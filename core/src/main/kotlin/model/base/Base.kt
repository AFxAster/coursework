package model.base

import common.BASE_MAX_HEALTH
import common.Texture

class Base(
    private val baseHealthBar: BaseHealthBar
) : Texture by baseHealthBar {

    private val maxHealth = BASE_MAX_HEALTH
    var health = maxHealth
        set(value) {
            baseHealthBar.status = value / maxHealth
            field = value
        }
    val isAlive: Boolean
        get() = health > 0
}
