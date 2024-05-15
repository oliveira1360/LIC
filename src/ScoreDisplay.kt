
const val UPTADE_SCORE = 0b1010_110
const val SCORE_ON = 0b0000_111
const val SCORE_OFF = 0b0001_111
fun main(){
    SerialEmitter.init()
    ScoreDisplay.init()
    ScoreDisplay.setScore(0b0001_000)
    ScoreDisplay.setScore(0b0010_001)
    ScoreDisplay.setScore(0b0011_010)

    ScoreDisplay.setScore(0b1001_011)
    ScoreDisplay.setScore(UPTADE_SCORE)    //uptade

}



object ScoreDisplay { // Controla o mostrador de pontuação.
    // Inicia a classe, estabelecendo os valores iniciais.

    fun init() {
        setScore(SCORE_ON)
    }


    // Envia comando para atualizar o valor do mostrador de pontuação
    fun setScore(value: Int) {
        SerialEmitter.send(SerialEmitter.Destination.SCORE, value,7)

    }


    // Envia comando para desativar/ativar a visualização do mostrador de pontuação
    fun off(value: Boolean) {
        return if (value) setScore(SCORE_OFF)
        else setScore(SCORE_ON)
    }
}