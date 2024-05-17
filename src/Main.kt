import isel.leic.utils.Time


const val DIGIGIT_ARRAY_IN_CHAR = "1472580369"
const val TIME_TO_SPAWN = 1800L
const val POS_INICIAL = 15
const val SPEED_FACTOR = 50
const val SPEED_DIFF = 400


fun main() {

        callInits()//inicilizacao da main
        val DIGIGIT_ARRAY_IN_INT = arrayOf(1,4,7,2,5,8,0,3,6,9)
        val mutableListTop = mutableListOf<Int>()
        val mutableListBottom = mutableListOf<Int>()


        var Speed = 0
        var linha0 = ""
        var linha1 = ""
        var Pos1 = POS_INICIAL
        var Pos2 = POS_INICIAL
        var ultimaTecla = ' '
        var score = 0
        var lastPos = 0
        ScoreDisplay.setScore(0b0001_000)

        while (Pos1 > 0 && Pos2 > 0){
                val random= (0..9).random()
                if (random > 4) {
                        linha0 += DIGIGIT_ARRAY_IN_CHAR[random]
                        mutableListTop += DIGIGIT_ARRAY_IN_INT[random]
                        LCD.cursor(0, Pos1)
                        LCD.write(linha0)
                        Pos1--
                }
                else {
                        LCD.cursor(1, Pos2)
                        linha1 += DIGIGIT_ARRAY_IN_CHAR[random]
                        mutableListBottom += DIGIGIT_ARRAY_IN_INT[random]
                        LCD.write(linha1)
                        Pos2--
                }
                var timePC = Time.getTimeInMillis()
                val timeUntilSpawn = timePC + TIME_TO_SPAWN - Speed
                while (timeUntilSpawn > timePC)
                {
                        cursorPos( false, lastPos)
                        val tecla = KBD.waitKey(timeUntilSpawn)
                        if (tecla == '*') lastPos = cursorPos(true, lastPos) // mudar de linha
                        if (tecla == '#') {

                                if (lastPos == 0) {
                                        if (ultimaTecla == linha0[0]) {
                                                linha0 = linha0.substring(1)
                                                Pos1++
                                                score += mutableListTop[0] + 1
                                                mutableListTop.removeAt(0)
                                                separarDigitos(score)
                                                cleanKillMoster(Pos1, 0)
                                        }
                                }
                                else{
                                        if (ultimaTecla == linha1[0]) {
                                                linha1 = linha1.substring(1)
                                                Pos2++
                                                score += mutableListBottom[0] + 1
                                                mutableListBottom.removeAt(0)
                                                separarDigitos(score)
                                                cleanKillMoster(Pos2, 1)

                                        }
                                }
                        }
                        if(tecla != ' ') {
                                ultimaTecla = tecla
                        }
                        timePC = Time.getTimeInMillis()
                }
                if (Speed + SPEED_DIFF <= TIME_TO_SPAWN) Speed += SPEED_FACTOR
        }
        LCD.clear()
        LCD.write("NABOS :(")
}


fun cursorPos(mudar: Boolean, last: Int): Int{
        var last = last
        if (mudar) {
                if (last == 1) {
                        playerVision(0,0)
                        last = 0

                } else {
                        playerVision(1, 0)
                        last = 1
                }
        }
        else{
                LCD.cursor(0, 0)
                LCD.write(']')
                LCD.cursor(1, 0)
                LCD.write(']')

                LCD.cursor(last, 1)
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
        }
        ScoreDisplay.setScore(UPTADE_SCORE)    //update
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
}


fun cleanKillMoster(end:Int, l: Int){
        LCD.cursor(l, end)
        LCD.write(' ')
        LCD.cursor(l, 1)
}

