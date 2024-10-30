package atlas

import GDXTexture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import common.BASIC_TOWER_DEFAULT_ATTACK_SPEED

object TowerAtlas { // todo или статику сделать?)

    val BASIC_TOWER_BASE: GDXTexture = GDXTexture("assets/basictower/1_lvl/base.png")
    lateinit var BASIC_TOWER_ANIMATION: Animation<TextureRegion>
        private set
    const val BASIC_TOWER_FRAME_NUMBER: Int = 5

    fun init() {
        val basicTower1LvlAtlas = TextureAtlas(Gdx.files.internal("assets/basictower/1_lvl/basic_tower_1_lvl.atlas"))

        val frameDuration = 1 / BASIC_TOWER_DEFAULT_ATTACK_SPEED / BASIC_TOWER_FRAME_NUMBER
        BASIC_TOWER_ANIMATION =
            Animation(frameDuration, basicTower1LvlAtlas.findRegions("weapon"), Animation.PlayMode.LOOP)
    }

    fun dispose() {
        BASIC_TOWER_BASE.dispose()
    }
}
