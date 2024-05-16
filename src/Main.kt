import isel.leic.utils.Time

const val MOSTER_SPAW_LINE_1_COORDENATES = 20
const val MOSTER_SPAW_LINE_2_COORDENATES = 7
const val digitArray = "1472580369"
const val TIME_TO_SPAWN = 1800L
var posMain = false
var last = 0


fun main() {
        //inicilizacao da main
        HAL.init()
        LCD.init()
        KBD.init()
        SerialEmitter.init()
        SerialEmitter.init()

        var linha0 = ""
        var linha1 = ""
        var Pos1 = 15
        var Pos2 = 15
        var ultimaTecla = ' '
        var score = 0
        ScoreDisplay.setScore(0b0001_000)



        //spwan mostros
        //caso e tecla premida seja igual ao primeiro mostro nessa linha ilimar o mostro e aumenta o score em o valor do numero mais 1
        // mover entre linhas com '#'

        while (Pos1 > 0 && Pos2 > 0){
                val random= (0..8).random()
                if (random %2 == 0) {
                        linha0 += digitArray[random]
                        LCD.cursor(0, Pos1)
                        LCD.write(linha0)
                        Pos1--
                }
                else {
                        LCD.cursor(1, Pos2)
                        linha1 += digitArray[random]
                        LCD.write(linha1)
                        Pos2--
                }
                var timePC = Time.getTimeInMillis()
                val timeUntilSpawn = timePC + TIME_TO_SPAWN
                while (timeUntilSpawn > timePC)
                {
                        cursorPos(posMain, false)
                        val tecla = KBD.waitKey(timeUntilSpawn)
                        if (tecla == '*') {
                                cursorPos(posMain,true)
                        }
                        if (tecla == '#') {
                                println( posMain)

                                if (!posMain) {
                                        if (ultimaTecla == linha0[0]) {
                                                linha0 = linha0.substring(1)
                                                Pos1++
                                                score += ultimaTecla.toString().toInt() + 1
                                                separarDigitos(score)
                                        }
                                }
                                else{
                                        if (ultimaTecla == linha1[0]) {
                                                LCD.cursor(1, Pos2)
                                                linha1 = linha1.substring(1)
                                                LCD.write(linha1)
                                                Pos2++
                                                score += ultimaTecla.toString().toInt() + 1
                                                separarDigitos(score)
                                        }
                                }
                        }
                        if(tecla != ' ') {
                                ultimaTecla = tecla
                                println()
                        }
                        timePC = Time.getTimeInMillis()
                }



        }
        LCD.clear()
        LCD.write("NABOS :(")
}


fun cursorPos(pos: Boolean, mudar: Boolean){
        if (mudar) {
                if (pos) {
                        LCD.cursor(0, 0)
                        LCD.write(']')
                        LCD.cursor(0, 1)
                        posMain = false
                        last = 0

                } else {
                        LCD.cursor(1, 0)
                        LCD.write(']')
                        LCD.cursor(1, 1)
                        posMain = true
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
        for (i in 0..list.size -1){
                ScoreDisplay.setScore(i or list[i].shl(3))//move 3 bits para a esqierda o valor e mete a pos == i, para seguir o protocolo
        }
        ScoreDisplay.setScore(UPTADE_SCORE)    //update
}


fun cleanLine(line: Int, pos: Int, size: Int){
        var string = " "
        for (i in 0 until size){
                string += " "
        }
        LCD.cursor(line, pos)
        LCD.write(string)



}
