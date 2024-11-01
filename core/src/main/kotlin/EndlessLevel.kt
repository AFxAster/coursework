import controller.Map
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.enemy.Enemy

class EndlessLevel(
    private val map: Map
) {
    fun getEnemies(): Flow<Enemy> =
        flow { // todo мб здесь сделать getNewWave и возврщать именно волну и в контроллере итерироваться по ней уже
            val newWave = Wave(
                startPoint = map.firstPathCoords,
                amount = 5,
                scaleHp = 1f,
                scaleDamage = 1f
            )
            val enemies = newWave.getEnemies()
            enemies.forEach {
                emit(it)
                delay(1000)
            }
        }
}
