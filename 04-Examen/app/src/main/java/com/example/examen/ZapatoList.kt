package com.example.examen

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
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
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class ZapatoList : AppCompatActivity() {
    private lateinit var adaptador: ArrayAdapter<Zapato>
    private val zapatos = MemoryDataBase.zapatos
    private lateinit var filterZapatos: ArrayList<Zapato>
    private var itemSeleccionado = -1
    private var currentId: Int = -1

    private val callBackIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                val data = result.data
                showSnackBar("${data?.getStringExtra("action")}")
                val newPlayerId = data?.getIntExtra("id nuevo zapato", -1)
                if (newPlayerId != -1) {
                    filterZapatos.add(zapatos.filter{it.id==newPlayerId}[0])
                }
            }
            adaptador.notifyDataSetChanged()
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.child_object_menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        itemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_p -> {
                openForm(filterZapatos[itemSeleccionado].id)
                return true
            }

            R.id.delete_p -> {
                deleteDialog(itemSeleccionado)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun deleteDialog(item: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar ?")
        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
            val playerId = filterZapatos[item].id
            zapatos.removeIf { it.id == playerId }
            filterZapatos.removeAt(item)
            adaptador.notifyDataSetChanged()
            showSnackBar("Eliminar Registrado")
        })
        builder.setNegativeButton(
            "Cancelar", null
        )
        val dialogo = builder.create()
        dialogo.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zapato_list)

        val team = intent.getStringExtra("nombre tienda")
        val title = findViewById<TextView>(R.id.main_title)
        title.text = team

        val listView = findViewById<ListView>(R.id.list_view_tienda)
        val teamId = intent.getIntExtra("tienda", -1)
        currentId = teamId
        filterZapatos=zapatos.filter { it.tienda  == currentId } as ArrayList<Zapato>

        this.adaptador = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, filterZapatos
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        val botonAnadirListView = findViewById<Button>(
            R.id.btn_crear_tienda
        )
        botonAnadirListView.setOnClickListener {
            val newId = (zapatos.maxBy { it.id }).id + 1
            openForm(newId)
        }

        val backButton = findViewById<Button>(R.id.btn_regresar)
        backButton.setOnClickListener {
            finish()
        }
    }



    private fun openForm(playerId: Int) {
        val explicitIntent = Intent(this, ZapatoForm::class.java);
        explicitIntent.putExtra("zapato_id", playerId)
        explicitIntent.putExtra("tienda_id", currentId)
        callBackIntent.launch(explicitIntent)
    }

    private fun showSnackBar(text: String) {
        Snackbar.make(
            findViewById(R.id.list_view_tienda), text, Snackbar.LENGTH_LONG
        ).show()
    }
}