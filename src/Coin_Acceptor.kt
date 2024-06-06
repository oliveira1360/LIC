import isel.leic.utils.Time


private const val Coin = 0b0100_0000
private const val ECoin = 0b01000_0000



fun main(){
    HAL.init()
    LCD.init()

        Coin_Acceptor.read_coin()

}

object Coin_Acceptor {
    var flag = false
    fun read_coin(): Boolean{

        if (HAL.isBit(Coin)) {
            HAL.setBits(Coin)
            flag = true
        } else if (!HAL.isBit(Coin) && flag){
            HAL.clrBits(Coin)
            flag = false
            return true
        }



        return false

    }
    fun seeCoin(): Boolean{
        if (HAL.isBit(Coin)){
            return true
        }
        return false

    }
}

