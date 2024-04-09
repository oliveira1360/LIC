import isel.leic.UsbPort

fun main() {
        //LCD.writeByteParallel(false,0b0011_0000)
        HAL.init()
        //LCD.init()
        LCD.init()
        LCD.write("011d")
        //LCD.cursor(1,0)
        //LCD.clear()

        //LCD.writeByteParallel(false,0b0011_0000)
        //HAL.init()
        //HAL.writeBits(0b0000_1111, 0b0011_00000)


        //
        LCD.cursor(2,7)


}