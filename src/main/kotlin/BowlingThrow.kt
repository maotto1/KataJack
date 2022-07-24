data class BowlingThrow (
    val throwNumber: Int, // 1-3
    val pins: Int,
    val countsDouble: Boolean = false,
) {
    fun evaluate() = pins * if (countsDouble) 2 else 1

}
