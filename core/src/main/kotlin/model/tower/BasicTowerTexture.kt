package model.tower

import GDXTexture
import atlas.TowerAtlas
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import common.TOWER_RADIUS_COLOR


class BasicTowerTexture : TowerTexture {

    private val baseTexture: GDXTexture = TowerAtlas.BASIC_TOWER_BASE
    private var radiusTexture: GDXTexture = GDXTexture(Pixmap(0, 0, Pixmap.Format.RGBA8888))

    private val animation: Animation<TextureRegion> = TowerAtlas.BASIC_TOWER_ANIMATION
    override var animationDuration: Float = animation.animationDuration
        set(value) {
            animation.frameDuration = value / animation.keyFrames.size
            field = value
        }
    override var playAnimation: Boolean = false
    private var stateTime = 0f

    private val weaponTexture: TextureRegion = animation.keyFrames[0]

    override val originalWidth: Int = baseTexture.height
    override val originalHeight: Int =
        baseTexture.height // Todo че то решить с оригинальной не оригинальной шириной высотой

    override val textureCenter = Vector2(32f, 32f)

    override val weaponCenter = Vector2(textureCenter.x, textureCenter.y + 40)


    private var rotation: Float = 0f
    override var textureRadius: Float = 0f
        set(radius) {
            val pixmap = Pixmap((radius * 2).toInt(), (radius * 2).toInt(), Pixmap.Format.RGBA8888)
            pixmap.setColor(TOWER_RADIUS_COLOR)
            pixmap.fillCircle(radius.toInt(), radius.toInt(), radius.toInt())
            radiusTexture = GDXTexture(pixmap)
            field = radius
        }

    override fun render(batch: SpriteBatch, coordinates: Vector2) {
        batch.draw(baseTexture, coordinates.x, coordinates.y)

        val weaponStateTexture: TextureRegion
        if (playAnimation) {
            stateTime += Gdx.graphics.deltaTime
            weaponStateTexture = animation.getKeyFrame(stateTime)
        } else {
            stateTime = 0f
            weaponStateTexture = weaponTexture
        }
        batch.draw(
            weaponStateTexture,
            coordinates.x - 16,
            coordinates.y + 24,
            48f,
            48f,
            weaponStateTexture.regionWidth.toFloat(),
            weaponStateTexture.regionHeight.toFloat(),
            1f,
            1f,
            rotation
        )
    }

    override fun renderRadius(batch: SpriteBatch, coordinates: Vector2) {
        batch.draw(
            radiusTexture,
            coordinates.x + textureCenter.x - textureRadius,
            coordinates.y + textureCenter.y - textureRadius
        )
    }

    override fun rotateTo(to: Vector2) {
        rotation = to.angleDeg() - 90
    }

    override fun dispose() {
        radiusTexture.dispose()
    }
}
