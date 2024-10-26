import com.badlogic.gdx.InputAdapter
import common.SCREEN_HEIGHT
import controller.GameController

class InputAdapterImpl(
    private val controller: GameController
) : InputAdapter() {

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        controller.selectTile(screenX, SCREEN_HEIGHT - screenY)
        return false
    }
}
