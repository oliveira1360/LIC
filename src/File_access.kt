

import java.io.File
import java.io.FileWriter

fun main() {

}
object FileAccess{
    fun fileRead(inputFile: String) : MutableList<String>{
        val emptyList = emptyList<String>().toMutableList()
        val inFile = File(inputFile)
        val sequence = inFile.readLines()
        sequence.forEach {emptyList.add(it)}
        return emptyList
    }
    fun fileWrite(text: String, outputFile: String) {
        val output = FileWriter(outputFile,true)
        output.appendLine(text)
        output.close()
    }

    fun FileClear(inputFile: String) = FileWriter(inputFile, false).close();

}