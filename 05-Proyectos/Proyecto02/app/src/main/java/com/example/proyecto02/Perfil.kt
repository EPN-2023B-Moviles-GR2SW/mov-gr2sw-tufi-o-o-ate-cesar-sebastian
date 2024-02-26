package com.example.proyecto02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class Perfil : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        mAuth = FirebaseAuth.getInstance()

        val nombre = findViewById<TextView>(R.id.tv_usuario_nombre)
        val correo = findViewById<TextView>(R.id.tv_usuario_correo)

        nombre.setText(mAuth.currentUser?.displayName.toString())
        correo.setText(mAuth.currentUser?.email.toString())

        var busqueda = findViewById<LinearLayout>(R.id.ly_busqueda)
        busqueda.setOnClickListener{
            irActividad(Busqueda::class.java)
            finish()
        }

        var inicio = findViewById<LinearLayout>(R.id.ly_inicio)
        inicio.setOnClickListener{
            irActividad(Inicio::class.java)
            finish()
        }

        val btnCerrarSesion = findViewById<Button>(R.id.btn_cerrar_sesion)
        btnCerrarSesion.setOnClickListener {
            mAuth.signOut()
            irActividad(MainActivity::class.java)
            finish()
        }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}