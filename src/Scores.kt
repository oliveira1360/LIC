const val FILE = "SIG_scores.txt"
fun main() {
    Scores.addScores(FILE, Scores.Score("0", "Diogo"))
}
object Scores{
    data class Score(val score:String, val name:String)
    fun getScores(inputFile: String, scoresList : MutableList<Score>) {
        val receiveList = emptyList<String>().toMutableList()
        FileAccess.fileRead(inputFile,receiveList)
        receiveList.forEach {
            if (it.isNotEmpty()) {
                val splitFields = it.split(";")
                scoresList.add(Score(splitFields[0],splitFields[1]))
            }
        }
    }

    fun addScores(inputFile: String, newScore: Score){
        val receiveList = emptyList<Score>().toMutableList()
        getScores(inputFile, receiveList)
        receiveList.add(newScore)
        receiveList.sortByDescending { it.score.toInt() }
        println(receiveList)
        FileAccess.FileClear(inputFile)
        receiveList.forEach {
            FileAccess.fileWrite(it.score + ";" + it.name, inputFile)
        }

    }


}