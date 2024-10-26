package model.tower

import GDXTexture
import atlas.TowerAtlas
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class BasicTower(
    x: Int,
    y: Int
) : Tower(x, y) {

    override val originalHeight: Int = 128 // Todo че то решить с оригинальной не оригинальной шириной высотой
    override val originalWidth: Int = 64

    private val baseTexture: GDXTexture = TowerAtlas.BASIC_TOWER_1_LVL_BASE
    private val animation: Animation<TextureRegion> = TowerAtlas.BASIC_TOWER_1_LVL_ANIMATION

    //    private val weaponTexture = atlas.MyAtlas.BASIC_TOWER_1_LVL_WEAPON
    private var stateTime = 0f

    override fun render(batch: SpriteBatch, x: Float, y: Float) {
        stateTime += Gdx.graphics.deltaTime
        batch.draw(
            baseTexture,
            x,
            y,
            baseTexture.width.toFloat(),
            baseTexture.height.toFloat()
        )
        val weaponStateTexture = animation.getKeyFrame(stateTime)
        batch.draw(
            weaponStateTexture,
            x - 16,
            y + 24,
            weaponStateTexture.regionWidth.toFloat(),
            weaponStateTexture.regionHeight.toFloat()
        )
    }

    override fun dispose() {

    }
}
