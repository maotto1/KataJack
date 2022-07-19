fun main(args: Array<String>) {
    val house = House().recite()
    val echoHouse = EchoHouse().recite()
    val randomHouse = RandomHouse().recite()

    println(house)
    println("--------------------------------------------")
    println(echoHouse)
    println("--------------------------------------------")
    println(randomHouse)
}
