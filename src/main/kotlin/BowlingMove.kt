data class BowlingMove(
    val activeBonus: BowlingMoveBonus?,
    val pinsFirst: Int = -1,
    val pinsSecond: Int = -1
) {
    fun evaluate() = pinsFirst + pinsSecond + when (activeBonus) {
        BowlingMoveBonus.SPARE_BONUS -> pinsFirst
        BowlingMoveBonus.STRIKE_BONUS -> pinsFirst + pinsSecond
        null -> 0
    }
    // TODO: evaluate -1
}

enum class BowlingMoveBonus {
    SPARE_BONUS,
    STRIKE_BONUS;

    companion object {
        fun meritsBonus(move: BowlingMove) = when {
            move.pinsFirst == 10 -> STRIKE_BONUS
            move.pinsFirst + move.pinsSecond == 10 -> SPARE_BONUS
            else -> null
        }
    }
}
