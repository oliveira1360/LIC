import isel.leic.utils.Time


private const val Coin = 0b0100_0000


fun main(){
    HAL.init()
    LCD.init()
    Coin_Acceptor.read_coin()
}

object Coin_Acceptor {
    fun read_coin(): Boolean{
        val teste = HAL.readBits(Coin)
        if (teste != 0) {
            HAL.setBits(Coin)
            HAL.clrBits(Coin)
            return true
        }
        return false
    }
}
