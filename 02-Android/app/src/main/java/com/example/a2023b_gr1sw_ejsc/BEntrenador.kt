package com.example.a2023b_gr1sw_ejsc

class BEntrenador(
    var id:Int,
    var nombre:String?,
    var descripcion:String?
) {
    override fun toString(): String {
        return "${nombre} - ${descripcion}"
    }
}