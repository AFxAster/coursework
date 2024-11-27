package atlas

import GDXTexture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import common.BASIC_TOWER_DEFAULT_ATTACK_SPEED

object TowerAtlas { // todo или статику сделать?)
    private const val SELECTABLE_WIDTH = 48
    private const val SELECTABLE_HEIGHT = 96

    val BASIC_TOWER_BASE: GDXTexture = GDXTexture("assets/basictower/1_lvl/base.png")
    lateinit var BASIC_TOWER_ANIMATION: Animation<TextureRegion>
        private set
    const val BASIC_TOWER_FRAME_NUMBER: Int = 5

    val BASIC_TOWER_BASE_TO_SELECT: TextureRegion = resize(BASIC_TOWER_BASE)

    fun init() {
        val basicTower1LvlAtlas = TextureAtlas(Gdx.files.internal("assets/basictower/1_lvl/basic_tower_1_lvl.atlas"))

        val frameDuration = 1 / BASIC_TOWER_DEFAULT_ATTACK_SPEED / BASIC_TOWER_FRAME_NUMBER
        BASIC_TOWER_ANIMATION =
            Animation(frameDuration, basicTower1LvlAtlas.findRegions("weapon"), Animation.PlayMode.LOOP)
    }

    fun dispose() {
        BASIC_TOWER_BASE.dispose()
    }

    private fun resize(texture: GDXTexture): TextureRegion {
        texture.textureData.prepare()
        val pixmapOriginal = texture.textureData.consumePixmap()
        val pixmapResized = Pixmap(SELECTABLE_WIDTH, SELECTABLE_HEIGHT, pixmapOriginal.format)
        pixmapResized.drawPixmap(
            pixmapOriginal,
            0, 0, pixmapOriginal.width, pixmapOriginal.height,
            0, 0, pixmapResized.width, pixmapResized.height
        )
        pixmapOriginal.dispose()
        return TextureRegion(
            GDXTexture(pixmapResized),
            0,
            (pixmapResized.height * 0.25f).toInt(),
            pixmapResized.width,
            (pixmapResized.height * 0.75f).toInt()
        )
    }
}
