import isel.leic.UsbPort

fun main() {
    while (true) {
        HAL.init()
        Thread.sleep(1500);
        //println(HAL.readBits(0b0101))
        println(HAL.plate_value)
       //HAL.writeBits(0b0101, 0b0101)
        HAL.setBits(0b0001)
    }

}