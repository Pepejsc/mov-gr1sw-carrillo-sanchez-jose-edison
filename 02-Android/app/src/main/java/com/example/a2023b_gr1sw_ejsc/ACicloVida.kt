package com.example.a2023b_gr1sw_ejsc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
class ACicloVida : AppCompatActivity() {

    var textoGlobal = ""

    fun mostrarSnackbar(texto:String){
        textoGlobal +=  texto
        Snackbar
            .make(
                findViewById(R.id.cl_ciclo_vida),
                textoGlobal,
                Snackbar.LENGTH_INDEFINITE
            ).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
    }

    override fun onStart() {
        super.onStart()
        mostrarSnackbar("onStart")
    }

    override fun onResume() {
        super.onResume()
        mostrarSnackbar("onResume")
    }

    override fun onPause() {
        super.onPause()
        mostrarSnackbar("onPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarSnackbar("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackbar("onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarSnackbar("onRestart")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run{
            //Guardar variables prmitivas
            putString("textoGuardado", textoGlobal)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        //recuperar las varibales primitivas
        val textoRecuperado:String? = savedInstanceState
            .getString("textoGuardado")

        if (textoRecuperado != null){
            mostrarSnackbar(textoRecuperado)
            textoGlobal = textoRecuperado
        }
    }
}