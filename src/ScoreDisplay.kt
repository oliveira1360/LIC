object ScoreDisplay { // Controla o mostrador de pontuação.
    // Inicia a classe, estabelecendo os valores iniciais.

    fun init() {


    }


    // Envia comando para atualizar o valor do mostrador de pontuação
    fun setScore(value: Int) {
        SerialEmitter.send(SerialEmitter.Destination.SCORE, value,8)

    }


    // Envia comando para desativar/ativar a visualização do mostrador de pontuação
    fun off(value: Boolean) {

    }
}