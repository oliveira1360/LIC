
import isel.leic.UsbPort
import isel.leic.utils.Time

fun main() {

    //LCD.writeByteParallel(false,0b0011_0000)
    HAL.init()
    while (true)
        println( KBD.getKey())
}


object KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.
    const val NONE = 0;
    const val CLR_ALL_BITS = 0b1111_1111
    const val SET_ACK_1 = 0b0001_0000
    //    const val CHECKBIT = 0b0001_0000
    const val CHECKBIT = 0b0000_0001
    private val digitArray = "147*2580369#    ".toCharArray()
    const val NONERETURN = ' '

    // Inicia a classe
    fun init() {
        HAL.clrBits(CLR_ALL_BITS)
    }

    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    fun getKey(): Char {
        if (HAL.isBit(CHECKBIT) ) {
            //val tecla = HAL.readBits(0b0000_1111)
            val tecla = HAL.readBits(0b0001_1110).shr(1)
            HAL.setBits(SET_ACK_1)
            HAL.clrBits(SET_ACK_1)
            return digitArray[tecla]
        }
        return NONERETURN
    }

    // Retorna a tecla premida, caso ocorra antes do ‘timeout’ (representado em milissegundos), ou NONE caso contrário.
    fun waitKey(timeout: Long): Char {
        val time  = Time.getTimeInMillis()
        val tecla = getKey()
        return if (time > timeout) tecla else NONERETURN
    }
}