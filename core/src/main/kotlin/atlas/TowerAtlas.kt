package atlas

import GDXTexture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

object TowerAtlas { // todo или статику сделать?)

    val BASIC_TOWER_1_LVL_BASE: GDXTexture = GDXTexture("assets/basictower/1_lvl/base.png")
    lateinit var BASIC_TOWER_1_LVL_PROJECTILE: TextureRegion
        private set
    lateinit var BASIC_TOWER_1_LVL_ANIMATION: Animation<TextureRegion>
        private set

    fun init() {
        val frameDuration = 1 / 6f
        val basicTower1LvlAtlas = TextureAtlas(Gdx.files.internal("assets/basictower/1_lvl/basic_tower_1_lvl.atlas"))
        BASIC_TOWER_1_LVL_ANIMATION =
            Animation(frameDuration, basicTower1LvlAtlas.findRegions("weapon"), Animation.PlayMode.LOOP)
    }

    fun dispose() {
        BASIC_TOWER_1_LVL_BASE.dispose()
    }
}
