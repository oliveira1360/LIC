
import isel.leic.utils.Time

fun main() {
    HAL.init()
    //test buffers
    while (true){
        val read = KBD.getKey()
        if (read != ' ')
        println(read)
    }
}


object KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.
    private const val CLR_ALL_BITS = 0b1111_1111
    private const val SET_ACK_1 = 0b1000_0000
    private const val CHECKBIT = 0b0001_0000
    private val digitArray = "147*2580369#"
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
            while(HAL.isBit(CHECKBIT));
            HAL.clrBits(SET_ACK_1)
            return digitArray[tecla]
        }
        return NONERETURN
    }

    // Retorna a tecla premida, caso ocorra antes do ‘timeout’ (representado em milissegundos), ou NONE caso contrário.
    fun waitKey(timeout: Long): Char {
        var time  = Time.getTimeInMillis()
        val timeWait = Time.getTimeInMillis() + timeout
        while (time < timeWait){
            val tecla = getKey()
            if (tecla != ' ') return tecla
            time  = Time.getTimeInMillis()
        }
        return NONERETURN
    }
}