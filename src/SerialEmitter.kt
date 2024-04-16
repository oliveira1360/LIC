
const val CLK_REG_MASK = 0x10

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
        send(Destination.SCORE, 0b1_1100_1000,10)

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



        if (Destination.SCORE == addr) {
            for (i in 0 until size + 1) {
                if ( i == size){
                    HAL.setBits(LCDsel)
                    HAL.clrBits(SCLK)
                    HAL.clrBits(SDX)
                }
                if (i == 0) {
                    HAL.clrBits(LCDsel)
                    HAL.setBits(SCLK)
                    if (dataSend[i].code == 1)
                        HAL.setBits(SDX)
                    else
                        HAL.clrBits(SDX)
                }
                if (i > 0 && i < size) {
                    if (dataSend[i].code == 1)
                        HAL.setBits(SDX)
                    else
                        HAL.clrBits(SDX)
                }
                HAL.setBits(CLK_REG_MASK) //clock
                HAL.clrBits(CLK_REG_MASK)
            }



        }
        else{
            for (i in 0 until size)
                HAL.setBits(dataSend[i].code)
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


}