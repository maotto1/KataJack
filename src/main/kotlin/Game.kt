abstract class Game {

    fun play(pins: Int) = run {
        roll(pins)
        score()
        isOver()
    }

    abstract fun roll(pins: Int): Unit

    abstract fun score(): Int

    abstract fun isOver(): Boolean
}

class BowlingGame : Game() {

    private var actualMove: BowlingMove = BowlingMove(activeBonus = null)

    private var score = 0

    override fun roll(pins: Int) {
        updateScore(pins)
        actualMove = updateMove(pins)
    }

    private fun updateScore(pins: Int) = score + actualMove.evaluate()

    private fun updateMove(pins: Int) = when {
        actualMove.pinsFirst == -1 && !isStrike(pins) ->
            actualMove.copy(pinsFirst = pins)
        actualMove.pinsFirst == -1 -> BowlingMove(activeBonus = BowlingMoveBonus.STRIKE_BONUS)
        actualMove.pinsSecond == -1 ->
            actualMove.copy(pinsSecond = pins)
        else -> BowlingMove(activeBonus = BowlingMoveBonus.meritsBonus(actualMove))
    }

    private fun isStrike(pins: Int): Boolean = pins == 10

    override fun score(): Int {
        return score
    }

    override fun isOver(): Boolean {
        TODO("Not yet implemented")
    }
}
