import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.scenes.scene2d.ui.Table
import controller.GameController

class GameOverInputAdapter(
    private val gameController: GameController,
    private val newGameButton: Table
) : InputAdapter() {

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
//        controller.onClick(Vector2(screenX.toFloat(), SCREEN_HEIGHT - screenY.toFloat()))
        if (newGameButton.x <= screenX && screenX <= newGameButton.x + newGameButton.width &&
            newGameButton.y <= screenY && screenY <= newGameButton.y + newGameButton.height
        )
            gameController.newGame()
        return false
    }
}
