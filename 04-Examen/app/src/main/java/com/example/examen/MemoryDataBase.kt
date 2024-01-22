package com.example.examen

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MemoryDataBase {

    companion object {
        val tiendas: ArrayList<Tienda>
        val zapatos: ArrayList<Zapato>
        var tiendaZapatos: ArrayList<Zapato>


        fun filterTeamPlayers(teamId: Int) {
            tiendaZapatos = zapatos.filter { it.tienda == teamId } as ArrayList<Zapato>
        }

        init {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            tiendas = arrayListOf(
                Tienda(0, "Puma", dateFormatter.parse("1990-01-01")!!, 70.0F, true)
            )
            zapatos = arrayListOf(
                Zapato(
                    id = 1,
                    fechaIngreso = dateFormatter.parse("2023-05-20")!!,
                    disponibilidad = false,
                    nombre = "Marathon",
                    valor = 75.5f,
                    tienda = 1
                )
            )
            tiendaZapatos = zapatos
        }
    }
}