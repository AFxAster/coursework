import com.badlogic.gdx.math.Vector2
import common.TILE_SIZE
import model.enemy.Direction
import model.enemy.Enemy
import model.enemy.Scorpion
import model.enemy.ScorpionTexture

class Wave(
    private val startPoint: Vector2,
    val amount: Int,
    val scaleHp: Float,
    val scaleDamage: Float
) {
    fun getEnemies(): List<Enemy> {
        val enemies = mutableListOf<Enemy>()
        repeat(amount) {
            val enemy = Scorpion(
                Vector2((startPoint.x - 1) * TILE_SIZE, startPoint.y * TILE_SIZE),
                ScorpionTexture(Direction.Right)
            ).apply {
                hp *= scaleHp
                damage *= scaleDamage
            }
            enemies.add(enemy)
        }
        println(enemies)
        return enemies
    }
}
