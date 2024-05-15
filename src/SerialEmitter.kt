import isel.leic.utils.Time


fun main() {

    LCD.init()
    HAL.init()
    SerialEmitter.init()
    while(true){

    SerialEmitter.send(SerialEmitter.Destination.LCD,0x155,9)
        Time.sleep(100)
    }
}


object SerialEmitter { // Envia tramas para os diferentes módulos Serial Receiver.

    val SCLK = 0x10 //10 sim    02 placa
    val SDX = 0x08 //08 sim     04 placa

    enum class Destination {LCD, SCORE}

    // Inicia a classe
    fun init() {

    }

    // Envia uma trama para o SerialReceiver identificado o destino em addr,os bits de dados em ‘data’ e em size o número de bits a enviar.
    fun send(addr: Destination, data: Int, size : Int) {
        //bit, 0 = par, 1 = impar

        val LCDsel  = if (addr == Destination.LCD) 0x01 else 0x02



        var valor = 0
        var left = data
        while (left != 0) {
            valor = valor xor (left and 1)
            left = left shr 1 // Right shift para conseguir o proximo bit
        }

        val paridade = if (valor == 1) 1 else 0

        //val withParaty =  data

        var mandar = data


        HAL.clrBits(LCDsel)


        for (i in size  downTo 1) {
                    if (mandar%2 != 0)
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
            clock()
            HAL.setBits(LCDsel)

    }

    fun clock(){
        HAL.setBits(SCLK) //clock
        HAL.clrBits(SCLK)
    }


}