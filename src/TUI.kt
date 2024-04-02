fun main(){
    HAL.init()
    KBD.init()
    LCD.init()

    val key = KBD.getKey()
    if (key in ('0'..'9'))
        LCD.write(key)
    else if(key == '#') {
        if (pos < 40 ) LCD.cursor(1, 1)
        else LCD.cursor(2,1)
    }
    else if(key == '*')
        LCD.clear()
}