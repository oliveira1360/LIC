

fun main(){
    HAL.init()
    SerialEmitter.init()
    ScoreDisplay.setScore(0b000_1000)

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
        return if (value) setScore( 0b111_0000)
        else setScore( 0b111_0001)
    }
}