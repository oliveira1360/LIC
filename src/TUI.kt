
fun main() {

    HAL.init()
    KBD.init()
    LCD.init()
    while (true) {


        val key = KBD.getKey()
        if (key in ('0'..'9'))
            LCD.write(key)
        else if (key == '#') {
            if (pos) {
                LCD.cursor(1, 1)
                pos = false
            }
            else{
                LCD.cursor(2, 1)
               pos = true
            }
        } else if (key == '*')
            LCD.clear()
    }
}