import isel.leic.UsbPort
import isel.leic.utils.KeyReader
import isel.leic.utils.Time

object KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.
    const val NONE = 0;
    const val CLR = 0b1111_1111
    const val SET = 0b0001_0000
    val digitArray = "147*2580369#".toCharArray()

    // Inicia a classe
    fun init() {
        HAL.clrBits(0b1111_1111)
    }

    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    fun getKey(): Char {

        val tecla = HAL.readBits(0b0000_1111)

        if (HAL.isBit(0b0001_0000) ) {
            HAL.setBits(SET)
            HAL.clrBits(CLR)
            return digitArray[tecla]

        }
        return ' '
    }

    // Retorna a tecla premida, caso ocorra antes do ‘timeout’ (representado em milissegundos), ou NONE caso contrário.
    fun waitKey(timeout: Long): Char {
        val time  = Time.getTimeInMillis()
        val tecla = getKey()
        return if (time > timeout) tecla else ' '
    }
}