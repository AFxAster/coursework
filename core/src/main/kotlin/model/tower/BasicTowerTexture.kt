package model.tower

import GDXTexture
import atlas.TowerAtlas
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion


class BasicTowerTexture : TowerTexture {

    override val originalHeight: Int = 128 // Todo че то решить с оригинальной не оригинальной шириной высотой
    override val originalWidth: Int = 64

    override val centerX: Float = 32f
    override val centerY: Float = 32f
    private val weaponCenterX = centerX
    private val weaponCenterY = centerY + 40

    override var rotation: Float = 0f
    var radius: Float = 0f
        set(radius) {
            val pixmap = Pixmap((radius * 2).toInt(), (radius * 2).toInt(), Pixmap.Format.RGBA8888)
            pixmap.setColor(40 / 255f, 210 / 255f, 255 / 255f, 0.40f)
            pixmap.fillCircle(radius.toInt(), radius.toInt(), radius.toInt())
            radiusTexture = GDXTexture(pixmap)
            field = radius
        }
    override var showRadius: Boolean = false


    private val baseTexture: GDXTexture = TowerAtlas.BASIC_TOWER_1_LVL_BASE
    private val animation: Animation<TextureRegion> = TowerAtlas.BASIC_TOWER_1_LVL_ANIMATION
    override var playAnimation: Boolean = false
    private val weaponTexture = animation.keyFrames[0]
    private var radiusTexture: GDXTexture = GDXTexture(Pixmap(0, 0, Pixmap.Format.RGBA8888))

    private var stateTime = 0f

    override fun render(batch: SpriteBatch, x: Float, y: Float) {

        if (showRadius) batch.draw(radiusTexture, x + centerX - radius, y + centerY - radius)
        batch.draw(baseTexture, x, y)

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
            x - 16,
            y + 24,
            16 + 32f,
            72 - 24f,
            weaponStateTexture.regionWidth.toFloat(),
            weaponStateTexture.regionHeight.toFloat(),
            1f,
            1f,
            rotation
        )
    }

    override fun dispose() {
        radiusTexture.dispose()
    }
}
