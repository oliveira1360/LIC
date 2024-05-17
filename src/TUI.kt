const val TIME = 1715857142087


fun main() {

    HAL.init()
    KBD.init()
    LCD.init()
    while (true) {

        val key = KBD.waitKey(TIME)
        println(key)
        if (key in ('0'..'9'))
            LCD.write(key)
        else if (key == '#') {
            if (pos) {
                LCD.cursor(0, 0)
                pos = false
            } else {
                LCD.cursor(1, 0)
                pos = true
            }
        } else if (key == '*')
            LCD.clear()
    }
}