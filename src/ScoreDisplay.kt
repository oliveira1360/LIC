import isel.leic.utils.Time

const val UPTADE_SCORE = 0b1010_110

fun main(){
    SerialEmitter.init()
    ScoreDisplay.init()
    ScoreDisplay.setScore(111)
    ScoreDisplay.setScore(20)
    ScoreDisplay.setScore(300)
    ScoreDisplay.setScore(400)
}

object ScoreDisplay { // Controla o mostrador de pontuação.
    // Inicia a classe, estabelecendo os valores iniciais.
    private const val SCORE_ON = 0b0000_111
    private const val SCORE_OFF = 0b0001_111

    fun init() {
        off(false)
    }


    // Envia comando para atualizar o valor do mostrador de pontuação
    fun setScore(value: Int) {

        var num = value
        var digitos = mutableListOf<Int>()

        while (num > 0) {
            val digito = num % 10
            digitos.add(0, digito)
            num /= 10
        }
        digitos = digitos.reversed().toMutableList()
        for (i in 0 until digitos.size){
            SerialEmitter.send(SerialEmitter.Destination.SCORE, i or digitos[i].shl(3),7)
            Time.sleep(4)
        }
        Time.sleep(4)
        SerialEmitter.send(SerialEmitter.Destination.SCORE, UPTADE_SCORE,7)



    }


    // Envia comando para desativar/ativar a visualização do mostrador de pontuação
    fun off(value: Boolean) {
        return if (value) SerialEmitter.send(SerialEmitter.Destination.SCORE, SCORE_OFF,7)
        else SerialEmitter.send(SerialEmitter.Destination.SCORE, SCORE_ON,7)

    }
    fun scoreRorate(list: List<Int>){
        val list = list
        for (i in 0 until list.size){
            SerialEmitter.send(SerialEmitter.Destination.SCORE, i or list[i].shl(3),7)
            Time.sleep(4)
        }
        Time.sleep(4)
        SerialEmitter.send(SerialEmitter.Destination.SCORE, UPTADE_SCORE,7)
    }

    fun scoreReset(){
        val list = listOf(0,0,0,0,0,0)
        for (i in 0 until list.size){
            SerialEmitter.send(SerialEmitter.Destination.SCORE, i or list[i].shl(3),7)
            Time.sleep(4)
        }
        Time.sleep(4)
        SerialEmitter.send(SerialEmitter.Destination.SCORE, UPTADE_SCORE,7)

    }

}