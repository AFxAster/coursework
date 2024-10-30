package common

import com.badlogic.gdx.graphics.Color

//val scale: Float = 1f
const val TILE_SIZE: Int = 64
const val ROWS_SIZE = 14
const val COLUMNS_SIZE = 14
const val SCREEN_WIDTH = COLUMNS_SIZE * TILE_SIZE
const val SCREEN_HEIGHT = ROWS_SIZE * TILE_SIZE
val GAME_OVER_COLOR = Color(145 / 255f, 135 / 255f, 140 / 255f, 0.60f)

const val BASE_MAX_HEALTH = 200f

val TOWER_RADIUS_COLOR = Color(40 / 255f, 210 / 255f, 255 / 255f, 0.40f)

const val BASIC_TOWER_DEFAULT_RADIUS = 250f
const val BASIC_TOWER_DEFAULT_ATTACK_SPEED = 0.9f
const val BASIC_TOWER_DEFAULT_DAMAGE = 20f

const val ARROW_DEFAULT_SPEED = 3f

const val SCORPION_DEFAULT_SPEED = 5f
const val SCORPION_DEFAULT_MAX_HEALTH = 100f
const val SCORPION_DEFAULT_DAMAGE = 100f

const val RENDER_TIME = 10L
