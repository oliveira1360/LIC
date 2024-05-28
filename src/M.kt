private const val M = 0b1000_0000
fun read_mode(): Boolean = HAL.isBit(M)
