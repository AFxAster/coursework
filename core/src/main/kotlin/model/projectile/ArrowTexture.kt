package model.projectile

import atlas.ProjectileAtlas
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

class ArrowTexture : ProjectileTexture {

    private val animation: Animation<TextureRegion> = ProjectileAtlas.ARROW_1_LVL_ANIMATION

    override val originalWidth: Int = animation.keyFrames[0].regionWidth
    override val originalHeight: Int = animation.keyFrames[0].regionHeight
    override val textureCenter = Vector2(originalWidth / 2f, originalHeight / 2f)

    private var rotation: Float = 0f

    private var stateTime = 0f

    override fun render(batch: SpriteBatch, coordinates: Vector2) {
        stateTime += Gdx.graphics.deltaTime
        val projectileStateTexture = animation.getKeyFrame(stateTime)

        batch.draw(
            projectileStateTexture,
            coordinates.x,
            coordinates.y,
            textureCenter.x,
            textureCenter.y,
            projectileStateTexture.regionWidth.toFloat(),
            projectileStateTexture.regionHeight.toFloat(),
            1f,
            1f,
            rotation
        )
    }

    override fun rotateTo(to: Vector2) {
        rotation = to.angleDeg() - 90
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }
}
