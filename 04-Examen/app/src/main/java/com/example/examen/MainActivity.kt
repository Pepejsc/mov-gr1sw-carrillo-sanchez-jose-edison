package com.example.examen

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val tiendas: ArrayList<Tienda> = arrayListOf()
    private var selectedItem = -1
    var query: DownloadManager.Query? = null
    private lateinit var adaptador: ArrayAdapter<Tienda>

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
                Log.e("CONTEXT MENU", tiendas[selectedItem].id.toString())
                openForm(tiendas[selectedItem].id.toString())
                return true
            }

            R.id.delete_t -> {
                deleteDialog(tiendas[selectedItem].id.toString())
                return true
            }

            R.id.view_zapatos -> {
                val explicitIntent = Intent(this, ZapatoList::class.java)
                val tienda = tiendas[selectedItem]
                explicitIntent.putExtra("tienda", tienda.id)
                explicitIntent.putExtra("nombreTienda", tienda.nombreTienda)
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
            updateAdapter()
        }
    }

    private fun retrieveAllTeams(adapter: ArrayAdapter<Tienda>) {
        val db = Firebase.firestore
        val citiesRef = db.collection("teams")
        citiesRef.get()
            .addOnSuccessListener { documentSnapshots ->
                guardarQuery(documentSnapshots, citiesRef)
                for (team in documentSnapshots) {
                    Log.e("Firebase function", team.toString())
                    anadirAArregloTeam(team)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {/* si hay fallos*/ }
    }

    fun anadirAArregloTeam(
        document: QueryDocumentSnapshot
    ) {
        val newTeam = Tienda(
            document.data["id"] as Int,
            document.data["nombreTienda"] as String,
            document.data["fechaApertura"] as String,
            document.data["disponiblidad"] as Boolean,
        )
        tiendas.add(newTeam)
    }

    fun guardarQuery(
        documentSnapshots: QuerySnapshot,
        refCities: Query
    ) {
        if (documentSnapshots.size() > 0) {
            val lastDoc = documentSnapshots
                .documents[documentSnapshots.size() - 1]
            refCities
                .startAfter(lastDoc)
        }
    }

    private fun updateAdapter() {
        adaptador.clear()
        retrieveAllTeams(this.adaptador)
        tiendas.forEach {
            adaptador.insert(it, adaptador.count)
        }
    }

    private fun deleteDialog(item: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar la tienda")
        builder.setPositiveButton("Aceptar") { _, _ ->
            val db = Firebase.firestore
            val referenciaTiendas = db.collection("tiendas")
            referenciaTiendas
                .document(item)
                .delete()
                .addOnCompleteListener {updateAdapter()}
                .addOnFailureListener {}
            showSnackBar("Tienda eliminada correctamente")
        }
        builder.setNegativeButton(
            "Cancelar", null
        )
        val dialog = builder.create()
        dialog.show()
    }


    private fun showSnackBar(text: String) {
        Snackbar.make(
            findViewById(R.id.list_view_zapato), text, Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.list_view_zapato)
        this.adaptador = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, tiendas
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        Log.e("Firebase",tiendas.toString())
        retrieveAllTeams(adaptador)
        Log.e("Firebase",tiendas.toString())

        val buttonAddListView = findViewById<Button>(
            R.id.btn_crear_tienda
        )
        buttonAddListView.setOnClickListener {
            openForm("2")
        }
        registerForContextMenu(listView)
    }

    private fun openForm(teamId: String?) {
        val explicitIntent = Intent(this, TeamForm::class.java)
        if (teamId != null) {
            explicitIntent.putExtra("tiendaId", teamId)
        }
        callBackIntent.launch(explicitIntent)
    }
}