

private const val ALPHABET_ARRAY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
private const val WAIT_KEY = 1000L
private const val CLEAN_LINE = "                "




object TUI {
    fun insertName(): String{
        var tecla = waitKey(WAIT_KEY)
        var col = 5
        var indice = 0
        val letra = ALPHABET_ARRAY[indice]
        write(letra)
        cursor(0, col)
        var scoreName = mutableListOf(choseName('A', 0, false),choseName('A', 0),choseName('A', 0),choseName('A', 0),choseName('A', 0),choseName('A', 0))
        var numberLers = 0
        activeBlilnk()
        while (tecla != '5' ) {
            tecla = waitKey(WAIT_KEY)
            if (tecla == '2' && scoreName[col - 5].pos in (0..24)){ //caracter acima
                indice = scoreName[col - 5].pos + 1
                cursor(0, col)
                write(ALPHABET_ARRAY[indice])
                cursor(0, col)
                scoreName[col - 5] = choseName(ALPHABET_ARRAY[indice], scoreName[col - 5].pos, false)
                scoreName[col - 5].pos++


            }
            if (tecla == '8' && scoreName[col - 5].pos in (1..25)){ //caracter abaixo
                indice = scoreName[col - 5].pos -1
                cursor(0, col)
                write(ALPHABET_ARRAY[indice])
                cursor(0, col)
                scoreName[col - 5] = choseName(ALPHABET_ARRAY[indice], scoreName[col - 5].pos, false)
                scoreName[col - 5].pos--

            }
            if (tecla == '6' && col in (5..9)){ //andar para a direita
                col++
                cursor(0, col)
                write(scoreName[col - 5].letra)
                cursor(0, col)
                scoreName[col - 5] = choseName(scoreName[col - 5].letra, scoreName[col - 5].pos, false)

            }
            if (tecla == '4' && col in (6..10)){ //andar para a esquerda
                col--
                cursor(0, col)


            }
        }
        var stringName = ""
        for (i in 0 until scoreName.size ){
            if (scoreName[i].end) break;
            stringName += scoreName[i].letra
        }
        return stringName

    }

    fun viewCoins(totalCoin: Int){
        cursor(1, 0)
        write(CLEAN_LINE)
        cursor(1, 1)
        write("game " )
        cursor(1, 6)
        writeCustomChar(0)
        cursor(1, 8)
        writeCustomChar(1)
        cursor(1, 10)
        writeCustomChar(1)
        cursor(1, 13 )
        write("$" + totalCoin  )
    }
    fun mView(){
        clear()
        cursor(0,0)
        write("modo manutencao")
        cursor(1,0)
        write("pw:1")
        cursor(1,5)
        write("score:#")
        ScoreDisplay.scoreReset()
    }
    fun init(){
        KBD.init()
        LCD.init()
    }
    fun rotateListRight(list: List<Int>): List<Int> {
        if (list.isEmpty()) return list
        val lastElement = list.last()
        val subList = list.dropLast(1)
        return listOf(lastElement) + subList
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



    //LCD
    //-------------------------------------------------------------------------
    fun write(c: Char) {
        LCD.write(c)
    }
    fun write(c: String) {
        LCD.write(c)
    }

    fun cursor(line: Int, column: Int) {
        LCD.cursor(line, column)
    }
    fun clear(){
        LCD.clear()
    }
    fun createCustomChar(location: Int, charMap: ByteArray) {
        LCD.createCustomChar(location, charMap)
    }
    fun writeCustomChar(location: Int) {
        LCD.writeCustomChar(location)
    }
    fun activeBlilnk(){
        LCD.activeBlilnk()
    }
    fun desativeBlilnk(){
        LCD.desativeBlilnk()
    }

    //-------------------------------------------------------------------------

    //KBD
    //-------------------------------------------------------------------------
    fun waitKey(timeout: Long): Char {
        return KBD.waitKey(timeout)
    }


    //-------------------------------------------------------------------------



}