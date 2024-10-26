package atlas

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array

object EnemyAtlas {
    lateinit var SCORPION_MOVING_LEFT_ANIMATION: Animation<TextureRegion>
        private set
    lateinit var SCORPION_MOVING_RIGHT_ANIMATION: Animation<TextureRegion>
        private set
    lateinit var SCORPION_MOVING_UP_ANIMATION: Animation<TextureRegion>
        private set
    lateinit var SCORPION_MOVING_DOWN_ANIMATION: Animation<TextureRegion>
        private set

    fun init() {
        val frameDuration = 1 / 8f
        val scorpionAtlas = TextureAtlas(Gdx.files.internal("assets/enemy/scorpion/scorpion.atlas"))
        SCORPION_MOVING_LEFT_ANIMATION =
            Animation<TextureRegion>(frameDuration, scorpionAtlas.findRegions("movingLeft"), Animation.PlayMode.LOOP)

        val scorpionMovingRightArray =
            Array<TextureRegion>(scorpionAtlas.findRegions("movingLeft").map {
                it.apply { flip(true, false) }
            }.toTypedArray())
        SCORPION_MOVING_RIGHT_ANIMATION =
            Animation<TextureRegion>(frameDuration, scorpionMovingRightArray, Animation.PlayMode.LOOP)

        SCORPION_MOVING_UP_ANIMATION =
            Animation<TextureRegion>(frameDuration, scorpionAtlas.findRegions("movingUp"), Animation.PlayMode.LOOP)

        SCORPION_MOVING_DOWN_ANIMATION =
            Animation<TextureRegion>(frameDuration, scorpionAtlas.findRegions("movingDown"), Animation.PlayMode.LOOP)
    }

    fun dispose() {}
}
