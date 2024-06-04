private const val FILE = "SIG_scores.txt"
fun main() {
    //Scores.addScores(Scores.Score("70", "batata"))
    println(Scores.getScores())

}
object Scores{
    data class Score(val score:String, val name:String)
    fun getScores(inputFile: String = FILE) : MutableList<Score>{
        val scoreList = emptyList<Score>().toMutableList()
        val receiveList = FileAccess.fileRead(inputFile)
        receiveList.forEach {
            if (it.isNotEmpty()) {
                val splitFields = it.split(";")
                scoreList.add(Score(splitFields[0],splitFields[1]))
            }
        }
        return scoreList
    }

    fun addScores(newScore: Score, inputFile: String= FILE){
        val receiveList = getScores(inputFile)
        if (receiveList.size < 20) receiveList.add(newScore)
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
        }

    }


}