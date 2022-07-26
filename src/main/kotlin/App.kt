fun main(args: Array<String>) { // ktlint-disable filename
    val bowlingGame = BowlingGame()

    val rolls = listOf(1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10, 2, 8, 6)
    val testData = listOf(4, 2, 8, 2, 6, 3, 10, 3, 7, 0, 8, 10, 10, 7, 0, 5, 5, 1)

    rolls.map { pins ->
        {
            bowlingGame.play(pins)
            println(
                "new move: $pins played, score is now ${bowlingGame.score()} and the game is " + if (bowlingGame.isOver()) "not " else "" + "over."
            )
        }
    }

    println("--------------------------------------------")
}
