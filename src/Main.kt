import isel.leic.UsbPort

fun main() {
    while (true) {
        Thread.sleep(1500);
        println(HAL.readBits(101))
    }

}