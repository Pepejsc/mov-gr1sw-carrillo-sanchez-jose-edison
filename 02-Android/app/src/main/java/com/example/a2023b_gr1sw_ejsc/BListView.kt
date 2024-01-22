package com.example.a2023b_gr1sw_ejsc

import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.AlertDialog
import com.google.android.material.snackbar.Snackbar

class BListView : AppCompatActivity() {
    val arreglo = BBaseDeDatosMemoria.arregloBEntrenador
    var posicionItemSeleccionado = -1

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        //obtener el id del arraylistseleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar -> {
                mostrarSnackbar("${posicionItemSeleccionado}")
                return true
            }
            R.id.mi_eliminar -> {
                mostrarSnackbar("${posicionItemSeleccionado}")
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                mostrarSnackbar("Eliminar aceptado")
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val opciones = resources.getStringArray(
            R.array.strign_array_opciones_dialogo
        )
        val seleccionPrevia = booleanArrayOf(
            true, //lunes seleccionado
            false, //martes no seleccionado
            false // miercoles no seleccionado
        )
        builder.setMultiChoiceItems(
            opciones,
            seleccionPrevia,
            { dialog,
              which,
              isChecked ->
                "Dio clic en el item ${which}"

            }
        )
        val dialogo = builder.create()
        dialogo.show()
    }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.lv_list_view), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiempo
            )
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val lisView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,//como se vera
            arreglo

        )
        lisView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(
            R.id.btn_anadir_list_view
        )
        botonAnadirListView
            .setOnClickListener{
                anadirEntrenador(adaptador)
            }
        registerForContextMenu(lisView)
    }
    fun anadirEntrenador(
        adaptador:ArrayAdapter<BEntrenador>
    ){
        arreglo.add(

            BEntrenador(
                1,
                "Adrian",
                "Descripcion"
            )
        )
        adaptador.notifyDataSetChanged()
    }
}