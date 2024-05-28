private const val FILE = "SIG_scores.txt"
fun main() {
    Scores.addScores(FILE, Scores.Score("70", "batata"))
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
        if (receiveList.size < 6) receiveList.add(newScore)
        else{
            val ele = receiveList.lastOrNull {it.score.toInt() < newScore.score.toInt()}
            if (ele != null){
                receiveList.remove(ele)
                receiveList.add(newScore)
            }

        }
        receiveList.sortByDescending { it.score.toInt() }
        FileAccess.FileClear(inputFile)
        receiveList.forEach {
            FileAccess.fileWrite(it.score + ";" + it.name, inputFile)
            println(it)
        }

    }


}