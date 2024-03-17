import isel.leic.UsbPort
import isel.leic.utils.KeyReader




object KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.
    const val NONE = 0;


    // Inicia a classe
    fun init() {

    }


    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    fun getKey(): Char {
        val tecla = KeyReader().waitKeyChar()

        return if (tecla == null ) ' ' else tecla
    }


    // Retorna a tecla premida, caso ocorra antes do ‘timeout’ (representado em milissegundos), ou NONE caso contrário.
    fun waitKey(timeout: Long): Char {
       val valorAntesDoTimeOut = 0.0.toLong()
        val tecla = KeyReader().waitKeyChar()
        return if (timeout < valorAntesDoTimeOut) tecla else ' '
    }
}