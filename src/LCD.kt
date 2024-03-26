object LCD { // Escreve no LCD usando a interface a 4 bits.
    private const val LINES = 2
    private const val COLS = 16 // Dimensão do display.

    const val DATA_MASK = 0x0F
    const val RS_MASK = 0x40
    const val E_MASK = 0x20
    const val CLK_REG_MASK = 0x10

    // Escreve um byte de comando/dados no LCD em paralelo
     fun writeByteParallel(rs: Boolean, data: Int){
        if (rs) {
            val low = data.and(DATA_MASK)
            HAL.writeBits(DATA_MASK, low)
            println("low " + low)
            HAL.setBits(CLK_REG_MASK)
            HAL.clrBits(CLK_REG_MASK)
            val high = data.shr(4)
            println("high " + high)
            HAL.writeBits(DATA_MASK, high)

        }
    }

    // Escreve um byte de comando/dados no LCD em série
    private fun writeByteSerial(rs: Boolean, data: Int) {

    }


    // Escreve um byte de comando/dados no LCD
    private fun writeByte(rs: Boolean, data: Int) {

    }


    // Escreve um comando no LCD
    private fun writeCMD(data: Int) {

    }


    // Escreve um dado no LCD
    private fun writeDATA(data: Int) {

    }


    // Envia a sequência de iniciação para comunicação a 4 bits.
    fun init() {

    }


    // Escreve um caráter na posição corrente.
    fun write(c: Char) {

    }


    // Escreve uma string na posição corrente.
    fun write(text: String) {

    }


    // Envia comando para posicionar cursor (‘line’:0..LINES-1 , ‘column’:0..COLS-1)
    fun cursor(line: Int, column: Int) {

    }


    // Envia comando para limpar o ecrã e posicionar o cursor em (0,0)
    fun clear() {

    }
}