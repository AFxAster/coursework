package model.enemy

sealed interface Direction {
    data object Left : Direction
    data object Right : Direction
    data object Up : Direction
    data object Down : Direction
}
