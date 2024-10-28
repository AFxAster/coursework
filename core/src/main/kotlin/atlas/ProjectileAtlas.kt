package atlas

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

object ProjectileAtlas {

    lateinit var ARROW_1_LVL_ANIMATION: Animation<TextureRegion>
        private set

    fun init() {
        val frameDuration = 1 / 3f
        val basicTower1LvlAtlas = TextureAtlas(Gdx.files.internal("assets/basictower/1_lvl/basic_tower_1_lvl.atlas"))
        ARROW_1_LVL_ANIMATION =
            Animation(frameDuration, basicTower1LvlAtlas.findRegions("projectile"), Animation.PlayMode.LOOP)
    }

    fun dispose() {

    }
}
