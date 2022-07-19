

open class House {
    /**
     * List of elements of the poem
     */
    protected val textBricks = arrayOf(
        "the horse and the hound and the horn that belonged to",
        "the farmer sowing his corn that kept",
        "the rooster that crowed in the morn that woke",
        "the priest all shaven and shorn that married",
        "the man all tattered and torn that kissed",
        "the maiden all forlorn that milked",
        "the cow with the crumpled horn that tossed",
        "the dog that worried",
        "the cat that killed",
        "the rat that ate",
        "the malt that lay in",
        "the house that jack build"
    )

    protected var elements = textBricks

    private val sentenceBeginning = "This is"

    /**
     * Returns the full Poem as a String
     */
    fun recite(): String {
        sortElements()
        return (1..elements.size).mapIndexed { index, _ ->
            sentenceBeginning + constructPhrase(index)
        }.reduce { acc, phrase -> acc + "\n\n" + phrase }.plus("\n")
    }

    protected open fun sortElements() = Unit

    private fun constructPhrase(index: Int): String {
        if (index >= elements.size) {
            return "."
        }
        return constructPhraseMiddle(index) + constructPhrase(index + 1)
    }

    protected open fun constructPhraseMiddle(index: Int) = " " + elements.getOrElse(index) { "" }
}

class EchoHouse : House() {
    override fun constructPhraseMiddle(index: Int): String {
        return super.constructPhraseMiddle(index) + super.constructPhraseMiddle(index)
    }
}

class RandomHouse : House() {
    override fun sortElements() {
        elements = textBricks.toList().shuffled().toTypedArray()
    }
}
