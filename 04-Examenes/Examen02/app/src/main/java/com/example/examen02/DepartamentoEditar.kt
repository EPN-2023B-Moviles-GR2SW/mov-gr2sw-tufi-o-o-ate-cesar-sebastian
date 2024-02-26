package com.example.examen02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.examen02.entidad.Condominio
import com.example.examen02.entidad.Departamento
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class DepartamentoEditar : AppCompatActivity() {
    var departamento = Departamento()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamento_editar)
        // Recupera el ID
        val intent = intent
        val idCondominio = intent.getStringExtra("idCondominio")
        val idDepartamento = intent.getStringExtra("idDepartamento")
        // Buscar Condominio
        mostrarSnackbar(idDepartamento!!)
        consultarDocumento(idCondominio!!, idDepartamento!!)

        // Setear el texto en componentes visuales
        val numero = findViewById<EditText>(R.id.input_numero)
        val inquilino = findViewById<EditText>(R.id.input_inquilino)
        val cantidadDeHabitaciones = findViewById<EditText>(R.id.input_cantidad_habitaciones)
        val area = findViewById<EditText>(R.id.input_area)
        val tieneBalcon = findViewById<Switch>(R.id.input_tiene_balcon)

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_departamento)
        botonActualizar
            .setOnClickListener {
                departamento.numero = numero.text.toString().toInt()
                departamento.inquilino = inquilino.text.toString()
                departamento.cantidadDeHabitaciones = cantidadDeHabitaciones.text.toString().toInt()
                departamento.area = area.text.toString().toDouble()
                departamento.tieneBalcon = tieneBalcon.isChecked
                actualizarDepartamento(departamento, idCondominio!!)
            }
    }

    fun consultarDocumento(idCondominio: String, idDepartamento: String) {
        val db = Firebase.firestore
        val departamentosRef = db.collection("condominio/${idCondominio}/departamentos")

        departamentosRef
            .document(idDepartamento)
            .get()
            .addOnSuccessListener {
                departamento = Departamento(
                    it.id as String?,
                    (it.data?.get("numero") as Long).toInt(),
                    it.data?.get("inquilino") as String,
                    (it.data?.get("cantidadDeHabitaciones") as Long).toInt(),
                    it.data?.get("area") as Double,
                    it.data?.get("tieneBalcon") as Boolean
                )
                notificarActualizacionDepartamento()
            }
            .addOnFailureListener {
                // salio Mal
            }
    }

    fun notificarActualizacionDepartamento() {
        // Setear el texto en componentes visuales
        val numero = findViewById<EditText>(R.id.input_numero)
        val inquilino = findViewById<EditText>(R.id.input_inquilino)
        val cantidadDeHabitaciones = findViewById<EditText>(R.id.input_cantidad_habitaciones)
        val area = findViewById<EditText>(R.id.input_area)
        val tieneBalcon = findViewById<Switch>(R.id.input_tiene_balcon)
        numero.setText(departamento.numero.toString())
        inquilino.setText(departamento.inquilino)
        cantidadDeHabitaciones.setText(departamento.cantidadDeHabitaciones.toString())
        area.setText(departamento.area.toString())
        tieneBalcon.isChecked = departamento.tieneBalcon
    }

    fun actualizarDepartamento(departamento: Departamento, idCondominio: String) {
        val db = Firebase.firestore
        val departamentosRef = db.collection("condominio/${idCondominio}/departamentos")

        // Crear un mapa con los nuevos datos del condominio
        val datosCondominio = hashMapOf(
            "numero" to departamento.numero.toLong(),
            "inquilino" to departamento.inquilino,
            "cantidadDeHabitaciones" to departamento.cantidadDeHabitaciones.toLong(),
            "area" to departamento.area,
            "tieneBalcon" to departamento.tieneBalcon,
        )
        // Actualizar el documento en Firestore
        departamentosRef
            .document(departamento.id!!)
            .update(datosCondominio as Map<String, Any>)
            .addOnSuccessListener {
                mostrarSnackbar("Departamento Actualizado")
            }
            .addOnFailureListener {
                mostrarSnackbar("Error al actualizar el departamento")
            }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_departamento_editar),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }
}