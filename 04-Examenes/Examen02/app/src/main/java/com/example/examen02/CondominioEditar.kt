package com.example.examen02

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.examen02.entidad.Condominio
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CondominioEditar : AppCompatActivity() {
    lateinit var fechaDeConstruccion: EditText
    val calendar = Calendar.getInstance()
    var condominio = Condominio()
    val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condominio_editar)
        // Recupera el ID
        val intent = intent
        val id = intent.getStringExtra("id")

        // Buscar Condominio
        mostrarSnackbar(id!!)
        consultarDocumento(id!!)

        // Setear el texto en componentes visuales
        val nombre = findViewById<EditText>(R.id.input_nombre)
        val direccion = findViewById<EditText>(R.id.input_direccion)
        fechaDeConstruccion = findViewById<EditText>(R.id.input_fecha)
        val tienePiscina = findViewById<Switch>(R.id.input_tiene_piscina)
        val tieneCancha = findViewById<Switch>(R.id.input_tiene_cancha)

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_condominio)
        botonActualizar
            .setOnClickListener {
                condominio.nombre = nombre.text.toString()
                condominio.direccion = direccion.text.toString()
                val fechaTexto = fechaDeConstruccion.text.toString()
                condominio.fechaDeConstruccion = LocalDate.parse(fechaTexto, formatoFecha)
                condominio.tienePiscina = tienePiscina.isChecked
                condominio.tieneCancha = tieneCancha.isChecked
                actualizarCondominio(condominio)
            }
        fechaDeConstruccion
            .setOnClickListener {
                mostrarDatePickerDialog()
            }

    }

    fun consultarDocumento(id: String) {
        val db = Firebase.firestore
        val condominiosRef = db.collection("condominio")

        condominiosRef
            .document(id)
            .get()
            .addOnSuccessListener {
                condominio = Condominio(
                    it.id as String?,
                    it.data?.get("nombre") as String,
                    it.data?.get("direccion") as String,
                    (it.data?.get("fechaDeConstruccion") as Timestamp).toDate().toInstant().atZone(
                        ZoneId.systemDefault()
                    ).toLocalDate(),
                    it.data?.get("tienePiscina") as Boolean,
                    it.data?.get("tieneCancha") as Boolean
                )
                notificarActualizacionCondiminio()
            }
            .addOnFailureListener {
                // salio Mal
            }
    }

    fun notificarActualizacionCondiminio() {
        // Setear el texto en componentes visuales
        val nombre = findViewById<EditText>(R.id.input_nombre)
        val direccion = findViewById<EditText>(R.id.input_direccion)
        fechaDeConstruccion = findViewById<EditText>(R.id.input_fecha)
        val tienePiscina = findViewById<Switch>(R.id.input_tiene_piscina)
        val tieneCancha = findViewById<Switch>(R.id.input_tiene_cancha)

        nombre.setText(condominio.nombre)
        direccion.setText(condominio.direccion)
        val fechaFormateada = condominio.fechaDeConstruccion.format(formatoFecha)
        fechaDeConstruccion.setText(fechaFormateada)
        tienePiscina.isChecked = condominio.tienePiscina
        tieneCancha.isChecked = condominio.tieneCancha
    }

    fun mostrarDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this, { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val fechaSeleccionada = Calendar.getInstance()
                fechaSeleccionada.set(year, monthOfYear, dayOfMonth)
                val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val fechaFormateada = formatoFecha.format(fechaSeleccionada.time)
                fechaDeConstruccion.setText(fechaFormateada)

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    fun actualizarCondominio(condominio: Condominio) {
        val db = Firebase.firestore
        val condominioRef = db.collection("condominio")

        val fechaTimestamp = Timestamp(
            Date.from(
                condominio.fechaDeConstruccion.atStartOfDay(ZoneId.systemDefault()).toInstant()
            )
        )
        // Crear un mapa con los nuevos datos del condominio
        val datosCondominio = hashMapOf(
            "nombre" to condominio.nombre,
            "direccion" to condominio.direccion,
            "fechaDeConstruccion" to fechaTimestamp,
            "tienePiscina" to condominio.tienePiscina,
            "tieneCancha" to condominio.tieneCancha,
        )
        // Actualizar el documento en Firestore
        condominioRef
            .document(condominio.id!!)
            .update(datosCondominio as Map<String, Any>)
            .addOnSuccessListener {
                mostrarSnackbar("Condominio Actualizado")
            }
            .addOnFailureListener {
                mostrarSnackbar("Error al actualizar el condominio")
            }
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_condominio_editar),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

}