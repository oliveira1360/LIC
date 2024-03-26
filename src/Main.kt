import isel.leic.UsbPort

fun main() {

        //LCD.writeByteParallel(false,0b0011_0000)

        HAL.writeBits(0x03, 0x03)

        HAL.setBits(0x10)
        HAL.clrBits(0x10)

        HAL.writeBits(0x0F, 0x0A)

        HAL.setBits(0x10)
        HAL.clrBits(0x10)

}