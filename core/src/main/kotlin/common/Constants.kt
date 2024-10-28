package common

import com.badlogic.gdx.graphics.Color

//val scale: Float = 1f
const val TILE_SIZE: Int = 64
const val ROWS_SIZE = 14
const val COLUMNS_SIZE = 14
const val SCREEN_WIDTH = COLUMNS_SIZE * TILE_SIZE
const val SCREEN_HEIGHT = ROWS_SIZE * TILE_SIZE

val TOWER_RADIUS_COLOR = Color(40 / 255f, 210 / 255f, 255 / 255f, 0.40f)

const val BASIC_TOWER_RADIUS_DEFAULT = 250f
const val BASIC_TOWER_ATTACK_SPEED = 0.5f

const val ARROW_SPEED = 3f

const val SCORPION_DEFAULT_SPEED = 2f

const val RENDER_TIME = 10L
