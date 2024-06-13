

private const val ALPHABET_ARRAY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
private const val WAIT_KEY = 1000L
private const val CLEAN_LINE = "                "
private const val MOVE_RIGHT_NAME = '6'
private const val MOVE_LEFTT_NAME = '4'
private const val MOVE_UP_NAME = '2'
private const val MOVE_DOWN_NAME = '8'
private const val ENTER_NAME = '5'
private const val NAME_INICIAL_POS = 5
private const val NAME_MAX_POS = 9









    object TUI {
    fun insertName(): String{
        var tecla = waitKey(WAIT_KEY)
        var col = 5
        var indice = 0
        val letra = ALPHABET_ARRAY[indice]
        write(letra)
        cursor(0, col)
        var scoreName = mutableListOf(choseName('A', 0, false),choseName('A', 0),choseName('A', 0),choseName('A', 0),choseName('A', 0),choseName('A', 0))
        activeBlilnk()
        while (tecla != ENTER_NAME ) {
            tecla = waitKey(WAIT_KEY)
            if (tecla == MOVE_UP_NAME && scoreName[col - 5].pos in (0 until ALPHABET_ARRAY.length - 1)){ //caracter acima
                indice = scoreName[col - NAME_INICIAL_POS].pos + 1
                cursor(0, col)
                write(ALPHABET_ARRAY[indice])
                cursor(0, col)
                scoreName[col - NAME_INICIAL_POS] = choseName(ALPHABET_ARRAY[indice], scoreName[col - NAME_INICIAL_POS].pos, false)
                scoreName[col - NAME_INICIAL_POS].pos++


            }
            if (tecla == MOVE_DOWN_NAME && scoreName[col - NAME_INICIAL_POS].pos in (1..ALPHABET_ARRAY.length)){ //caracter abaixo
                indice = scoreName[col - NAME_INICIAL_POS].pos -1
                cursor(0, col)
                write(ALPHABET_ARRAY[indice])
                cursor(0, col)
                scoreName[col - NAME_INICIAL_POS] = choseName(ALPHABET_ARRAY[indice], scoreName[col - NAME_INICIAL_POS].pos, false)
                scoreName[col - NAME_INICIAL_POS].pos--

            }
            if (tecla == MOVE_RIGHT_NAME && col in (NAME_INICIAL_POS..NAME_MAX_POS)){ //andar para a direita
                col++
                cursor(0, col)
                write(scoreName[col - 5].letra)
                cursor(0, col)
                scoreName[col - NAME_INICIAL_POS] = choseName(scoreName[col - NAME_INICIAL_POS].letra, scoreName[col - NAME_INICIAL_POS].pos, false)

            }
            if (tecla == MOVE_LEFTT_NAME && col in (NAME_INICIAL_POS + 1..NAME_MAX_POS +  1)){ //andar para a esquerda
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



    fun cleanKilledMoster(end:Int, l: Int){
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
    fun deactivateBlilnk(){
        LCD.deactivateBlilnk()
    }

    //-------------------------------------------------------------------------

    //KBD
    //-------------------------------------------------------------------------
    fun waitKey(timeout: Long): Char {
        return KBD.waitKey(timeout)
    }

    //-------------------------------------------------------------------------



}