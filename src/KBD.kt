
import isel.leic.utils.Time

fun main() {
    while (true){
        val read = KBD.waitKey(17345524000)
        println(read)
    }

}


object KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.
    private const val CLR_ALL_BITS = 0b1111_1111
    private const val SET_ACK_1 = 0b1000_0000
    private const val CHECKBIT = 0b0001_0000
    private val digitArray = "147*2580369#    "
    private const val NONERETURN = ' '

    // Inicia a classe
    fun init() {
        HAL.clrBits(CLR_ALL_BITS)
    }

    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    fun getKey(): Char {
        if (HAL.isBit(CHECKBIT) ) {
            val tecla = HAL.readBits(0b0000_1111)
            HAL.setBits(SET_ACK_1)
            HAL.clrBits(SET_ACK_1)
            return digitArray[tecla]
        }
        return NONERETURN
    }

    // Retorna a tecla premida, caso ocorra antes do ‘timeout’ (representado em milissegundos), ou NONE caso contrário.
    fun waitKey(timeout: Long): Char {
        var time  = Time.getTimeInMillis()
        while (time < timeout){
            val tecla = getKey()
            if (tecla != ' ') return tecla
            time  = Time.getTimeInMillis()
        }
        return NONERETURN
    }
}