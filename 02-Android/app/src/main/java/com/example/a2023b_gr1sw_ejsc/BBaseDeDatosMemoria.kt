package com.example.a2023b_gr1sw_ejsc

class BBaseDeDatosMemoria {

    //Companion object

    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()

        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1, "Adrian", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2, "Edison", "b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3, "Jose", "c@c.com")
                )
        }
    }
}