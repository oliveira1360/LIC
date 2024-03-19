import isel.leic.UsbPort
import isel.leic.utils.KeyReader
import isel.leic.utils.Time

object KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.
    const val NONE = 0;

    // Inicia a classe
    fun init() {

    }

    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    fun getKey(): Char {

        val tecla = HAL.readBits(0b0000_1111)
        return if (KeyReader().isAnyKeyPressed ) tecla else ' '
    }

    // Retorna a tecla premida, caso ocorra antes do ‘timeout’ (representado em milissegundos), ou NONE caso contrário.
    fun waitKey(timeout: Long): Char {
        val time  = Time.getTimeInMillis()
        val tecla = getKey()
        return if (time > timeout) tecla else ' '
    }
}