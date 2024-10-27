package model.enemy

import atlas.EnemyAtlas
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import common.SCORPION_DEFAULT_SPEED

class Scorpion(
    x: Float,
    y: Float,
    direction: Direction
) : Enemy(x, y, direction) {

    override val originalWidth: Int = 64
    override val originalHeight: Int = 64

    override var direction: Direction = direction
        set(value) {
            animation = when (value) {
                is Direction.Left -> EnemyAtlas.SCORPION_MOVING_LEFT_ANIMATION
                is Direction.Right -> EnemyAtlas.SCORPION_MOVING_RIGHT_ANIMATION
                is Direction.Up -> EnemyAtlas.SCORPION_MOVING_UP_ANIMATION
                is Direction.Down -> EnemyAtlas.SCORPION_MOVING_DOWN_ANIMATION
            }
            field = value
        }

    private var animation: Animation<TextureRegion> = EnemyAtlas.SCORPION_MOVING_RIGHT_ANIMATION
    private var stateTime = 0f
    private val delta = SCORPION_DEFAULT_SPEED

    override val centerX: Float
        get() = x + originalWidth / 2
    override val centerY: Float
        get() = y + originalHeight / 2

    override fun render(batch: SpriteBatch, x: Float, y: Float) {
        stateTime += Gdx.graphics.deltaTime
        val scorpionStateTexture = animation.getKeyFrame(stateTime)
        batch.draw(
            scorpionStateTexture,
            x,
            y,
            scorpionStateTexture.regionWidth.toFloat(),
            scorpionStateTexture.regionHeight.toFloat()
        )
    }

    override fun moveTo(direction: Direction) {
        this.direction = direction
        when (direction) {
            is Direction.Left -> {
                x -= delta
                lastPoint.x = x + originalWidth
            }

            is Direction.Right -> {
                x += delta
                lastPoint.x = x
            }

            is Direction.Up -> {
                y += delta
                lastPoint.y = y
            }

            is Direction.Down -> {
                y -= delta
                lastPoint.y = y + originalHeight
            }
        }
    }

    override fun dispose() {}
}
