import isel.leic.UsbPort
import isel.leic.utils.Time


const val SCORE_INCREASE = 1
const val MOSTER_SPAW_LINE_1_COORDENATES = 20
const val MOSTER_SPAW_LINE_2_COORDENATES = 7
const val digitArray = "1472580369"

fun main() {
        var Linah1 = ""
        var Linah2 = ""
        var Pos1 = 15
        var Pos2 = 15
        HAL.init()
        LCD.init()
        KBD.init()
        SerialEmitter.init()
        SerialEmitter.init()



        //spwan mostros
        //caso e tecla premida seja igual ao primeiro mostro nessa linha ilimar o mostro e aumenta o score em o valor do numero mais 1
        // mover entre linhas com '#'
        var tempo = 10000_0000_0000L
        while (Pos1 > 0 && Pos2 > 0){
                val random= (0..9).random()
                Linah1 += digitArray[random]
                LCD.cursor(0, Pos1)
                LCD.write(Linah1)

                LCD.cursor(1, Pos2)
                val random2= (0..9).random()
                Linah2 += digitArray[random2]
                LCD.write(Linah2)
                Pos1--
                Pos2--
                while (tempo != 0L)
                {
                        tempo -= 50
                        var ultimaTecla = ' '
                        val tecla = KBD.waitKey(1734552400000)
                        if (tecla == '#') {
                                if (ultimaTecla == Linah1[0]) {
                                        Linah1.substring(0)
                                }
                                ultimaTecla = tecla
                        }

                }
                tempo = 10000_0000_00L


        }
        LCD.clear()
        LCD.write("NABOS :(")





}