package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import java.util.*

class TeamForm : AppCompatActivity() {
    private var tiendaId: String = ""
    private var tienda = Tienda(0, "", "", true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tienda_form)

        tiendaId = intent.getStringExtra("team_id")!!

        consultar(tiendaId)


        val backButton = findViewById<Button>(R.id.btn_cancelar)
        backButton.setOnClickListener {
            exit("Sin cambios")
        }


        val saveButton = findViewById<Button>(R.id.btn_guardar)
        saveButton.setOnClickListener {
            crear()
            if (tienda.id != 2) {
                exit("Tienda Actualizada")
            } else {
                exit("Tienda Creada")
            }
        }
    }

    private fun exit(message: String) {
        val returnIntent = Intent()
        returnIntent.putExtra("action", message)
        setResult(
            RESULT_OK, returnIntent
        )
        finish()
    }


    fun consultar(id: String): Task<DocumentSnapshot> {
        val db = Firebase.firestore
        val citiesRefUnico = db.collection("teams")
        return citiesRefUnico.document(id).get().addOnSuccessListener { it ->
            if (it.data != null) {
                Log.e("TEAM", it.toString())
                tienda = Tienda(
                    it.data?.get("id") as Int,
                    it.data?.get("nombre") as String,
                    it.data!!["fechaApertura"] as String,
                    it.data!!["disponibilidad"] as Boolean
                )
                var id = it.id
                "ID: $id".also { findViewById<TextView>(R.id.id_tienda).text = id }
                findViewById<EditText>(R.id.nombre_tienda).setText(tienda.nombreTienda)
                findViewById<EditText>(R.id.fecha_apertura).setText(tienda.fechaApertura)
                findViewById<CheckBox>(R.id.disponibilidad_tienda).isChecked = tienda.disponibilidad
            }

        }.addOnFailureListener {

        }
    }

    fun crear() {
        val db = com.google.firebase.ktx.Firebase.firestore
        val coll = db.collection("tiendas")
        val tiendaInfo = hashMapOf(
            "nombre" to this.findViewById<TextView>(R.id.nombre_tienda).text.toString(),
            "fechaApertura" to this.findViewById<TextView>(R.id.fecha_apertura).text.toString(),
            "disponibilidad" to this.findViewById<CheckBox>(R.id.disponibilidad_tienda).isChecked,
        )
        if (tienda.id != 0) {
            coll
                .document(tienda.id.toString())
                .set(tiendaInfo)
                .addOnSuccessListener { }
                .addOnFailureListener { }
        } else {
            coll
                .add(tiendaInfo)
                .addOnCompleteListener { }
                .addOnFailureListener { }
        }
    }
}

