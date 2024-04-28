import isel.leic.utils.Time

var pos = true// true == linha de cima

fun main() {

    //LCD.writeByteParallel(false,0b0011_0000)
    HAL.init()
    //LCD.init()
    LCD.init()
  //  LCD.write("011d")
    //LCD.cursor(1,0)
    //LCD.clear()

    //LCD.writeByteParallel(false,0b0011_0000)
    //HAL.init()
    //HAL.writeBits(0b0000_1111, 0b0011_00000)


        LCD.cursor(1, 7)








}
object LCD { // Escreve no LCD usando a interface a 4 bits.
    private const val LINES = 2
    private const val COLS = 16 // Dimensão do display.
    private const val SERIAL_INTERFACE = false

    const val DATA_MASK = 0x0F
    const val RS_MASK = 0x40
    const val E_MASK = 0x20
    const val CLK_REG_MASK = 0x10

    // Escreve um byte de comando/dados no LCD em paralelo
    fun writeByteParallel(rs: Boolean, data: Int){
        if (rs)
            HAL.setBits(RS_MASK)
        else
            HAL.clrBits(RS_MASK)

        HAL.setBits(E_MASK)

        val high = data.shr(4) //write highbits
        HAL.clrBits(DATA_MASK) //
        HAL.writeBits(DATA_MASK, high)

        HAL.setBits(CLK_REG_MASK) //clock
        HAL.clrBits(CLK_REG_MASK)

        //val low = data.and(DATA_MASK)
        HAL.clrBits(DATA_MASK) //
        HAL.writeBits(DATA_MASK, data) //write lowbits
        HAL.setBits(CLK_REG_MASK) //clock
        HAL.clrBits(CLK_REG_MASK)

        HAL.clrBits(E_MASK)
    }

    // Escreve um byte de comando/dados no LCD em série
    private fun writeByteSerial(rs: Boolean, data: Int) {

    }


        // Escreve um byte de comando/dados no LCD
    private fun writeByte(rs: Boolean, data: Int) {
        if (SERIAL_INTERFACE)
            writeByteSerial(rs,data)
        else
            writeByteParallel(rs, data)
    }


    // Escreve um comando no LCD
    private fun writeCMD(data: Int) {
        writeByte(false, data)
    }


    // Escreve um dado no LCD
    private fun writeDATA(data: Int) {
        writeByte(true, data)
    }

    // Envia a sequência de iniciação para comunicação a 4 bits.
    fun init() {
        Time.sleep(16)
        writeCMD(0b0011_0000)
        Time.sleep(5)
        writeCMD(0b0011_0000)
        Time.sleep(1)
        writeCMD(0b0011_0000)

        writeCMD(0b0011_1000)
        writeCMD(0b0000_1000)//display off
        writeCMD(0b0000_0001)//display clear
        Time.sleep(2)
        writeCMD(0b0000_0110)//mode set

        writeCMD(0b0000_1111)//lcd on

    }


    // Escreve um caráter na posição corrente.
    fun write(c: Char) {
        writeDATA(c.code)
    }


    // Escreve uma string na posição corrente.
    fun write(text: String) {
        for (element in text)
            write(element)

    }


    // Envia comando para posicionar cursor (‘line’:0..LINES-1 , ‘column’:0..COLS-1)
    fun cursor(line: Int, column: Int) {

        val new_pos = ((line - 1) * 40) + column

        clear()//display clear
        for (i in 0 until new_pos - 1)
            write(' ')


        //writeCMD(0b0010 + new_pos)//display clear


    }

    // Envia comando para limpar o ecrã e posicionar o cursor em (0,0)
    fun clear() {
        writeCMD(0b0000_0001)//display clear
        Time.sleep(2)
    }
}