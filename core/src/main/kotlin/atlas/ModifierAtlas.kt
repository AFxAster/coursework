package atlas

import GDXTexture
import com.badlogic.gdx.graphics.Pixmap

object ModifierAtlas {
    private const val ICON_SIZE = 100
    val ATTACK: GDXTexture = resize(GDXTexture("assets/common/ic_attack.png"))
    val ATTACK_SPEED: GDXTexture = resize(GDXTexture("assets/common/ic_attack_speed.png"))
    val RANGE: GDXTexture = resize(GDXTexture("assets/common/ic_range.png"))

    fun dispose() {
        ATTACK.dispose()
        ATTACK_SPEED.dispose()
        RANGE.dispose()
    }

    private fun resize(texture: GDXTexture): GDXTexture {
        texture.textureData.prepare()
        val pixmapOriginal = texture.textureData.consumePixmap()
        val pixmapResized = Pixmap(ICON_SIZE, ICON_SIZE, pixmapOriginal.format)
        pixmapResized.drawPixmap(
            pixmapOriginal,
            0, 0, pixmapOriginal.width, pixmapOriginal.height,
            0, 0, pixmapResized.width, pixmapResized.height
        )
        pixmapOriginal.dispose()
        return GDXTexture(pixmapResized)
    }
}
