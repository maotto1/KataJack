

class House {
    /**
     * List of elements of the poem
     */
    private var elements = arrayOf(
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

    private val sentenceBeginning = "This is"

    /**
     * Returns the full Poem as a String
     */
    fun recite(): String? {
//        prepareData().reverse().stringConcat()
        return (1..elements.size).mapIndexed{ index, _ ->
            sentenceBeginning + constructPhrase(index)
        }.reduce{acc, phrase -> acc + "\n\n" + phrase}
    }

    private fun prepareData() = elements.also { it[it.size-1] = it.last() + "." }

    private fun constructPhrase(index: Int): String{
        if (index >= elements.size){
            return "."
        }
        return " " + elements.getOrElse(index) { "" } + constructPhrase(index+1)
    }
}