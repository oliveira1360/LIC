import isel.leic.UsbPort

object HAL { // Virtualiza o acesso ao sistema UsbPort
    var plate_value : Int = 0

    // Inicia a classe
    fun init(){
        plate_value = 0b0000
        UsbPort.write(plate_value)
    }

    // Retorna true se o bit tiver o valor lógico ‘1’
    fun isBit(mask: Int) : Boolean = mask.and( UsbPort.read()) == mask

    // Retorna os valores dos bits representados por mask presentes no UsbPort
    fun readBits(mask: Int): Int {
        val values = UsbPort.read()
        return values.and(mask)
    }

    // Escreve nos bits representados por mask os valores dos bits correspondentes em value
    fun writeBits(mask: Int, value: Int) {
        val m = mask and value
        plate_value = plate_value or m
        UsbPort.write(plate_value)
    }

    // Coloca os bits representados por mask no valor lógico ‘1’
    fun setBits(mask: Int) {
        val valor = plate_value.or(mask)
        plate_value = valor
        print(plate_value)
        UsbPort.write(plate_value)
    }

    // Coloca os bits representados por mask no valor lógico ‘0’
    fun clrBits(mask: Int) {
        val valor = plate_value.and(mask.inv())
        plate_value = valor
        UsbPort.write(plate_value)
    }
}