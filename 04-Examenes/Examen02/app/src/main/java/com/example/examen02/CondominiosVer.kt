package com.example.examen02

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.examen02.entidad.Condominio
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.ZoneId
import java.util.Date

class CondominiosVer : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var idCondominioSeleccionado = ""
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Condominio>
    var query: Query? = null
    val arreglo: ArrayList<Condominio> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condominios_ver)
        listView = findViewById<ListView>(R.id.lv_condominio_ver)
        adaptador = ArrayAdapter(
            this, // Contexto
            android.R.layout.simple_list_item_1, // como se va a ver (XML)
            arreglo
        )
        listView.adapter = adaptador
        //consultarColeccion()
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
        val botonCrearCondominio = findViewById<Button>(R.id.btn_crear_condominio)
        botonCrearCondominio
            .setOnClickListener {
                crearCondominio()
            }
    }

    fun consultarColeccion() {
        val db = Firebase.firestore
        val condominiosRef = db.collection("condominio")
        var tarea: Task<QuerySnapshot>? = null
        tarea = condominiosRef.get() // 1era vez
        limpiarArreglo()
        //adaptador.notifyDataSetChanged()
        if (tarea != null) {
            tarea
                .addOnSuccessListener { documentSnapshots ->
                    guardarQuery(documentSnapshots, condominiosRef)
                    for (condominio in documentSnapshots) {
                        anadirAArreglo(condominio)
                    }
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener { }
        }
    }

    fun limpiarArreglo() {
        arreglo.clear()
    }

    fun guardarQuery(
        documentSnapshots: QuerySnapshot,
        ref: Query
    ) {
        if (documentSnapshots.size() > 0) {
            val ultimoDocumento = documentSnapshots
                .documents[documentSnapshots.size() - 1]
            query = ref
                // Start After nos ayuda a paginar
                .startAfter(ultimoDocumento)
        }
    }

    fun anadirAArreglo(
        document: QueryDocumentSnapshot
    ) {
        val condominio = Condominio(
            document.id as String?,
            document.data.get("nombre") as String,
            document.data.get("direccion") as String,
            (document.data.get("fechaDeConstruccion") as Timestamp).toDate().toInstant().atZone(
                ZoneId.systemDefault()
            ).toLocalDate(),
            document.data.get("tienePiscina") as Boolean,
            document.data.get("tieneCancha") as Boolean
        )
        arreglo.add(condominio)
    }

    fun crearCondominio() {
        val db = Firebase.firestore
        val condominiosRef = db.collection("condominio")
        val datosCondominio = hashMapOf(
            "nombre" to "Jardín",
            "direccion" to "Llano Chico",
            "fechaDeConstruccion" to Timestamp.now(),
            "tienePiscina" to false,
            "tieneCancha" to false,
        )
        val identificador = Date().time
        condominiosRef // (crear/actualizar)
            .document(identificador.toString())
            .set(datosCondominio)
            .addOnSuccessListener { }
            .addOnFailureListener { }
        consultarColeccion()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_condominio, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        // Acceder al objeto Condominio en la posición seleccionada
        val condominioSeleccionado = arreglo[posicion]
        // Obtener el id del Condominio seleccionado
        idCondominioSeleccionado = condominioSeleccionado.id!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_condominio -> {
                irActividadConId(CondominioEditar::class.java, idCondominioSeleccionado)
                return true
            }

            R.id.mi_eliminar_condominio -> {
                abrirDialogo()
                return true
            }

            R.id.mi_ver_departamentos -> {
                irActividadConId(DepartamentosVer::class.java, idCondominioSeleccionado)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividadConId(
        clase: Class<*>,
        id: String
    ) {
        val intent = Intent(this, clase)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_condominio_ver),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar"
        ) { dialog, which ->
            eliminarRegistro(idCondominioSeleccionado)
            mostrarSnackbar("Elemento id:${idCondominioSeleccionado} eliminado")
        }
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val dialogo = builder.create()
        dialogo.show()
    }

    fun eliminarRegistro(id: String) {
        val db = Firebase.firestore
        val condominiosRef = db.collection("condominio")

        condominiosRef
            .document(id)
            .delete() // elimina
            .addOnCompleteListener { }
            .addOnFailureListener { }
        consultarColeccion()
        //adaptador.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        consultarColeccion()
        // adaptador.notifyDataSetChanged()
    }
}