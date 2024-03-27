import isel.leic.UsbPort

fun main() {
        //LCD.writeByteParallel(false,0b0011_0000)
        //HAL.init()
        LCD.init()
        //HAL.writeBits(0b0000_1111, 0b0011_00000)

        while (true) {
                Thread.sleep(1000)
                val teste = KBD.getKey()
                LCD.write(teste.toString())
        }



}