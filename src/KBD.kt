




object KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.
    const val NONE = 0;


    // Inicia a classe
    fun init() {

    }


    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    fun getKey(): Char {
        return 's'
    }


    // Retorna a tecla premida, caso ocorra antes do ‘timeout’ (representado em milissegundos), ou NONE caso contrário.
    fun waitKey(timeout: Long): Char {
        return  'a'
    }
}