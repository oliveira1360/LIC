private const val FILE = "SIG_scores.txt"
fun main() {
    Scores.addScores(FILE, Scores.Score("0", "Diogo"))
}
object Statistics{
    data class Statistic(val coins:String, val games:String)
        fun getStatistics(inputFile: String, scoresList : MutableList<Statistic>) {
        val receiveList = emptyList<String>().toMutableList()
        FileAccess.fileRead(inputFile,receiveList)
        receiveList.forEach {
            if (it.isNotEmpty()) {
                val splitFields = it.split(";")
                scoresList.add(Statistic(splitFields[0],splitFields[1]))
            }
        }
    }

    fun addStatistic(inputFile: String, newScore: Statistic){
        FileAccess.FileClear(inputFile)
        FileAccess.fileWrite(newScore.coins + ";" + newScore.games, inputFile)
    }


}