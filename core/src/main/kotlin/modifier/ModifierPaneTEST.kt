package modifier

import GDXTexture
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import common.MODIFIER_PANE_BACKGROUND_COLOR
import common.SCREEN_WIDTH
import common.Texture
import common.plus


class ModifierPaneTEST : Texture {
    private val modifierTextures: List<ModifierTexture> =
        listOf(AttackModifierTexture(), AttackSpeedModifierTexture(), RangeModifierTexture())
    private val padding = 5
    override val originalWidth: Int =
        modifierTextures.sumOf { it.originalWidth } + (modifierTextures.size + 1) * padding
    override val originalHeight: Int = modifierTextures.maxOf { it.originalHeight } + 2 * padding
    override val textureCenter = Vector2(originalWidth / 2f, originalHeight / 2f)
    private val rectangle by lazy {
        val pixmap = Pixmap(originalWidth, originalHeight, Pixmap.Format.RGBA8888)
        pixmap.setColor(MODIFIER_PANE_BACKGROUND_COLOR)
        pixmap.fill()
        GDXTexture(pixmap)
    }

    override fun render(batch: SpriteBatch, coordinates: Vector2) {
        batch.draw(rectangle, coordinates.x, coordinates.y)
        var x = padding.toFloat()
        modifierTextures.forEach {
            it.render(batch, coordinates + Vector2(x, padding.toFloat()))
            x += it.originalWidth + padding
        }
    }

    fun getClickedModifier(coordinates: Vector2): Modifier? {
        if (coordinates.y <= padding || coordinates.y >= originalHeight - padding) return null
        var x = 0
        modifierTextures.forEach {
            x += padding
            if (coordinates.x <= x) return null
            x += it.originalWidth
            if (coordinates.x <= x) return it.toModifier()
        }
        return null
    }

    fun isClickInside(coordinates: Vector2): Boolean {
        return coordinates.x >= SCREEN_WIDTH - originalWidth && coordinates.y <= originalHeight
    }

    override fun dispose() {
        rectangle.dispose()
        modifierTextures.forEach { it.dispose() }
    }
}

private fun ModifierTexture.toModifier(): Modifier? {
    return when (this) {
        is AttackModifierTexture -> Modifier.Attack
        is AttackSpeedModifierTexture -> Modifier.AttackSpeed
        is RangeModifierTexture -> Modifier.Range
        else -> null
    }
}
