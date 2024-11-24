package modifier

import GDXTexture
import atlas.ModifierAtlas
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

class AttackSpeedModifierTexture : ModifierTexture {

    private val texture: GDXTexture = ModifierAtlas.ATTACK_SPEED
    override val originalWidth: Int = texture.width
    override val originalHeight: Int = texture.height
    override val textureCenter: Vector2 = Vector2(originalWidth / 2f, originalHeight / 2f)

    override fun render(batch: SpriteBatch, coordinates: Vector2) {
        batch.draw(texture, coordinates.x, coordinates.y)
    }

    override fun dispose() {
        texture.dispose()
    }
}
