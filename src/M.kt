private const val M = 0b1000_0000

//retorna se o o modo de manutencao esta ativo o nao
fun read_mode(): Boolean = HAL.isBit(M)
