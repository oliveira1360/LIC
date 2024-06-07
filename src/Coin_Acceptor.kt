private const val COIN_MASK = 0b0100_0000
fun main(){
    HAL.init()
    LCD.init()
    Coin_Acceptor.read_coin()
}

object Coin_Acceptor {
    var coinIn = false
    fun read_coin(): Boolean{

        if (HAL.isBit(COIN_MASK)) {
            HAL.setBits(COIN_MASK)
            coinIn = true
        } else if (!HAL.isBit(COIN_MASK) && coinIn){
            HAL.clrBits(COIN_MASK)
            coinIn = false
            return true
        }
        return false
    }
    fun seeCoin(): Boolean = HAL.isBit(COIN_MASK)
}

