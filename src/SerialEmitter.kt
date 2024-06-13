import isel.leic.utils.Time


fun main() {

    LCD.init()
    HAL.init()
    SerialEmitter.init()
    while(true){

    SerialEmitter.send(SerialEmitter.Destination.LCD,0x155,9)
        Time.sleep(10)
    }
}


object SerialEmitter { // Envia tramas para os diferentes módulos Serial Receiver.

    private const val SCLK = 0x02
    private const val SDX = 0x04
    private const val LCD_ADDR = 0x01
    private const val SCORE_ADDR = 0x08


    enum class Destination {LCD, SCORE}

    // Inicia a classe
    fun init() {
        HAL.init()
    }

    // Envia uma trama para o SerialReceiver identificado o destino em addr,os bits de dados em ‘data’ e em size o número de bits a enviar.
    fun send(addr: Destination, data: Int, size : Int) {

        val LCDsel  = if (addr == Destination.LCD) LCD_ADDR else SCORE_ADDR

        var valor = 0
        var left = data
        while (left != 0) {
            valor = valor xor (left and 1)
            left = left shr 1 // Right shift para conseguir o proximo bit
        }

        val paridade = if (valor == 1) 1 else 0 //bit, 0 = par, 1 = impar

        var send = data

        HAL.clrBits(LCDsel)

        for (i in size  downTo 1) {
            if (send%2 != 0) HAL.setBits(SDX) else HAL.clrBits(SDX)
            send = send.shr(1)//para ver o proximo bit
            clock()
        }
        if (paridade == 1) HAL.setBits(SDX) else HAL.clrBits(SDX)
            clock()
            HAL.setBits(LCDsel)

    }

    fun clock(){
        HAL.setBits(SCLK)
        HAL.clrBits(SCLK)
    }


}