package com.example.examen02

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
import androidx.appcompat.app.AlertDialog
import com.example.examen02.entidad.Departamento
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

class DepartamentosVer : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var idDepartamentoSeleccionado = ""
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Departamento>
    var query: Query? = null
    val arreglo: ArrayList<Departamento> = arrayListOf()
    var idCondominio: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamentos_ver)
        // Recupera el ID
        val intent = intent
        // Buscar Departamentos
        buscarCondominio(intent.getStringExtra("id")!!)
        val botonCrearDepartamento = findViewById<Button>(R.id.btn_crear_departamento)
        botonCrearDepartamento
            .setOnClickListener {
                crearDepartamento()
            }
    }

    fun crearDepartamento() {
        if (idCondominio == null) {
            return
        }
        val db = Firebase.firestore
        val departamentosRef = db.collection("condominio/${idCondominio}/departamentos")
        val datosDepartamento = hashMapOf(
            "numero" to 0,
            "inquilino" to "Julian Tufiño",
            "cantidadDeHabitaciones" to 2,
            "area" to 20.0,
            "tieneBalcon" to false
        )
        val identificador = Date().time
        departamentosRef // (crear/actualizar)
            .document(identificador.toString())
            .set(datosDepartamento)
            .addOnSuccessListener { }
            .addOnFailureListener { }
        consultarColeccion()
    }

    fun buscarCondominio(id: String) {
        val db = Firebase.firestore
        val condominioRef = db.collection("condominio")

        condominioRef
            .document(id)
            .get()
            .addOnSuccessListener {
                idCondominio = id
                val nombreCondominio = findViewById<TextView>(R.id.tv_nombre_condominio)
                nombreCondominio.setText(it.data?.get("nombre") as String)

                listView = findViewById<ListView>(R.id.lv_departamento_ver)
                adaptador = ArrayAdapter(
                    this, // Contexto
                    android.R.layout.simple_list_item_1, // como se va a ver (XML)
                    arreglo
                )
                listView.adapter = adaptador
                consultarColeccion()
                registerForContextMenu(listView)
            }
            .addOnFailureListener { }
    }

    fun consultarColeccion() {
        if (idCondominio == null) {
            return
        }
        val db = Firebase.firestore
        val departamentosRef = db.collection("condominio/${idCondominio}/departamentos")
        var tarea: Task<QuerySnapshot>? = null
        tarea = departamentosRef.get() // 1era vez
        limpiarArreglo()
        //adaptador.notifyDataSetChanged()
        if (tarea != null) {
            tarea
                .addOnSuccessListener { documentSnapshots ->
                    guardarQuery(documentSnapshots, departamentosRef)
                    for (departamento in documentSnapshots) {
                        anadirAArreglo(departamento)
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
        val departamento = Departamento(
            document.id as String?,
            (document.data.get("numero") as Long).toInt(),
            document.data.get("inquilino") as String,
            (document.data.get("cantidadDeHabitaciones") as Long).toInt(),
            document.data.get("area") as Double,
            document.data.get("tieneBalcon") as Boolean
        )
        arreglo.add(departamento)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_departamento, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        // Acceder al objeto Condominio en la posición seleccionada
        val departamentoSeleccionado = arreglo[posicion]
        // Obtener el id del Condominio seleccionado
        idDepartamentoSeleccionado = departamentoSeleccionado.id!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_departamento -> {
                irActividadConId(DepartamentoEditar::class.java, idDepartamentoSeleccionado)
                return true
            }

            R.id.mi_eliminar_departamento -> {
                abrirDialogo()
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
        intent.putExtra("idCondominio", idCondominio);
        intent.putExtra("idDepartamento", id);
        startActivity(intent)
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_departamento_ver),
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
            eliminarRegistro(idDepartamentoSeleccionado)
            mostrarSnackbar("Elemento id:${idDepartamentoSeleccionado} eliminado")
        }
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val dialogo = builder.create()
        dialogo.show()
    }

    fun eliminarRegistro(id: String) {
        if (idCondominio == null) {
            return
        }
        val db = Firebase.firestore
        val departamentosRef = db.collection("condominio/${idCondominio}/departamentos")

        departamentosRef
            .document(id)
            .delete() // elimina
            .addOnCompleteListener { }
            .addOnFailureListener { }
        consultarColeccion()
    }

    override fun onResume() {
        super.onResume()
        consultarColeccion()
    }

}