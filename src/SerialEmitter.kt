import isel.leic.utils.Time


const val CHECK_P = 0
const val MASK_SEND = 0x01
fun main() {

    HAL.init()
    SerialEmitter.init()
    while(true){

    SerialEmitter.send(SerialEmitter.Destination.LCD,0x155,9)
        Time.sleep(100)
    }
}


object SerialEmitter { // Envia tramas para os diferentes módulos Serial Receiver.

    val LCDsel = 0x01
    val SCLK = 0x02 //10
    val SDX = 0x04 //08

    enum class Destination {LCD, SCORE}

    // Inicia a classe
    fun init() {


        HAL.setBits(LCDsel)

     //   send(Destination.LCD, 0b0101_0101_0,9)

    }

    // Envia uma trama para o SerialReceiver identificado o destino em addr,os bits de dados em ‘data’ e em size o número de bits a enviar.
    fun send(addr: Destination, data: Int, size : Int) {

        //usar o hal
        //pit paridade depende se o numero de 1 e par ou impar 0 = par, 1 = impar
        //o size limita o tamnho data a ser enviado

        //nada

        var valor = 0
        var left = data
        while (left != 0) {
            valor = valor xor (left and 1)
            left = left shr 1 // Right shift to get next bit
        }

        val paridade = if (valor == 1) 1 else 0

        val withParaty =  data

        var mandar = withParaty


        HAL.clrBits(LCDsel)

        val rs = if (data % 2 == 0) 0 else 1
        if (rs == 1)
            HAL.setBits(SDX)
        else
            HAL.clrBits(SDX)
        //println("    rs: " + rs + "  fim")
        clock()

        var send: Int
        for (i in size - 1 downTo 1) {
                mandar = mandar.shr(1)
                send = mandar and MASK_SEND
          //      println("    dado: " + send + "  fim")
                    if (send == 1)
                        HAL.setBits(SDX)
                    else
                        HAL.clrBits(SDX)

                clock()
            }
        //print("    paridade: " + paridade + "  fim")
            if (paridade == 1)
                HAL.setBits(SDX)
            else
                HAL.clrBits(SDX)
            clock()
            // I = size
            HAL.setBits(LCDsel)

    }

    fun clock(){
        HAL.setBits(SCLK) //clock
        HAL.clrBits(SCLK)
    }


}