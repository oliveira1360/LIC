import java.sql.Time



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
        HAL.clrBits(0b1111_1111)
        send(Destination.LCD, 0b1_0000_0000,10)

    }

    // Envia uma trama para o SerialReceiver identificado o destino em addr,os bits de dados em ‘data’ e em size o número de bits a enviar.
    fun send(addr: Destination, data: Int, size : Int) {

        //usar o hal
        //pit paridade depende se o numero de 1 e par ou impar 0 = par, 1 = impar
        //o size limita o tamnho data a ser enviado

        val dataInBinary = decimalToBinary(data)
        var dataSend = dataInBinary



        var valor = dataInBinary[dataInBinary.length -1].toString().toInt()
        for (i in dataInBinary.length -2 downTo 1) {
            valor = dataInBinary[i].toString().toInt().xor(valor)
        }

        if (dataInBinary.length < size){
            val diff = size - dataSend.length - 1
            var temp = dataSend
            dataSend = ""
            for (i in 0 until  diff) dataSend += "0"
            dataSend += temp
        }
        val novo = if (valor == 1) 0 else 1
        dataSend += novo



        //redefeniri o LCD e SClk
        HAL.setBits(LCDsel)
        //isel.leic.utils.Time.sleep(20)
        HAL.clrBits(LCDsel)

        if (Destination.LCD == addr) {

            for (i in 0 until size) {
                    isel.leic.utils.Time.sleep(100)
                val teste  = dataSend[i]
                    if (dataSend[i] == '1')
                        HAL.setBits(SDX)
                    else
                        HAL.clrBits(SDX)

                clock()
            }
            // I = size
            HAL.setBits(LCDsel)



        }
        else if (Destination.SCORE == addr){
            for (i in 0 until size) {
                if (dataSend[i].code == 1)
                    HAL.setBits(SDX)
                else
                    HAL.clrBits(SDX)

                clock()
            }
            HAL.clrBits(LCDsel)
            HAL.setBits(SCLK)
            clock()
        }
    }

    fun decimalToBinary(n: Int): String {
        val intList = mutableListOf<Int>()
        var decimalNumber = n

        while (decimalNumber > 0) {
            intList.add(decimalNumber % 2)
            decimalNumber /= 2
        }
        return intList.reversed().joinToString("")
    }

    fun clock(){
        isel.leic.utils.Time.sleep(10)
        HAL.setBits(SCLK) //clock
        isel.leic.utils.Time.sleep(10)
        HAL.clrBits(SCLK)
    }


}