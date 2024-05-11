

fun main(){
    HAL.init()
    SerialEmitter.init()
    ScoreDisplay.setScore(0b0001_000)
    ScoreDisplay.setScore(0b0010_001)
    ScoreDisplay.setScore(0b0011_010)

    ScoreDisplay.setScore(0b1001_011)
    ScoreDisplay.setScore(0b1010_110)    //uptade



}



object ScoreDisplay { // Controla o mostrador de pontuação.
    // Inicia a classe, estabelecendo os valores iniciais.

    fun init() {

    }


    // Envia comando para atualizar o valor do mostrador de pontuação
    fun setScore(value: Int) {
        SerialEmitter.send(SerialEmitter.Destination.SCORE, value,7)

    }


    // Envia comando para desativar/ativar a visualização do mostrador de pontuação
    fun off(value: Boolean) {
        return if (value) setScore( 0b0001_111)
        else setScore( 0b0000_111)
    }
}