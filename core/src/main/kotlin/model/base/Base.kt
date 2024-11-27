package model.base

import common.BASE_MAX_HP
import common.INITIAL_CASH
import common.Texture
import pane.CashPane

class Base(
    private val cashPane: CashPane,
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

    var cash = INITIAL_CASH
        set(value) {
            cashPane.cash = value
            field = value
        }

    init {
        cash = INITIAL_CASH
    }
}
