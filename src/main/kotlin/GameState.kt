data class GameState (
    val moves: MutableList<BowlingMove> = mutableListOf(),
    val actualThrows: MutableList<BowlingThrow> = mutableListOf(),
){
    fun activeBonus() = moves.lastOrNull()?.let { BowlingMoveBonus.meritsBonus(it) }

    fun finishMove(move: BowlingMove){
        moves.add(move)
        actualThrows.clear()
    }
}