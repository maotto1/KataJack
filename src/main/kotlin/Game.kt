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

class BowlingGame : Game(private var gameState: GameState = GameState()) {

    private var actualMove: BowlingMove = BowlingMove(activeBonus = null)

    private var score = 0

    init {
        actualMove = throwsToMove(gameState.actualThrows, gameState.activeBonus())
        score = previousMovesScore() + actualMoveIntermediateScore()
    }

    private fun actualMoveIntermediateScore() = gameState.actualThrows.sumOf { it.evaluate() } ?: 0

    private fun previousMovesScore() = gameState.moves.sumOf { it.evaluate() } ?: 0

    override fun roll(pins: Int) {
        handleThrow(pins)
        handleMove(pins)
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

    private fun throwsToMove(bowlingThrows: List<BowlingThrow>, activeBonus: BowlingMoveBonus?) =
        BowlingMove(
            activeBonus = activeBonus,
            pinsFirst = bowlingThrows.firstOrNull()?.pins ?: -1,
            pinsSecond = bowlingThrows.getOrNull(1)?.pins ?: -1,
        )

    private fun handleThrow(pins: Int){
        val newThrow = createThrow(pins)
        gameState.actualThrows.add(newThrow)
    }

    private fun createMove(lastMove: BowlingMove) = BowlingMove(activeBonus = BowlingMoveBonus.meritsBonus(lastMove))

    private fun createThrow(pins: Int): BowlingThrow {
        val number = (gameState.actualThrows.size) +1
        return BowlingThrow(
            throwNumber = number,
            pins = pins,
            countsDouble = actualMove.activeBonus?.countsDouble(number) ?: false
        )
    }

    private fun handleMove(pins: Int){
        actualMove = throwsToMove(gameState.actualThrows, activeBonus = actualMove.activeBonus)
        if (actualMove.isOver()){
            gameState.finishMove(actualMove)
            actualMove = createMove(lastMove = actualMove)
            println("new move after this")
        }
    }


    private fun isStrike(pins: Int): Boolean = pins == 10

    override fun score(): Int {
        return previousMovesScore() + actualMoveIntermediateScore()
    }

    override fun isOver(): Boolean {
        return gameState.moves.size < 11
    }
}
