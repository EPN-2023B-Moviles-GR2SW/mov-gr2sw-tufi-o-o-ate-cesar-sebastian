package com.example.deber02

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
import com.example.deber02.entidad.Condominio
import com.example.deber02.sql.BaseDeDatos
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate

class CondominiosVer : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var idCondominioSeleccionado = 0
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Condominio>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condominios_ver)
        listView = findViewById<ListView>(R.id.lv_condominio_ver)
        adaptador = ArrayAdapter(
            this, // Contexto
            android.R.layout.simple_list_item_1, // como se va a ver (XML)
            BaseDeDatos.tablaCondominio!!.consultarTodosLosCondominios()
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonCrearCondominio = findViewById<Button>(R.id.btn_crear_condominio)
        botonCrearCondominio
            .setOnClickListener {
                crearCondominio()
            }
        registerForContextMenu(listView)
    }

    fun crearCondominio() {
        val condominio =
            Condominio(
                null,
                "Jardín",
                "Llano Chico",
                LocalDate.now(),
                false,
                false
            )
        BaseDeDatos.tablaCondominio!!.crearCondominio(condominio)
        adaptador.clear()
        adaptador.addAll(BaseDeDatos.tablaCondominio!!.consultarTodosLosCondominios())
        adaptador.notifyDataSetChanged()
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
        val condominioSeleccionado = BaseDeDatos.tablaCondominio!!.consultarTodosLosCondominios()[posicion]
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
        id: Int
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
            BaseDeDatos.tablaCondominio!!.eliminarCondominioPorID(idCondominioSeleccionado)
            mostrarSnackbar("Elemento id:${idCondominioSeleccionado} eliminado")
            adaptador.clear()
            adaptador.addAll(BaseDeDatos.tablaCondominio!!.consultarTodosLosCondominios())
            adaptador.notifyDataSetChanged()
        }
        builder.setNegativeButton(
            "Cancelar",
            null
        )
        val dialogo = builder.create()
        dialogo.show()
    }

    override fun onResume() {
        super.onResume()
        adaptador.clear()
        adaptador.addAll(BaseDeDatos.tablaCondominio!!.consultarTodosLosCondominios())
        adaptador.notifyDataSetChanged()
    }
}