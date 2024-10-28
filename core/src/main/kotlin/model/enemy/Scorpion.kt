package model.enemy

import atlas.EnemyAtlas
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import common.RENDER_TIME
import controller.Map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Scorpion( // todo ScorpionTexture пора бы скрафтить
    x: Float,
    y: Float,
    speed: Float,
    direction: Direction
) : Enemy(x, y, speed, direction) {



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

    override val originalWidth: Int = animation.keyFrames[0].regionWidth
    override val originalHeight: Int = animation.keyFrames[0].regionHeight

    private var stateTime = 0f

    override val textureCenterX = originalWidth / 2f
    override val textureCenterY = originalHeight / 2f

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

    override fun startMove(map: Map) {
        movingJob = CoroutineScope(Dispatchers.Default).launch {
            // todo пока жив еще наверное, если при смерти ОТМЕНЯТЬ эту корутину, то когда убираю этого крипа нужно
            //  добавить проверку что именно отменена корутина была, а не завершена
            while (0 <= lastPoint.x && lastPoint.x < Gdx.graphics.width && 0 <= lastPoint.y && lastPoint.y < Gdx.graphics.height) {
                val direction = map.getDirectionOnPath(lastPoint.x, lastPoint.y)
                moveTo(direction)
                delay(RENDER_TIME)
            }
            dispose()
        }
    }

    override fun moveTo(direction: Direction) {
        this.direction = direction
        when (direction) {
            is Direction.Left -> {
                x -= speed
                lastPoint.x = x + originalWidth
            }

            is Direction.Right -> {
                x += speed
                lastPoint.x = x
            }

            is Direction.Up -> {
                y += speed
                lastPoint.y = y
            }

            is Direction.Down -> {
                y -= speed
                lastPoint.y = y + originalHeight
            }
        }
    }

    override fun dispose() {}
}
