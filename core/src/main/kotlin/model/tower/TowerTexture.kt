package model.tower

import common.Texture

interface TowerTexture : Texture {
    var rotation: Float
    var playAnimation: Boolean
    var showRadius: Boolean
}
