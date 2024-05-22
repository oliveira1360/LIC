import com.sun.tools.javac.Main
import isel.leic.utils.Time



object TUI {

    fun cursorPos(mudar: Boolean, last: Int, byteArray: ByteArray): Int{
        var last = last
        if (mudar) {
            if (last == 1) {
                LCD.cursor(1, 0)
                LCD.write(']')
                LCD.cursor(1, 1)
                LCD.write(' ')
                playerVision(0,0)
                last = 0

            } else {
                LCD.cursor(0, 0)
                LCD.write(']')
                LCD.cursor(0, 1)
                LCD.write(' ')
                playerVision(1, 0)
                last = 1
            }
        }
        else{
            LCD.createCustomChar(0, byteArray)
            LCD.cursor(last, 1)
            LCD.writeCustomChar(0)

        }
        return last

    }
    fun separarDigitos(numero: Int) {
        var num = numero
        val digitos = mutableListOf<Int>()

        while (num > 0) {
            val digito = num % 10
            digitos.add(0, digito)
            num /= 10
        }
        updateScore(digitos.reversed())
    }

    fun updateScore(list: List<Int>){
        for (i in 0 until list.size){
            ScoreDisplay.setScore(i or list[i].shl(3))//move 3 bits para a esqierda o valor e mete a pos == i, para seguir o protocolo
            Time.sleep(4)
        }
        Time.sleep(4)
        ScoreDisplay.setScore(UPTADE_SCORE)
    }

    fun playerVision(l: Int, c:Int){
        LCD.cursor(l, c)
        LCD.write(']')
        LCD.cursor(l, c + 1)
    }



    fun cleanKillMoster(end:Int, l: Int){
        LCD.cursor(l, end)
        LCD.write(' ')
        LCD.cursor(l, 1)
    }

    fun rotateListRight(list: List<Int>): List<Int> {
        if (list.isEmpty()) return list
        val lastElement = list.last()
        val subList = list.dropLast(1)
        return listOf(lastElement) + subList
    }



}