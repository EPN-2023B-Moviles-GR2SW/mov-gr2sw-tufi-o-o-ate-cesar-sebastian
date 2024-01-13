package com.example.examenbi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.examenbi.dao.CondominioDAO
import com.example.examenbi.dao.DepartamentoDAO
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DepartamentoEditar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamento_editar)
        // Recupera el ID
        val intent = intent
        val id = intent.getIntExtra("id", 1)
        // Buscar Condominio
        val departamento = DepartamentoDAO().getById(id)!!

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

        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_departamento)
        botonActualizar
            .setOnClickListener {
                departamento.numero = numero.text.toString().toInt()
                departamento.inquilino = inquilino.text.toString()
                departamento.cantidadDeHabitaciones = cantidadDeHabitaciones.text.toString().toInt()
                departamento.area = area.text.toString().toDouble()
                departamento.tieneBalcon = tieneBalcon.isChecked
                DepartamentoDAO().update(departamento)
                mostrarSnackbar("Departamento Actualizado")
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