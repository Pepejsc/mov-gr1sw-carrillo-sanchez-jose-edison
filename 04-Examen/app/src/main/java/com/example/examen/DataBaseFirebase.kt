package com.example.examen

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


val firestore = FirebaseFirestore.getInstance()

class FirebaseDB {
    companion object {

        fun retrieveAllTeams() {
            val collectionRef = firestore.collection("teams")
            val teams = ArrayList<Tienda>()
            collectionRef.get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot) {
                        val data = document.data
                        Log.e("FIREBASE", document.toString())

                        Log.e("FIREBASE", data.toString())

                        teams.add(
                            Tienda(
                                document.data["1"] as Int,
                                document.data["nombreTienda"] as String,
                                document.data["fechaApertura"] as String,
                                document.data["disponiblidad"] as Boolean
                            )
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("FIREBASE", "Error getting documents: $exception")
                }
        }

        fun recuperarTienda(teamId: String?): Tienda {
            return Tienda(1, "a", "1990-01-01", true, )
        }

        fun crearTienda(toString: String, toString1: String, toFloat: Float, checked: Boolean) {

        }

        fun actualizarTienda(
            toString: String,
            toString1: String,
            toFloat: Float,
            checked: Boolean,
            id: String
        ) {

        }

        fun eliminarTienda(item: String, s: String) {

        }


        fun recuperarZapato(playerId: Int): Zapato {
            return Zapato(1, "2023-01-03", true, "adidas", 100,1)
        }

        fun actualizarZapato(
            selectedDate: String,
            checked: Boolean,
            toString: String,
            toFloat: Float,
            teamId: Int,
            playerId: Int
        ) {

        }

        fun crearZapato(
            toString: String,
            checked: Boolean,
            toString1: String,
            toFloat: Float,
            teamId: Int
        ) {

        }

        fun retrieveByTeam(currentId: Int): ArrayList<Zapato>? {
            return ArrayList<Zapato>()
        }

    }

}