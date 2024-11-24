import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2
import common.SCREEN_HEIGHT
import controller.GameController

class InputAdapterImpl(
    private val controller: GameController
) : InputAdapter() {

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        controller.onClick(Vector2(screenX.toFloat(), SCREEN_HEIGHT - screenY.toFloat()))
        return false
    }
}
