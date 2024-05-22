import isel.leic.utils.Time


private const val DIGIGIT_ARRAY_IN_CHAR = "1472580369"
private const val TIME_TO_SPAWN = 1800L
private const val POS_INICIAL = 15
private const val SPEED_FACTOR = 50
private const val SPEED_DIFF = 400
private const val ROTATE_SCORE_DISPLAY_SPEED = 900L


fun main() {
        while (true){
                game()
        }
}
fun game(){
        val smiley = byteArrayOf(
                0b11110,
                0b11000,
                0b11100,
                0b11111,
                0b11100,
                0b11000,
                0b11110,
                0b00000
        )
        val heart = byteArrayOf(
                0b01100,
                0b11110,
                0b11110,
                0b11110,
                0b01100,
                0b01100,
                0b00100,
                0b00100
        )


        callInits()//inicilizacao da main
        apresentcionBegin()
        val DIGIGIT_ARRAY_IN_INT = arrayOf(1,4,7,2,5,8,0,3,6,9)
        val mutableListTop = mutableListOf<Int>()
        val mutableListBottom = mutableListOf<Int>()


        var Speed = 0
        var linha0 = ""
        var linha1 = ""
        var ultimaTecla = ' '
        var score = 0
        var lastPos = 0

        while (linha0.length < 13 && linha1.length < 13){
                val random= (0..9).random()
                if (random > 4) {
                        LCD.cursor(0, POS_INICIAL - linha0.length)
                        linha0 += DIGIGIT_ARRAY_IN_CHAR[random]
                        mutableListTop += DIGIGIT_ARRAY_IN_INT[random]
                        LCD.write(linha0)
                }
                else {
                        LCD.cursor(1, POS_INICIAL - linha1.length)
                        linha1 += DIGIGIT_ARRAY_IN_CHAR[random]
                        mutableListBottom += DIGIGIT_ARRAY_IN_INT[random]
                        LCD.write(linha1)
                }
                var timePC = Time.getTimeInMillis()
                val timeUntilSpawn = timePC + TIME_TO_SPAWN - Speed
                while (timeUntilSpawn > timePC)
                {
                        cursorPos( false, lastPos,smiley)
                        val tecla = KBD.waitKey(TIME_TO_SPAWN- Speed)
                        if (tecla == '*') lastPos = cursorPos(true, lastPos,smiley) // mudar de linha
                        if (tecla == '#') {
                                if (lastPos == 0) {
                                        if (linha0.isNotEmpty() && ultimaTecla == linha0[0]) {
                                                linha0 = linha0.substring(1)// remocer o mostro morto
                                                score += mutableListTop[0] + 1
                                                mutableListTop.removeAt(0)
                                                separarDigitos(score)
                                                cleanKillMoster(POS_INICIAL - linha0.length, 0)
                                        }
                                }
                                else{
                                        if ( linha1.isNotEmpty() && ultimaTecla == linha1[0]) {
                                                linha1 = linha1.substring(1)// remocer o mostro morto
                                                score += mutableListBottom[0] + 1
                                                mutableListBottom.removeAt(0)
                                                separarDigitos(score)
                                                cleanKillMoster(POS_INICIAL - linha1.length, 1)


                                        }
                                }
                                LCD.cursor(0, 0)
                                LCD.write(']')
                                LCD.cursor(1, 0)
                                LCD.write(']')
                        }
                        if(tecla != ' ') {
                                ultimaTecla = tecla
                                if(ultimaTecla != '#' && ultimaTecla != '*')
                                {
                                        if (lastPos == 0) LCD.cursor(0,0) else LCD.cursor(1,0)
                                        LCD.write(ultimaTecla)
                                }
                        }
                        timePC = Time.getTimeInMillis()
                }
                if (Speed + SPEED_DIFF <= TIME_TO_SPAWN) Speed += SPEED_FACTOR
        }
        LCD.clear()
        LCD.write("NABOS :( ")

        LCD.cursor(1, 0)
        LCD.write("score: " + score)
        Time.sleep(4000)



}


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

fun callInits(){
        HAL.init()
        LCD.init()
        KBD.init()
        SerialEmitter.init()
        SerialEmitter.init()
        ScoreDisplay.setScore(0b0001_000)

}


fun cleanKillMoster(end:Int, l: Int){
        LCD.cursor(l, end)
        LCD.write(' ')
        LCD.cursor(l, 1)
}

fun apresentcionBegin(){
        LCD.init()
        ScoreDisplay.init()
        LCD.cursor(0,1)
        LCD.write("space invaders")
        LCD.cursor(1,1)
        LCD.write("game")
        LCD.cursor(1,14)
        LCD.write("12")

        var tecla = KBD.waitKey(ROTATE_SCORE_DISPLAY_SPEED)
        var list = listOf(10,11,12,13,14,15)
        while (tecla != '*'){
                updateScore(list)
                tecla = KBD.waitKey(ROTATE_SCORE_DISPLAY_SPEED)
                list = rotateListRight(list)
                updateScore(list)
        }
        LCD.clear()
        updateScore(listOf(0,0,0,0,0,0))
        LCD.cursor(0, 0)
        LCD.write(']')
        LCD.cursor(1, 0)
        LCD.write(']')



}
fun rotateListRight(list: List<Int>): List<Int> {
        if (list.isEmpty()) return list
        val lastElement = list.last()
        val subList = list.dropLast(1)
        return listOf(lastElement) + subList
}



