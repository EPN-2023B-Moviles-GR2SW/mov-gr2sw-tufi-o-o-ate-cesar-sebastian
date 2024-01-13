package com.example.examenbi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.examenbi.dao.CondominioDAO
import com.example.examenbi.dao.DepartamentoDAO
import com.example.examenbi.entidad.Condominio
import com.example.examenbi.entidad.Departamento
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate

class DepartamentosVer : AppCompatActivity() {
    var arregloDespartamentos = arrayListOf<Departamento>()
    var condominio: Condominio = Condominio()
    var posicionItemSeleccionado = 0
    var idDepartamentoSeleccionado = 0
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Departamento>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamentos_ver)
        // Recupera el ID
        val intent = intent
        val id = intent.getIntExtra("id", 1)
        // Buscar Departamentos
        condominio = CondominioDAO().getById(id)!!
        arregloDespartamentos = condominio.departamentos

        val nombreCondominio = findViewById<TextView>(R.id.tv_nombre_condominio)
        nombreCondominio.setText("${condominio.nombre}")

        listView = findViewById<ListView>(R.id.lv_departamento_ver)
        adaptador = ArrayAdapter(
            this, // Contexto
            android.R.layout.simple_list_item_1, // como se va a ver (XML)
            arregloDespartamentos
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonCrearDepartamento = findViewById<Button>(R.id.btn_crear_departamento)
        botonCrearDepartamento
            .setOnClickListener {
                crearDepartamento()
            }
        registerForContextMenu(listView)
    }

    fun crearDepartamento() {
        val departamento =
            Departamento(
                null,
                0,
                "Julian Tufiño",
                2,
                20.0,
                false,
                condominio
            )
        DepartamentoDAO().create(departamento)
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
        inflater.inflate(R.menu.menu_departamento, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        // Acceder al objeto Condominio en la posición seleccionada
        val departamentoSeleccionado = arregloDespartamentos.get(posicion)
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
        id: Int
    ) {
        val intent = Intent(this, clase)
        intent.putExtra("id", id);
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
            DepartamentoDAO().deleteById(idDepartamentoSeleccionado)
            mostrarSnackbar("Elemento id:${idDepartamentoSeleccionado} eliminado")
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
        adaptador.notifyDataSetChanged()
    }

}