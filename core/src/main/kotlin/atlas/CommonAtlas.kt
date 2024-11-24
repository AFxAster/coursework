package atlas

import GDXTexture

object CommonAtlas {
    val TILE: GDXTexture = GDXTexture("assets/common/tile.png")
    val PATH_TILE: GDXTexture = GDXTexture("assets/common/pathTile.png")

    fun dispose() {
        TILE.dispose()
        PATH_TILE.dispose()
    }
}
