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

class BowlingGame(private var gameState: GameState = GameState()) : Game() {

    private var score = 0

    private var actualMove: BowlingMove = NormalBowlingMove(activeBonus = null)

    init {
        actualMove = throwsToMove(gameState.actualThrows, gameState.activeBonus())
        score = previousMovesScore() + actualMoveIntermediateScore()
    }

    private fun actualMoveIntermediateScore() = gameState.actualThrows.sumOf { it.evaluate() } ?: 0

    private fun previousMovesScore() = gameState.moves.sumOf { it.evaluate() } ?: 0

    override fun roll(pins: Int) {
        println("new throw: $pins played,")
        handleThrow(pins)
        handleMove(pins)
//        actualMove = updateMove(pins)
//        actualMove.isOver()
    }

    private fun throwsToMove(bowlingThrows: List<BowlingThrow>, activeBonus: BowlingMoveBonus?) =
        NormalBowlingMove(
            activeBonus = activeBonus,
            pinsFirst = bowlingThrows.firstOrNull()?.pins ?: -1,
            pinsSecond = bowlingThrows.getOrNull(1)?.pins ?: -1
        )

    private fun throwsToBonusMove(bowlingThrows: List<BowlingThrow>) = when (actualMove) {
        is LastSpareBonusMove -> LastSpareBonusMove(pins = bowlingThrows.firstOrNull()?.pins ?: -1)
        is LastStrikeBonusMove ->
            LastStrikeBonusMove(
                pinsFirst = bowlingThrows.firstOrNull()?.pins ?: -1,
                pinsSecond = bowlingThrows.getOrNull(1)?.pins ?: -1
            )
        else -> throwsToMove(bowlingThrows, activeBonus = null)
    }

    private fun handleThrow(pins: Int) {
        val newThrow = createThrow(pins)
        gameState.actualThrows.add(newThrow)
    }

    private fun createThrow(pins: Int): BowlingThrow {
        val number = (gameState.actualThrows.size) + 1
        return BowlingThrow(
            throwNumber = number,
            pins = pins,
            countsDouble = actualMove.activeBonus?.countsDouble(number) ?: false
        )
    }

    private fun handleMove(pins: Int) = when (actualMove) {
        is NormalBowlingMove -> {
            handleNormalMove(actualMove as NormalBowlingMove)
        }
        is LastSpareBonusMove, is LastStrikeBonusMove -> {
            actualMove = throwsToBonusMove(gameState.actualThrows)
            if (actualMove.isOver()) {
                gameState.finishMove(actualMove)
            }
            println(" \t score is now ${score()} and the game is  ${isOver()}.\n")
        }
        else -> null
    }

    private fun handleNormalMove(move: NormalBowlingMove) {
        actualMove = throwsToMove(gameState.actualThrows, activeBonus = move.activeBonus)
        println(" \t score is now ${score()} and the game is  ${isOver()}.\n")
        if (actualMove.isOver()) {
            gameState.finishMove(actualMove)
            if (isOver()) return
            actualMove = if (wasLast()) {
                createBonusMove(lastMove = actualMove as NormalBowlingMove) ?: NormalBowlingMove(activeBonus = null)
            } else createMove(lastMove = actualMove as NormalBowlingMove)
            println("new move after this \n -------------------------------------------------")
        }
    }

//    private fun handleBonusMove

    private fun createBonusMove(lastMove: NormalBowlingMove) = when (BowlingMoveBonus.meritsBonus(lastMove)) {
        BowlingMoveBonus.STRIKE_BONUS -> LastStrikeBonusMove()
        BowlingMoveBonus.SPARE_BONUS -> LastSpareBonusMove()
        else -> null
    }

    private fun createMove(lastMove: NormalBowlingMove) = NormalBowlingMove(activeBonus = BowlingMoveBonus.meritsBonus(lastMove))

    private fun updateScore(pins: Int) = score + actualMove.evaluate()

//    private fun updateMove(pins: Int) = when {
//        actualMove.pinsFirst == -1 && !isStrike(pins) ->
//            actualMove.copy(pinsFirst = pins)
//        actualMove.pinsFirst == -1 -> BowlingMove(activeBonus = BowlingMoveBonus.STRIKE_BONUS)
//        actualMove.pinsSecond == -1 ->
//            actualMove.copy(pinsSecond = pins)
//        else -> BowlingMove(activeBonus = BowlingMoveBonus.meritsBonus(actualMove))
//    }

    private fun wasLast() = gameState.moves.size == 10

    override fun score(): Int {
        return previousMovesScore() + actualMoveIntermediateScore()
    }

    override fun isOver(): Boolean {
        return gameState.moves.size > 10
    }
}
