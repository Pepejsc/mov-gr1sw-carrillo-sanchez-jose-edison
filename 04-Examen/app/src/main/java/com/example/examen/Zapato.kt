package com.example.examen

import java.util.*

data class Zapato(
    var id: Int,
    var fechaIngreso: Date,
    var disponibilidad: Boolean,
    var nombre: String,
    var valor: Float,
    var tienda: Int
){
    override fun toString(): String {
        return "$nombre";
    }
}
