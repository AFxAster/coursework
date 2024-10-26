package atlas

import GDXTexture

object CommonAtlas {
    val TILE: GDXTexture = GDXTexture("tile.png")
    val PATH_TILE: GDXTexture = GDXTexture("pathTile.png")

    fun dispose() {
        TILE.dispose()
        PATH_TILE.dispose()
    }
}
