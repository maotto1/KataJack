sealed class BowlingMove(open val activeBonus: BowlingMoveBonus? = null) {
    abstract fun evaluate(): Int
    abstract fun isOver(): Boolean
    abstract fun terminate(): BowlingMove
}
data class NormalBowlingMove(
    override val activeBonus: BowlingMoveBonus?,
    val pinsFirst: Int = -1,
    val pinsSecond: Int = -1
): BowlingMove(activeBonus) {
    override fun evaluate() = pinsFirst + pinsSecond + when (activeBonus) {
        BowlingMoveBonus.SPARE_BONUS -> pinsFirst
        BowlingMoveBonus.STRIKE_BONUS -> pinsFirst + pinsSecond
        null -> 0
    }

    override fun isOver() =
        BowlingMoveBonus.meritsBonus(this) == BowlingMoveBonus.STRIKE_BONUS || pinsSecond > -1

    override fun terminate() = copy(
            pinsFirst = if (pinsFirst >= 0) pinsFirst else 0,
            pinsSecond = if (pinsSecond >= 0) pinsSecond else 0,
        )
}

data class LastSpareBonusMove(
    val pins: Int = -1,
) : BowlingMove() {

    override fun isOver() =
        pins != -1

    override fun evaluate() = pins

    override fun terminate() = this

}

data class LastStrikeBonusMove(
    val pinsFirst: Int = -1,
    val pinsSecond: Int = -1,
) : BowlingMove() {

    override fun isOver() =
        pinsSecond != -1

    override fun evaluate() = pinsFirst + pinsSecond

    override fun terminate() = copy(
        pinsFirst = if (pinsFirst >= 0) pinsFirst else 0,
        pinsSecond = if (pinsSecond >= 0) pinsSecond else 0,
    )

}

enum class BowlingMoveBonus {
    SPARE_BONUS,
    STRIKE_BONUS;

    fun countsDouble(throwNumber: Int) = when {
        throwNumber == 1 -> true
        throwNumber == 2 && this == STRIKE_BONUS -> true
        else -> false
    }

    companion object {
        fun meritsBonus(move: BowlingMove) = when {
            move !is NormalBowlingMove -> null
            move.pinsFirst == 10 -> STRIKE_BONUS
            move.pinsFirst + move.pinsSecond == 10 -> SPARE_BONUS
            else -> null
        }
    }
}
