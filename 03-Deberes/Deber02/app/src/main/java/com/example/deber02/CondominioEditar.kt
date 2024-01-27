package com.example.deber02

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.deber02.sql.BaseDeDatos
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class CondominioEditar : AppCompatActivity() {
    lateinit var fechaDeConstruccion: EditText
    val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condominio_editar)
        // Recupera el ID
        val intent = intent
        val id = intent.getIntExtra("id", 1)
        // Buscar Condominio
        val condominio = BaseDeDatos.tablaCondominio!!.consultarCondominioPorID(id)

        // Setear el texto en componentes visuales
        val nombre = findViewById<EditText>(R.id.input_nombre)
        val direccion = findViewById<EditText>(R.id.input_direccion)
        fechaDeConstruccion = findViewById<EditText>(R.id.input_fecha)
        val tienePiscina = findViewById<Switch>(R.id.input_tiene_piscina)
        val tieneCancha = findViewById<Switch>(R.id.input_tiene_cancha)

        nombre.setText(condominio.nombre)
        direccion.setText(condominio.direccion)
        val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val fechaFormateada = condominio.fechaDeConstruccion.format(formatoFecha)
        fechaDeConstruccion.setText(fechaFormateada)
        tienePiscina.isChecked = condominio.tienePiscina
        tieneCancha.isChecked = condominio.tieneCancha

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_condominio)
        botonActualizar
            .setOnClickListener {
                condominio.nombre = nombre.text.toString()
                condominio.direccion = direccion.text.toString()
                val fechaTexto = fechaDeConstruccion.text.toString()
                condominio.fechaDeConstruccion = LocalDate.parse(fechaTexto, formatoFecha)
                condominio.tienePiscina = tienePiscina.isChecked
                condominio.tieneCancha = tieneCancha.isChecked
                BaseDeDatos.tablaCondominio!!.actualizarCondominio(condominio)
                mostrarSnackbar("Condominio Actualizado")
            }
        fechaDeConstruccion
            .setOnClickListener {
                mostrarDatePickerDialog()
            }
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

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_condominio_editar),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

}