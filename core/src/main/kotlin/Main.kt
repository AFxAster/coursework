import atlas.CommonAtlas
import atlas.EnemyAtlas
import atlas.TowerAtlas
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils
import controller.GameController

typealias GDXTexture = com.badlogic.gdx.graphics.Texture

class Main : ApplicationAdapter() {
    private val batch: SpriteBatch by lazy { SpriteBatch() }
    private val controller by lazy { GameController() }

    override fun create() {
        super.create()
        TowerAtlas.init()
        EnemyAtlas.init()
        Gdx.input.inputProcessor = InputAdapterImpl(controller)
    }

    override fun render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        batch.begin()
        controller.renderMap(batch)
        controller.render(batch)
        batch.end()

    }

    override fun dispose() {
        batch.dispose()
        CommonAtlas.dispose()
        TowerAtlas.dispose()
        EnemyAtlas.dispose()
        controller.dispose()
    }
}
