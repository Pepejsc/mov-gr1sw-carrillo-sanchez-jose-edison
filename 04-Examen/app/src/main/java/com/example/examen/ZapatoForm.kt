package com.example.examen

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ZapatoForm : AppCompatActivity() {
    private val zapatos = DataBaseFirebase.zapatos
    private var zapatoId = -1
    private var tiendaId = -1
    val calendar = Calendar.getInstance()
    private lateinit var selectedDateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zapato_form)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        zapatoId = intent.getIntExtra("zapato_id", -1)
        tiendaId = intent.getIntExtra("tienda_id", -1)
        val zapato = zapatos.find { it.id == zapatoId }

        selectedDateTextView = findViewById(R.id.fecha_ingreso_zapato)
        selectedDateTextView.setOnClickListener {
            showDatePickerDialog()
        }

        if (zapato != null) {
            val selectedDate = dateFormat.format(zapato.fechaIngreso)
            selectedDateTextView.text = selectedDate
            findViewById<EditText>(R.id.zapato_nombre).setText(zapato.nombre)
            findViewById<EditText>(R.id.costo_zapato).setText(zapato.valor.toString())
            findViewById<CheckBox>(R.id.disponible_zapato).isChecked = zapato.disponibilidad
        }

        val backButton = findViewById<Button>(R.id.btn_cancel)
        backButton.setOnClickListener {
            exit("Sin cambios")
        }


        val saveButton = findViewById<Button>(R.id.btn_guardar_zapato)
        saveButton.setOnClickListener {
            if (zapato != null) {
                zapato.nombre = this.findViewById<TextView>(R.id.zapato_nombre).text.toString()
                zapato.valor =
                    this.findViewById<TextView>(R.id.costo_zapato).text.toString().toFloat()
                zapato.disponibilidad = this.findViewById<CheckBox>(R.id.disponible_zapato).isChecked
                zapato.fechaIngreso =
                    dateFormat.parse(this.findViewById<TextView>(R.id.fecha_ingreso_zapato).text.toString())!!
                exit("Zapato Actualizado Exitosamente")
            } else {
                zapatos.add(
                    Zapato(
                        zapatoId,
                        dateFormat.parse(this.findViewById<TextView>(R.id.fecha_ingreso_zapato).text.toString())!!,
                        this.findViewById<CheckBox>(R.id.disponible_zapato).isChecked,
                        this.findViewById<TextView>(R.id.zapato_nombre).text.toString(),
                        this.findViewById<TextView>(R.id.costo_zapato).text.toString().toFloat(),
                        tiendaId
                    )
                )
                println(zapatos)
                exit("Zapato Creado Exitosamente",zapatoId)
            }
        }
    }

    private fun exit(message: String,newPlayer: Int) {
        val returnIntent = Intent()
        returnIntent.putExtra("action", message)
        returnIntent.putExtra("id zapato nuevo", newPlayer)
        println(message)
        println(newPlayer)
        setResult(
            RESULT_OK, returnIntent
        )
        finish()
    }

    private fun exit(message: String) {
        val returnIntent = Intent()
        returnIntent.putExtra("action", message)
        setResult(
            RESULT_OK, returnIntent
        )
        finish()
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateSelectedDate()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun updateSelectedDate() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val selectedDate = dateFormat.format(calendar.time)
        selectedDateTextView.text = selectedDate
    }
}

