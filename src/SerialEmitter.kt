object SerialEmitter { // Envia tramas para os diferentes módulos Serial Receiver.
    enum class Destination {LCD, SCORE}

    // Inicia a classe
    fun init() {

    }

    // Envia uma trama para o SerialReceiver identificado o destino em addr,os bits de dados em ‘data’ e em size o número de bits a enviar.
    fun send(addr: Destination, data: Int, size : Int) {

    }
}