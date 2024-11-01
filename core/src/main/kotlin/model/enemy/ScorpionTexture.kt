package model.enemy

import atlas.EnemyAtlas
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

class ScorpionTexture(
    direction: Direction
) : EnemyTexture {

    override val healthBar = EnemyHealthBar()
    private var animation: Animation<TextureRegion> = EnemyAtlas.SCORPION_MOVING_RIGHT_ANIMATION
    override val originalWidth: Int = animation.keyFrames[0].regionWidth
    override val originalHeight: Int = animation.keyFrames[0].regionHeight
    override val textureCenter = Vector2(originalWidth / 2f, originalHeight / 2f)

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

    override fun render(batch: SpriteBatch, coordinates: Vector2) {
        stateTime += Gdx.graphics.deltaTime
        val scorpionStateTexture = animation.getKeyFrame(stateTime)
        batch.draw(
            scorpionStateTexture,
            coordinates.x,
            coordinates.y,
            scorpionStateTexture.regionWidth.toFloat(),
            scorpionStateTexture.regionHeight.toFloat()
        )
        healthBar.render(
            batch,
            Vector2(coordinates.x + (originalWidth - healthBar.originalWidth) / 2f, coordinates.y + originalHeight)
        )
    }

    override fun dispose() {
        healthBar.dispose()
    }
}
