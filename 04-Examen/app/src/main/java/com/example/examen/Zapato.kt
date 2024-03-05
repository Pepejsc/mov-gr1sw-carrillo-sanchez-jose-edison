package com.example.examen

import java.util.*

data class Zapato(
    var id: Int,
    var fechaIngreso: String,
    var disponibilidad: Boolean,
    var nombre: String,
    var valor: Int,
    var tienda: Int
){
    override fun toString(): String {
        return "$nombre";
    }
}
