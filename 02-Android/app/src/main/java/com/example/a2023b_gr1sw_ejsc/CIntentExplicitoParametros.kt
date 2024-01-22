package com.example.a2023b_gr1sw_ejsc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
    }
    val nombre = intent.getStringExtra("nombre")
    val apellido = intent.getStringExtra("apellido")
    val edad = intent.getIntExtra("edad", 0)
    //mostrarSnackbar("${nombre} ${apellido} ${edad}")
    val boton = findViewById<Button>(R.id.btn_devolver_respuesta)

    fun devolverRespuesta(){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nombreModificado", "Jose")
        intentDevolverParametros.putExtra("edadModificado", 25)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.id_layout_main), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiempo
            )
            .show()
    }
}



