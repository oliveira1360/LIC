import isel.leic.utils.Time
import javax.swing.text.Position
import kotlin.system.measureTimeMillis


private const val DIGIGIT_ARRAY_IN_CHAR = "1472580369"
private const val ALPHABET_ARRAY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
private const val TIME_TO_SPAWN = 500L
private const val POS_INICIAL = 15
private const val SPEED_FACTOR = 50
private const val SPEED_DIFF = 400
private const val ROTATE_SCORE_DISPLAY_SPEED = 50L
private const val CLEAN_LINE = "                "
private const val CLEAN_SPEED = 300L
var games = 0
var moedas = 0
var scoreList = emptyList<Scores.Score>().toMutableList()

data class choseName(val letra: Char, var pos: Int, var end:Boolean = true)

val ghost = byteArrayOf(
        0b11111,
        0b11111,
        0b10101,
        0b11111,
        0b11111,
        0b10001,
        0b10001,
        0b00000
)
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
val invaderBig0_0 = byteArrayOf(
        0b00000,
        0b00011,
        0b00111,
        0b01111,
        0b01101,
        0b11111,
        0b01110,
        0b00100
        )
val invader01_1 = byteArrayOf(
        0b11111,
        0b11111,
        0b11111,
        0b11111,
        0b10101,
        0b11111,
        0b01110,
        0b00100
        )
val invader01_2 = byteArrayOf(
        0b00000,
        0b11000,
        0b11100,
        0b11110,
        0b01010,
        0b11111,
        0b01110,
        0b00100
        )
val invader1_0 = byteArrayOf(
        0b01010,
        0b10101,
        0b10101,
        0b01010,
        0b01110,
        0b00100,
        0b00000,
        0b00000
        )
fun main() {
        callInits()//inicilizacao da main
        var coin = 0
        coin = apresentcionBegin(coin)
        var first = false
        while (true){
                coin = game(coin, false, first)
                first = false
        }



}
fun game(coin: Int, mode: Boolean, first: Boolean): Int{

        var coin = coin
        if (!first || !mode)coin = apresentcionBegin(coin)
        val DIGIGIT_ARRAY_IN_INT = arrayOf(1,4,7,2,5,8,0,3,6,9)
        val mutableListTop = mutableListOf<Int>()
        val mutableListBottom = mutableListOf<Int>()


        var Speed = 0
        var linha0 = ""
        var linha1 = ""
        var ultimaTecla = ' '
        var score = 0
        var lastPos = 0

        while (linha0.length < 14 && linha1.length < 14){
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
                        TUI.cursorPos( false, lastPos,smiley)
                        val tecla = KBD.waitKey(TIME_TO_SPAWN- Speed)
                        if (tecla == '*') lastPos = TUI.cursorPos(true, lastPos,smiley) // mudar de linha
                        if (tecla == '#') {
                                if (lastPos == 0) {
                                        if (linha0.isNotEmpty() && ultimaTecla == linha0[0]) {
                                                linha0 = linha0.substring(1)// remocer o mostro morto
                                                score += mutableListTop[0] + 1
                                                mutableListTop.removeAt(0)
                                                TUI.separarDigitos(score)
                                                TUI.cleanKillMoster(POS_INICIAL - linha0.length, 0)
                                        }
                                }
                                else{
                                        if ( linha1.isNotEmpty() && ultimaTecla == linha1[0]) {
                                                linha1 = linha1.substring(1)// remocer o mostro morto
                                                score += mutableListBottom[0] + 1
                                                mutableListBottom.removeAt(0)
                                                TUI.separarDigitos(score)
                                                TUI.cleanKillMoster(POS_INICIAL - linha1.length, 1)


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
        //End of the game, score with name added
        incaderClean()
        LCD.clear()
        LCD.write("***Game over***")
        Time.sleep(1000)
        LCD.clear()
        var tecla = KBD.waitKey(ROTATE_SCORE_DISPLAY_SPEED)
        LCD.clear()
        LCD.cursor(1, 0)
        LCD.write("score: " + score)
        LCD.cursor(0, 0)
        LCD.write("Nome:")

        var col = 5
        var indice = 0
        val letra = ALPHABET_ARRAY[indice]
        LCD.write(letra)
        LCD.cursor(0, col)
        var scoreName = mutableListOf(choseName('A', 0, false),choseName('A', 0),choseName('A', 0),choseName('A', 0),choseName('A', 0),choseName('A', 0),choseName('A', 0),choseName('A', 0),choseName('A', 0),choseName('A', 0),choseName('A', 0))
        var numberLers = 0
        SerialEmitter.send( SerialEmitter.Destination.LCD, 0b0000_11110, 9)//piscar o ecra
        while (tecla != '5' && score > 0 && !mode) {
                tecla = KBD.waitKey(ROTATE_SCORE_DISPLAY_SPEED)
                if (tecla == '2' && scoreName[col - 5].pos in (0..24)){ //caracter acima
                        indice = scoreName[col - 5].pos + 1
                        LCD.cursor(0, col)
                        LCD.write(ALPHABET_ARRAY[indice])
                        LCD.cursor(0, col)
                        scoreName[col - 5] = choseName(ALPHABET_ARRAY[indice], scoreName[col - 5].pos, false)
                        scoreName[col - 5].pos++


                }
                if (tecla == '8' && scoreName[col - 5].pos in (1..25)){ //caracter abaixo
                        indice = scoreName[col - 5].pos -1
                        LCD.cursor(0, col)
                        LCD.write(ALPHABET_ARRAY[indice])
                        LCD.cursor(0, col)
                        scoreName[col - 5] = choseName(ALPHABET_ARRAY[indice], scoreName[col - 5].pos, false)
                        scoreName[col - 5].pos--

                }
                if (tecla == '6' && col in (5..14)){ //andar para a direita
                        col++
                        LCD.cursor(0, col)
                        LCD.write(scoreName[col - 5].letra)
                        LCD.cursor(0, col)
                        numberLers++
                        scoreName[col - 5] = choseName(scoreName[col - 5].letra, scoreName[col - 5].pos, false)

                }
                if (tecla == '4' && col in (6..15)){ //andar para a esquerda
                        col--
                        LCD.cursor(0, col)


                }
        }
        var stringName = ""
        for (i in 0 .. numberLers ){
                if (scoreName[i].end) break;
                stringName += scoreName[i].letra
        }
        scoreList.add(Scores.Score( score.toString(), stringName))
        if (!mode){
                coin --
                Scores.addScores(Scores.Score(score.toString(), ""))
                games++
        }
        SerialEmitter.send( SerialEmitter.Destination.LCD, 0b0000_1111_0, 9)//piscar o ecra
        return coin
}
fun callInits(){
        HAL.init()
        LCD.init()
        KBD.init()
        SerialEmitter.init()
        SerialEmitter.init()
        ScoreDisplay.setScore(0b0001_000)

}

fun apresentcionBegin(coin: Int ):Int{
        LCD.init()
        ScoreDisplay.init()
        LCD.cursor(0,1)
        LCD.write("space invaders")

        var tecla = KBD.waitKey(ROTATE_SCORE_DISPLAY_SPEED)
        var list = listOf(10,11,12,13,14,15)
        var totalCoin = coin
        var time = 0L
        var timeUtil = 0L
        var i = 0
        val listScores = Scores.getScores()
        var inicialTIme = Time.getTimeInMillis() + 10000
        while (tecla != '*' || coin < 0 || Coin_Acceptor.seeCoin()){
                        if (Coin_Acceptor.read_coin()) {
                                totalCoin += 2
                                LCD.createCustomChar(1, ghost)
                                LCD.createCustomChar(1, ghost)


                                LCD.cursor(1, 0)
                                LCD.write(CLEAN_LINE)
                                LCD.cursor(1, 1)
                                LCD.write("game " )
                                LCD.cursor(1, 6)
                                LCD.writeCustomChar(0)
                                LCD.cursor(1, 8)
                                LCD.writeCustomChar(1)
                                LCD.cursor(1, 10)
                                LCD.writeCustomChar(1)
                                LCD.cursor(1, 13 )
                                LCD.write("" + totalCoin )



                                moedas++
                                inicialTIme = Time.getTimeInMillis() + 1000
                        }
                        TUI.updateScore(list)
                        tecla = KBD.waitKey(ROTATE_SCORE_DISPLAY_SPEED)
                        list = TUI.rotateListRight(list)
                        TUI.updateScore(list)
                        if (tecla == '*' && totalCoin == 0) {
                                LCD.clear()
                                LCD.cursor(0, 1)
                                LCD.write("Insert coin")
                                Time.sleep(1500)
                                tecla = 'p'
                        }
                        if (tecla != 'p') { //para nao aparcer na placa quando aparece a mensagem "insert coin"
                                LCD.cursor(0, 1)
                                LCD.write("space invaders")
                        }
                        if (read_mode()) mode()
                if (inicialTIme < time) {
                        if (timeUtil <= time) {
                                if (i == listScores.size * 2 - 1) i = 0

                                LCD.cursor(1, 0)
                                LCD.write(CLEAN_LINE)
                                if (i%2 == 0) {
                                        val iList = i /2
                                        LCD.cursor(1, 0)
                                        LCD.write("0" + (iList + 1) + "-" + listScores[iList].name)
                                        val shift = listScores[iList].score.length - 1
                                        LCD.cursor(1, 15 - shift)
                                        LCD.write(listScores[iList].score)
                                }else{
                                        LCD.cursor(1, 0)
                                        LCD.write(CLEAN_LINE)
                                        LCD.cursor(1, 1)
                                        LCD.write("game " )
                                        LCD.cursor(1, 6)
                                        LCD.writeCustomChar(0)
                                        LCD.cursor(1, 8)
                                        LCD.writeCustomChar(1)
                                        LCD.cursor(1, 10)
                                        LCD.writeCustomChar(1)
                                        LCD.cursor(1, 13 )
                                        LCD.write("$" + totalCoin  )
                                }
                                i++


                                timeUtil = time + 2600
                        }
                }
                else{
                        LCD.createCustomChar(0, smiley)
                        LCD.createCustomChar(1, ghost)

                        LCD.cursor(1, 0)
                        LCD.write(CLEAN_LINE)
                        LCD.cursor(1, 1)
                        LCD.write("game " )
                        LCD.cursor(1, 6)
                        LCD.writeCustomChar(0)
                        LCD.cursor(1, 8)
                        LCD.writeCustomChar(1)
                        LCD.cursor(1, 10)
                        LCD.writeCustomChar(1)
                        LCD.cursor(1, 13 )
                        LCD.write("$" + totalCoin  )
                }
                time = Time.getTimeInMillis()
                if (tecla == '*') println("ola")



        }
        LCD.clear()
        TUI.updateScore(listOf(0,0,0,0,0,0))
        LCD.cursor(0, 0)
        LCD.write(']')
        LCD.cursor(1, 0)
        LCD.write(']')
        return  totalCoin

}

fun mode(){
        LCD.init()
        LCD.clear()
        LCD.cursor(0,0)
        LCD.write("modo manutencao")
        LCD.cursor(1,0)
        LCD.write("pw:1")
        LCD.cursor(1,5)
        LCD.write("score:#")
        TUI.updateScore(listOf(0,0,0,0,0,0))
        var ultimaTecla = ' '
        while (read_mode()) {
                val tecla = KBD.waitKey(10)
                if (ultimaTecla == '#' && tecla == '*'){
                        moedas = 0
                        games = 0
                }
                if (tecla != ' ') {
                        ultimaTecla = tecla
                }
                if (tecla == '#'){
                        LCD.clear()
                        LCD.cursor(0,0)
                        LCD.write("moedas: " + moedas)
                        LCD.cursor(1,0)
                        LCD.write("games: " + games)


                }
                if (tecla == '*') {
                        LCD.clear()
                        LCD.cursor(0, 0)
                        LCD.write(']')
                        LCD.cursor(1, 0)
                        LCD.write(']')
                        game(0, true, true)
                        SerialEmitter.send( SerialEmitter.Destination.LCD, 0b0000_1100_0, 9)//piscar o ecra
                        LCD.clear()
                        LCD.cursor(0,0)
                        LCD.write("modo manutencao")
                        LCD.cursor(1,0)
                        LCD.write("pw:1")
                        LCD.cursor(1,5)
                        LCD.write("score:#")
                        TUI.updateScore(listOf(0,0,0,0,0,0))


                }
                if (tecla == '1'){
                        LCD.clear()
                        LCD.cursor(0,0)
                        LCD.write("off != 3")
                        LCD.cursor(1,0)
                        LCD.write("sair == 3")
                        while (true){
                                val tecla = KBD.waitKey(10)
                                if (tecla != ' ') {
                                        if (tecla == '3'){
                                                LCD.clear()
                                                LCD.cursor(0,0)
                                                LCD.write("modo manutencao")
                                                LCD.cursor(1,0)
                                                LCD.write("pw:1")
                                                LCD.cursor(1,5)
                                                LCD.write("score:#")
                                                break
                                        };
                                        if (tecla != '3') {
                                                println(scoreList)
                                                LCD.clear()
                                                for(i in 0 until scoreList.size) {
                                                        Scores.addScores(scoreList[i])
                                                }
                                                ScoreDisplay.off(true)
                                        }
                                }

                        }
                }

        }
        LCD.clear()
}

fun incaderClean(){
        LCD.createCustomChar(0, invaderBig0_0)
        LCD.createCustomChar(0, invaderBig0_0)
        LCD.createCustomChar(1, invader01_1)
        LCD.createCustomChar(2, invader01_2)
        LCD.createCustomChar(3, invader1_0)
        LCD.cursor(0,1)
        LCD.write(' ')
        LCD.cursor(1,1)
        LCD.write(' ')

        for (i in 15 downTo 2) {
                //cima
                LCD.cursor(0, i-2)
                LCD.writeCustomChar(0)
                LCD.cursor(0, i-1)
                LCD.writeCustomChar(1)
                LCD.cursor(0, i)
                LCD.writeCustomChar(2)

                //baixo
                LCD.cursor(1, i-2)
                LCD.writeCustomChar(3)
                LCD.cursor(1, i-1)
                LCD.writeCustomChar(3)
                LCD.cursor(1, i)
                LCD.writeCustomChar(3)
                for (j in 0 until  POS_INICIAL - i){
                        LCD.cursor(0, 15-j)
                        LCD.write(' ')
                        LCD.cursor(1, 15-j)
                        LCD.write(' ')

                }
                Time.sleep(CLEAN_SPEED)
        }



}
