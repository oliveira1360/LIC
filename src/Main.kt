import isel.leic.UsbPort

fun main() {
    while (true) {
        Thread.sleep(2000);
        println(HAL.readBits(5))
    }

}