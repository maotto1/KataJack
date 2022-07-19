fun main(args: Array<String>) {
    val bowlingGame = BowlingGame()

    val rolls = listOf(1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10, 2, 8, 6)

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
