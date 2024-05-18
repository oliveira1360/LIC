



private const val Coin = 0b0100_0000


fun main(){
    ler()
}

fun ler(){
    var total = 0
    while (total < 3) {
        val teste =  HAL.readBits(0b0100_0000)
        total += (teste + 1)/ 64
        //dar reset ao sinal
        println(teste)
    }
    println("fim")
}