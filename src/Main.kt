import isel.leic.UsbPort

fun main() {
    while (true) {
        /*
        Thread.sleep(1500);
        var teste = UsbPort.read()
        println(teste)
        println(HAL.readBits(0b0101))
        println(HAL.plate_value)
        HAL.writeBits(0b0101, 0b0101)
        HAL.setBits(0b0001)
         */
        Thread.sleep(1500)
        var teste = KBD.getKey()
        println(teste)


    }

}