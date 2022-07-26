data class BowlingMove1(
    val activeBonus: BowlingMoveBonus?,
    val pinsFirst: Int = -1,
    val pinsSecond: Int = -1
) {
    fun evaluate() = pinsFirst + pinsSecond + when (activeBonus) {
        BowlingMoveBonus.SPARE_BONUS -> pinsFirst
        BowlingMoveBonus.STRIKE_BONUS -> pinsFirst + pinsSecond
        null -> 0
    }

    fun isOver() =
        BowlingMoveBonus1.meritsBonus(this) == BowlingMoveBonus1.STRIKE_BONUS || pinsSecond > -1
    // TODO: evaluate -1
}

enum class BowlingMoveBonus1 {
    SPARE_BONUS,
    STRIKE_BONUS;

    fun countsDouble(throwNumber: Int) = when {
        throwNumber == 1 -> true
        throwNumber == 2 && this == STRIKE_BONUS -> true
        else -> false
    }

    companion object {
        fun meritsBonus(move: BowlingMove1) = when {
            move.pinsFirst == 10 -> STRIKE_BONUS
            move.pinsFirst + move.pinsSecond == 10 -> SPARE_BONUS
            else -> null
        }
    }
}
