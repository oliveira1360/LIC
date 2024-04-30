import java.sql.Time


const val CHECK_P = 0
const val MASK_SEND = 0x01
fun main() {

   SerialEmitter.init()

}


object SerialEmitter { // Envia tramas para os diferentes módulos Serial Receiver.

    val LCDsel = 0x01
    val SCLK = 0x02
    val SDX = 0x04

    enum class Destination {LCD, SCORE}

    // Inicia a classe
    fun init() {

        send(Destination.LCD, 0b0111_0110,9)

    }

    // Envia uma trama para o SerialReceiver identificado o destino em addr,os bits de dados em ‘data’ e em size o número de bits a enviar.
    fun send(addr: Destination, data: Int, size : Int) {

        //usar o hal
        //pit paridade depende se o numero de 1 e par ou impar 0 = par, 1 = impar
        //o size limita o tamnho data a ser enviado



        var valor = 0
        var left = data
        while (left != 0) {
            valor = valor xor (left and 1)
            left = left shr 1 // Right shift to get next bit
        }

        val paridade = if (valor == 1) 1 else 0

        val withParaty =  data.shl(1)

        var mandar = withParaty


        HAL.clrBits(LCDsel)

        val rs = data.shr(size)
        if (rs == 1)
            HAL.setBits(SDX)
        else
            HAL.clrBits(SDX)

            for (i in size - 1 downTo 1) {
                 val send = mandar and MASK_SEND
                    if (send == 1)
                        HAL.setBits(SDX)
                    else
                        HAL.clrBits(SDX)
                mandar = mandar.shr(1)
                clock()
            }
            if (paridade == 1)
                HAL.setBits(SDX)
            else
                HAL.clrBits(SDX)
            // I = size
            HAL.setBits(LCDsel)

    }

    fun clock(){
        isel.leic.utils.Time.sleep(10)
        HAL.setBits(SCLK) //clock
        isel.leic.utils.Time.sleep(10)
        HAL.clrBits(SCLK)
    }


}