package model.enemy

import atlas.EnemyAtlas
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class ScorpionTexture(
    direction: Direction
) : EnemyTexture {

    override val healthBar = EnemyHealthBar()
    private var animation: Animation<TextureRegion> = EnemyAtlas.SCORPION_MOVING_RIGHT_ANIMATION
    override val originalWidth: Int = animation.keyFrames[0].regionWidth
    override val originalHeight: Int = animation.keyFrames[0].regionHeight
    override val textureCenterX = originalWidth / 2f
    override val textureCenterY = originalHeight / 2f

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
    private var stateTime = 0f

    init {
        this.direction = direction
    }

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
        healthBar.render(batch, x + (originalWidth - healthBar.originalWidth) / 2f, y + originalHeight)
    }

    override fun dispose() {
        healthBar.dispose()
    }
}
