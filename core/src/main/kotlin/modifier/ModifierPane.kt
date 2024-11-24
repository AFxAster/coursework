package modifier

import GDXTexture
import atlas.ModifierAtlas
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import common.MODIFIER_PANE_BACKGROUND_COLOR
import common.SCREEN_WIDTH

class ModifierPane {
    private val padding = 5f
    val group = Table().apply {
        setSize(ModifierAtlas.ATTACK.width * 3f + padding * 4, ModifierAtlas.ATTACK.height + padding * 2)
        pad(padding)
        val bgPixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        bgPixmap.setColor(MODIFIER_PANE_BACKGROUND_COLOR)
        bgPixmap.fill()
        background = TextureRegionDrawable(GDXTexture(bgPixmap))
        bgPixmap.dispose()

        add(Image(ModifierAtlas.ATTACK))
        add(Image(ModifierAtlas.ATTACK_SPEED)).padLeft(padding)
        add(Image(ModifierAtlas.RANGE)).padLeft(padding)
    }

    fun getClickedModifier(coordinates: Vector2): Modifier? {
        if (coordinates.y <= group.padY / 2 || coordinates.y >= group.height - padding) return null
        var x = 0f
        group.children.forEach {
            x += padding
            if (coordinates.x <= x) return null
            x += it.width
            if (coordinates.x <= x) return it.zIndex.toModifier()
        }
        return null
    }

    fun isClickInside(coordinates: Vector2): Boolean {
        return coordinates.x >= SCREEN_WIDTH - group.width && coordinates.y <= group.height
    }
}

private fun Int.toModifier(): Modifier? {
    return when (this) {
        0 -> Modifier.Attack
        1 -> Modifier.AttackSpeed
        2 -> Modifier.Range
        else -> null
    }
}

