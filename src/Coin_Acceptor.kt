import isel.leic.utils.Time


private const val Coin = 0b0100_0000

fun main() {
    HAL.init()
    LCD.init()
    Coin_Acceptor.readCoin()
}

object Coin_Acceptor {

    fun readCoin() {
        val teste = HAL.readBits(Coin)
        if (teste != 0) {
            HAL.setBits(Coin)
            Time.sleep(2)
            HAL.clrBits(Coin)
        }

    }
}
