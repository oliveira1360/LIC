import isel.leic.UsbPort


object KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.
    const val NONE = 0;


    // Inicia a classe
    fun init() {

    }


    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    fun getKey(): Char {
        val tecla = UsbPort.keyreader()

        return if (tecla != 0) tecla.toChar() else ' '
    }


    // Retorna a tecla premida, caso ocorra antes do ‘timeout’ (representado em milissegundos), ou NONE caso contrário.
    fun waitKey(timeout: Long): Char {
        val tecla = UsbPort.keyreader()
        return if (timeout < tecla) tecla else ' '
    }
}