package modifier

sealed interface Modifier {
    data object Attack : Modifier
    data object AttackSpeed : Modifier
    data object Range : Modifier
}
