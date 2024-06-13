import isel.leic.utils.Time


fun main() {
    HAL.init()
    LCD.init()
    val smiley = byteArrayOf(// a forma como aparece no ecra
        0b11110,//primeira linha no LCD
        0b11000,//segunda linha ...
        0b11100,
        0b11111,
        0b11100,
        0b11000,
        0b11110,
        0b00000 // ultima linha
    )

    LCD.createCustomChar(0, smiley)
    LCD.cursor(0, 0)
    LCD.writeCustomChar(0)





}
object LCD { // Escreve no LCD usando a interface a 4 bits.
    private const val CLR_BITS_LCD = 0b0000_0001
    private const val LINES = 2
    private const val COLS = 16 // Dimensão do display.
    private const val SERIAL_INTERFACE = true


    //mascaras para quando os dados forem enviados em paralelo
    //---------------------
    const val DATA_MASK = 0x0F
    const val RS_MASK = 0x40
    const val E_MASK = 0x20
    const val CLK_REG_MASK = 0x10
    //---------------------

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

        HAL.clrBits(DATA_MASK) //
        HAL.writeBits(DATA_MASK, data) //write lowbits
        HAL.setBits(CLK_REG_MASK) //clock
        HAL.clrBits(CLK_REG_MASK)

        HAL.clrBits(E_MASK)
    }

    // Escreve um byte de comando/dados no LCD em série
    private fun writeByteSerial(rs: Boolean, data: Int) {
        if (rs)
            SerialEmitter.send(SerialEmitter.Destination.LCD, data.shl(1) + 1, 9)// shift e adiciona 1 para que o bit de menor peso seja 1
        else
            SerialEmitter.send(SerialEmitter.Destination.LCD, data.shl(1), 9)
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

        Time.sleep(18)
        writeCMD( 0b0011_1111)
        Time.sleep(5)
        writeCMD( 0b0011_0000)
        Time.sleep(1)
        writeCMD(  0b0011_0000)
        Time.sleep(1)
        writeCMD(0b0011_1000)
        Time.sleep(2)
        writeCMD(0b0000_1000)
        Time.sleep(2)
        writeCMD(0b0000_0001)//display off
        Time.sleep(2)
        writeCMD(  0b0000_0110)//display clear
        Time.sleep(2)
        writeCMD(  0b0000_0110)//mode set
        Time.sleep(2)
        writeCMD( 0b0000_1100)//piscar o ecra
        Time.sleep(2)

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
        val new_pos = (line * 64) + column
        writeByte(false, 0b1000_0000 + new_pos)
        Time.sleep(2)
    }

    // Envia comando para limpar o ecrã e posicionar o cursor em (0,0)
    fun clear() {
        writeCMD(CLR_BITS_LCD)//display clear
        Time.sleep(2)
    }

    //escreve na memroria CGRAM o caracter
    fun createCustomChar(location: Int, charMap: ByteArray) {
        val cgramAddress = 0x40 or (location shl 3)//0x40 == possicao inical, 0001_----_- //shift = 3 para que a localizacao sej a certa
        writeCMD(cgramAddress)
        for (i in charMap.indices) {
            writeDATA(charMap[i].toInt())
        }
    }

    //vai buscar a memoria o caracter que tem de "dessenhar"
    fun writeCustomChar(location: Int) {
        writeDATA(location)
    }
    fun activeBlilnk(){
        SerialEmitter.send( SerialEmitter.Destination.LCD, 0b0000_1111_0, 9)//piscar o ecra
    }
    fun deactivateBlilnk(){
        SerialEmitter.send( SerialEmitter.Destination.LCD, 0b0000_1100_0, 9)//nao piscar o ecra
    }
}