import isel.leic.UsbPort

fun main() {
        LCD.init()
        KBD.init()
        while (true) {

                Thread.sleep(1000)
                val teste = KBD.getKey()
                LCD.write(teste.toString())
                if (teste != ' ')
                        LCD.write(teste.toString())
                Thread.sleep(1000)
                LCD.clear()

        }


}