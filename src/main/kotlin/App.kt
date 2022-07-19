fun main(args: Array<String>) {
    val house = House().recite()
    val echoHouse = EchoHouse().recite()
    println(house)
    println("--------------------------------------------")
    println(echoHouse)
}
