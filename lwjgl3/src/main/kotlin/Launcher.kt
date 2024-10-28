import com.afxaster.cursevasya.lwjgl3.StartupHelper
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import common.SCREEN_HEIGHT
import common.SCREEN_WIDTH

fun main() {
    if (StartupHelper.startNewJvmIfRequired()) return
    Launcher().createApplication()

//    TexturePacker.process("assets/basicTower/tower", "assets/atlas", "tower");
}

class Launcher {
    fun createApplication(): Lwjgl3Application {
        return Lwjgl3Application(Main(), getDefaultConfiguration())
    }

    private fun getDefaultConfiguration(): Lwjgl3ApplicationConfiguration {
        return Lwjgl3ApplicationConfiguration().apply {
            setTitle("CurseVasya")
            useVsync(true)
            setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1)
            setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT)
            setResizable(false)
            setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
        }
    }
}
