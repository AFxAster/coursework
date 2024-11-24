package atlas

import GDXTexture

object CommonAtlas {
    val TILE: GDXTexture = GDXTexture("assets/common/tile.png")
    val PATH_TILE: GDXTexture = GDXTexture("assets/common/pathTile.png")
    val COIN: GDXTexture = GDXTexture("assets/common/ic_coin.png")

    fun dispose() {
        TILE.dispose()
        PATH_TILE.dispose()
        COIN.dispose()
    }
}
