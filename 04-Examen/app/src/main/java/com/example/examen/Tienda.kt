package com.example.examen

import java.util.*

data class Tienda(
    var id: Int,
    var nombreTienda: String,
    var fechaApertura: String,
    var disponibilidad: Boolean,
){
    override fun toString(): String {
        return nombreTienda;
    }
}
