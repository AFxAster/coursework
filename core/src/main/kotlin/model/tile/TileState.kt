package model.tile

import GDXTexture
import atlas.CommonAtlas

sealed class TileState(
    val texture: GDXTexture
) {
    data object Path : TileState(CommonAtlas.PATH_TILE)
    data object Empty : TileState(CommonAtlas.TILE)
    data object Occupied : TileState(CommonAtlas.TILE)
}
