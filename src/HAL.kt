object HAL { // Virtualiza o acesso ao sistema UsbPort


    // Inicia a classe
    fun init(){

    }



    // Retorna true se o bit tiver o valor lógico ‘1’
    fun isBit(mask: Int): Boolean {
        return true
    }




    // Retorna os valores dos bits representados por mask presentes no UsbPort
    fun readBits(mask: Int): Int {
        return 1
    }




    // Escreve nos bits representados por mask os valores dos bits correspondentes em value
    fun writeBits(mask: Int, value: Int) {

    }



    // Coloca os bits representados por mask no valor lógico ‘1’
    fun setBits(mask: Int) {

    }



    // Coloca os bits representados por mask no valor lógico ‘0’
    fun clrBits(mask: Int) {

    }
}