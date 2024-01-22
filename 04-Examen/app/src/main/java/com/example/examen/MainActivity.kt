package com.example.examen

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var adaptador: ArrayAdapter<Tienda>
    private val teams = MemoryDataBase.tiendas
    private var selectedItem = -1

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.main_object_menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        selectedItem = position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_t -> {
                openForm(teams[selectedItem].id)
                return true
            }

            R.id.delete_t -> {
                deleteDialog(selectedItem)
                return true
            }

            R.id.view_zapatos -> {
                val explicitIntent= Intent(this,ZapatoList::class.java)
                val team= teams[selectedItem]
                explicitIntent.putExtra("Tienda",team.id)
                explicitIntent.putExtra("Tienda Nombre",team.nombreTienda)
                startActivity(explicitIntent)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private val callBackIntent = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                val data = result.data
                showSnackBar("${data?.getStringExtra("action")}")
            }
            adaptador.notifyDataSetChanged()
        }
    }


    private fun deleteDialog(item: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar?")
        builder.setPositiveButton("Aceptar") { _, _ ->
            teams.removeAt(item)
            adaptador.notifyDataSetChanged()
            showSnackBar("Eliminar Registrado")
        }
        builder.setNegativeButton(
            "Cancelar", null
        )
        val dialog = builder.create()
        dialog.show()
    }


    private fun showSnackBar(text: String) {
        Snackbar.make(
            findViewById(R.id.list_view_tienda),
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.list_view_tienda)
        this.adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            teams
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val buttonAddListView = findViewById<Button>(
            R.id.btn_crear_tienda
        )
        buttonAddListView
            .setOnClickListener {
                val newTeamId=(teams.maxBy { it.id }).id+1
                openForm(newTeamId)
            }
        registerForContextMenu(listView)
    }

    private fun openForm(teamId:Int ) {
        val explicitIntent= Intent(this,TiendaForm::class.java)
        explicitIntent.putExtra("id Tienda",teamId)
        callBackIntent.launch(explicitIntent)
    }
}