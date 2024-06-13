import isel.leic.utils.Time


//constantes
//-------------------------------------------------------------
private const val DIGIGIT_ARRAY_IN_CHAR = "1472580369"
private const val TIME_TO_SPAWN = 500L
private const val POS_INICIAL = 15
private const val SPEED_FACTOR = 50
private const val SPEED_DIFF = 400
private const val ROTATE_SCORE_DISPLAY_SPEED = 50L
private const val CLEAN_LINE = "                "
private const val CLEAN_SPEED = 300L
private const val ALTERNATE_SPEED = 2000
private const val TIME_TO_START = 5500
private const val MAX_MOSTER_SIZE = 14
//-------------------------------------------------------------

//variaveis globais
//-------------------------------------------------------------
var games = 0
var moedas = 0
var scoreList = emptyList<Scores.Score>().toMutableList()
//-------------------------------------------------------------

data class choseName(val letra: Char, var pos: Int, var end:Boolean = true)

//"desanhos"
//-------------------------------------------------------------
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
//-------------------------------------------------------------


fun main() {
        TUI.init()
        ScoreDisplay.init()
        var coin = 0
        coin = presentationBegin(coin)
        while (true){
                coin = game(coin, false)
                coin = presentationBegin(coin)
        }



}
fun game(coin: Int, mode: Boolean): Int{

        var coin = coin
        val DIGIGIT_ARRAY_IN_INT = arrayOf(1,4,7,2,5,8,0,3,6,9)//array com a mesma sequencia da DIGIGIT_ARRAY_IN_CHAR, usada para evitar o usado de conversoes
        val mutableListTop = mutableListOf<Int>()
        val mutableListBottom = mutableListOf<Int>()


        var Speed = 0
        var linha0 = ""
        var linha1 = ""
        var ultimaTecla = ' '
        var score = 0
        var lastPos = 0

        while (linha0.length <= MAX_MOSTER_SIZE && linha1.length <= MAX_MOSTER_SIZE){
                val random= (0..9).random()
                if (random > 4) {
                        TUI.cursor(0, POS_INICIAL - linha0.length)
                        linha0 += DIGIGIT_ARRAY_IN_CHAR[random]
                        mutableListTop += DIGIGIT_ARRAY_IN_INT[random]
                        TUI.write(linha0)
                }
                else {
                        TUI.cursor(1, POS_INICIAL - linha1.length)
                        linha1 += DIGIGIT_ARRAY_IN_CHAR[random]
                        mutableListBottom += DIGIGIT_ARRAY_IN_INT[random]
                        TUI.write(linha1)
                }
                var timePC = Time.getTimeInMillis()
                val timeUntilSpawn = timePC + TIME_TO_SPAWN - Speed
                while (timeUntilSpawn > timePC)
                {
                        TUI.cursorPos( false, lastPos,smiley)
                        val tecla = TUI.waitKey(TIME_TO_SPAWN- Speed)
                        if (tecla == '*') lastPos = TUI.cursorPos(true, lastPos,smiley) // mudar de linha
                        if (tecla == '#') {
                                //usa as listas top e bottom para saber qual o valor tem que aumentar na pontuacao
                                if (lastPos == 0) {
                                        if (linha0.isNotEmpty() && ultimaTecla == linha0[0]) {
                                                linha0 = linha0.substring(1)// remover o mostro morto
                                                score += mutableListTop[0] + 1
                                                mutableListTop.removeAt(0)
                                                ScoreDisplay.setScore(score)
                                                TUI.cleanKilledMoster(POS_INICIAL - linha0.length, 0)
                                        }
                                }
                                else{
                                        if ( linha1.isNotEmpty() && ultimaTecla == linha1[0]) {
                                                linha1 = linha1.substring(1)// remover o mostro morto
                                                score += mutableListBottom[0] + 1
                                                mutableListBottom.removeAt(0)
                                                ScoreDisplay.setScore(score)
                                                TUI.cleanKilledMoster(POS_INICIAL - linha1.length, 1)


                                        }
                                }
                                TUI.cursor(0, 0)
                                TUI.write(']')
                                TUI.cursor(1, 0)
                                TUI.write(']')
                        }
                        if(tecla != ' ') {
                                ultimaTecla = tecla
                                if(ultimaTecla != '#' && ultimaTecla != '*')
                                {
                                        if (lastPos == 0) TUI.cursor(0,0) else TUI.cursor(1,0)
                                        TUI.write(ultimaTecla)
                                }
                        }
                        timePC = Time.getTimeInMillis()
                }
                if (Speed + SPEED_DIFF <= TIME_TO_SPAWN) Speed += SPEED_FACTOR
        }
        incaderClean()
        TUI.clear()
        TUI.write("***Game over***")
        Time.sleep(1000)
        TUI.clear()

        if (score > 0 && !mode){
                TUI.clear()
                TUI.cursor(1, 0)
                TUI.write("score: " + score)
                TUI.cursor(0, 0)
                TUI.write("Nome:")

                val playerName = TUI.insertName()
                scoreList.add(Scores.Score( score.toString(), playerName))

        }

        if (!mode){
                coin --
                Scores.addScores(Scores.Score(score.toString(), ""))
                games++
        }

        TUI.deactivateBlilnk()
        TUI.clear()
        return coin
}


fun presentationBegin(coin: Int ):Int{
        ScoreDisplay.init()
        TUI.cursor(0,1)
        TUI.write("space invaders")

        var tecla = TUI.waitKey(ROTATE_SCORE_DISPLAY_SPEED)
        var lista = listOf(10,11,12,13,14,15)
        var totalCoin = coin
        var time = 0L
        var timeUtil = 0L
        var i = 0
        val listScores = Scores.getScores()
        var inicialTIme = Time.getTimeInMillis() + TIME_TO_START//usado para saber passado quanto deve deve comecar a aparcer o score na primeira interacao
        TUI.createCustomChar(0, smiley)
        TUI.createCustomChar(1, ghost)

        TUI.viewCoins(totalCoin)
        while (tecla != '*' || coin < 0 || Coin_Acceptor.seeCoin()){
                        if (Coin_Acceptor.read_coin()) {
                                totalCoin += 2
                                TUI.viewCoins(totalCoin)
                                moedas++
                        }
                        ScoreDisplay.scoreRorate(lista)
                        lista = TUI.rotateListRight(lista)
                        tecla = TUI.waitKey(ROTATE_SCORE_DISPLAY_SPEED)
                        ScoreDisplay.scoreRorate(lista)
                        lista = TUI.rotateListRight(lista)
                        if (tecla == '*' && totalCoin == 0) {
                                TUI.write(CLEAN_LINE)
                                TUI.cursor(0, 1)
                                TUI.write("Insert coin")
                                Time.sleep(800)
                                tecla = '+'
                                TUI.cursor(0,1)
                                TUI.write("space invaders")
                        }
                        if (read_mode()) {
                                mode()
                                TUI.cursor(0,1)
                                TUI.write("space invaders")
                        }
                if (inicialTIme < time) {
                        if (timeUtil <= time) {
                                if (i == listScores.size * 2 - 1) i = 0//Para saber se ja mostrou todos os scores intercalados com a messagem default do jogo, caso tenha volta ao inicio

                                TUI.cursor(1, 0)
                                TUI.write(CLEAN_LINE)
                                //se o numero for par vai mostrar o score caso contrio mostra a mensagem de quantas moedas tem
                                if (i%2 == 0) {
                                        val iList = i /2
                                        TUI.cursor(1, 0)
                                        TUI.write("0" + (iList + 1) + "-" + listScores[iList].name)
                                        val shift = listScores[iList].score.length - 1
                                        TUI.cursor(1, 15 - shift)
                                        TUI.write(listScores[iList].score)
                                }else{
                                        TUI.viewCoins(totalCoin)
                                }
                                i++


                                timeUtil = time + ALTERNATE_SPEED
                        }
                }
                time = Time.getTimeInMillis()


        }
        TUI.clear()
        ScoreDisplay.scoreReset()
        TUI.cursor(0, 0)
        TUI.write(']')
        TUI.cursor(1, 0)
        TUI.write(']')
        return  totalCoin

}

fun mode(){
        TUI.mView()//texto de apresentacao do modo de manutencao
        var ultimaTecla = ' '
        while (read_mode()) {
                var tecla = TUI.waitKey(10)
                if (tecla == '#'){
                        TUI.clear()
                        TUI.cursor(0,0)
                        TUI.write("moedas: " + moedas)
                        TUI.cursor(1,0)
                        TUI.write("games: " + games)
                        ultimaTecla = '#'
                        while (true) {
                                var tecla = TUI.waitKey(10)
                                if (ultimaTecla == '#' && tecla == '*') {
                                        moedas = 0
                                        games = 0
                                        TUI.clear()
                                        TUI.cursor(0, 0)
                                        TUI.write("moedas: " + moedas)
                                        TUI.cursor(1, 0)
                                        TUI.write("games: " + games)
                                        tecla = 'p'
                                }
                                if (tecla != ' ') {
                                        ultimaTecla = tecla
                                }
                                if (tecla == '1') break
                                if (!read_mode()) break
                        }
                        TUI.mView()
                }
                if (tecla == '*') {
                        TUI.clear()
                        TUI.cursor(0, 0)
                        TUI.write(']')
                        TUI.cursor(1, 0)
                        TUI.write(']')
                        game(0, true)
                        TUI.mView()


                }
                if (tecla == '1'){
                        TUI.clear()
                        TUI.cursor(0,4)
                        TUI.write("SHUTDOWN")
                        TUI.cursor(1,1)
                        TUI.write("YES=3")
                        TUI.cursor(1,7)
                        TUI.write("NO=Other")
                        while (true){
                                val tecla = TUI.waitKey(10)
                                if (tecla != ' ') {
                                        if (tecla == '3'){
                                                println(scoreList)
                                                TUI.clear()
                                                for(i in 0 until scoreList.size) {
                                                        Scores.addScores(scoreList[i])
                                                }
                                                Statistics.updateStatistic(Statistics.Statistic(moedas, games))
                                                ScoreDisplay.off(true)
                                        };
                                        else {
                                                TUI.mView()
                                                break
                                        }
                                }
                                if (!read_mode()) break

                        }
                }

        }
        TUI.clear()
}

fun incaderClean(){
        TUI.createCustomChar(0, invaderBig0_0)
        TUI.createCustomChar(0, invaderBig0_0)
        TUI.createCustomChar(1, invader01_1)
        TUI.createCustomChar(2, invader01_2)
        TUI.createCustomChar(3, invader1_0)
        TUI.cursor(0,1)
        TUI.write(' ')
        TUI.cursor(1,1)
        TUI.write(' ')

        for (i in 15 downTo 2) {
                //cima
                TUI.cursor(0, i-2)
                TUI.writeCustomChar(0)
                TUI.cursor(0, i-1)
                TUI.writeCustomChar(1)
                TUI.cursor(0, i)
                TUI.writeCustomChar(2)

                //baixo
                TUI.cursor(1, i-2)
                TUI.writeCustomChar(3)
                TUI.cursor(1, i-1)
                TUI.writeCustomChar(3)
                TUI.cursor(1, i)
                TUI.writeCustomChar(3)
                for (j in 0 until  POS_INICIAL - i){
                        TUI.cursor(0, 15-j)
                        TUI.write(' ')
                        TUI.cursor(1, 15-j)
                        TUI.write(' ')
                }
                Time.sleep(CLEAN_SPEED)
        }
}


