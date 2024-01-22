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

class TiendaForm : AppCompatActivity() {
    private val tiendasCreadas = MemoryDataBase.tiendas
    private var tiendaId = -1
    val calendar = Calendar.getInstance()
    private lateinit var selectedDateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tienda_form)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        tiendaId = intent.getIntExtra("tienda_id", -1)
        val team = tiendasCreadas.find { it.id == tiendaId }
        //"ID: $teamId".also { findViewById<TextView>(R.id.id_label_t).text = it }

        selectedDateTextView = findViewById(R.id.fecha_apertura)
        selectedDateTextView.setOnClickListener {
            showDatePickerDialog()
        }

        if (team != null) {
            val selectedDate = dateFormat.format(team.fechaApertura)
            selectedDateTextView.text = selectedDate
            findViewById<EditText>(R.id.nombre_tienda).setText(team.nombreTienda)
            findViewById<EditText>(R.id.id_tienda).setText(team.netIncome.toString())
            findViewById<CheckBox>(R.id.disponibilidad_tienda).isChecked = team.disponibilidad
        }

        val backButton = findViewById<Button>(R.id.btn_cancelar)
        backButton.setOnClickListener {
            exit("Sin cambios")
        }


        val saveButton = findViewById<Button>(R.id.btn_guardar)
        saveButton.setOnClickListener {
            if (team != null) {
                team.nombreTienda = this.findViewById<TextView>(R.id.nombre_tienda).text.toString()
                team.netIncome =
                    this.findViewById<TextView>(R.id.id_tienda).text.toString().toFloat()
                team.disponibilidad = this.findViewById<CheckBox>(R.id.disponibilidad_tienda).isChecked
                team.fechaApertura =
                    dateFormat.parse(this.findViewById<TextView>(R.id.fecha_apertura).text.toString())!!
                exit("Tienda Actualizada Exitosamente")
            } else {
                tiendasCreadas.add(
                    Tienda(
                        tiendaId, this.findViewById<TextView>(R.id.nombre_tienda).text.toString(),
                        dateFormat.parse(this.findViewById<TextView>(R.id.fecha_apertura).text.toString())!!,
                        this.findViewById<TextView>(R.id.id_tienda).text.toString().toFloat(),
                        this.findViewById<CheckBox>(R.id.disponibilidad_tienda).isChecked,
                    )
                )
                exit("Tienda Creada Exitosamente")

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

