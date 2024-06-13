private const val FILE = "statistics.txt"
fun main() {
   val a = Statistics.getStatistics()[0]
    Statistics.updateStatistic(a.copy(coins = 40))
    println(Statistics.getStatistics())
}
object Statistics{
    data class Statistic(val coins:Int, val games:Int)
        fun getStatistics(inputFile: String = FILE) :  MutableList<Statistic>{
            val statsList = emptyList<Statistic>().toMutableList()
            val receiveList = FileAccess.fileRead(inputFile)
            receiveList.forEach {
                if (it.isNotEmpty()) {
                    val splitFields = it.split(";")
                    statsList.add(Statistic(splitFields[0].toInt(),splitFields[1].toInt()))
                }
            }
            return statsList
    }

    fun updateStatistic(newStatistic: Statistic, inputFile: String=FILE){
        FileAccess.FileClear(inputFile)
        FileAccess.fileWrite("" + newStatistic.coins + ";" + newStatistic.games, inputFile)
    }

}