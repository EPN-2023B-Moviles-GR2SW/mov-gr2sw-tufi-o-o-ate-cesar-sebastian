package com.example.proyecto02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance()

        // Verificar si ya hay una sesión activa
        if (mAuth.currentUser != null) {
            irActividad(Inicio::class.java)
            finish()
            return
        }

        editTextUsername = findViewById(R.id.pt_usuario)
        editTextPassword = findViewById(R.id.pt_contrasenia)

        val registrarme = findViewById<TextView>(R.id.tv_registrarme)
        registrarme.setOnClickListener {
            irActividad(Registro::class.java)
        }

        val inicio = findViewById<Button>(R.id.btn_iniciar_sesion)
        inicio.setOnClickListener {
            loginUser()
        }
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_ly_main),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun loginUser() {
        val username = editTextUsername.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        val db = Firebase.firestore
        val ref = db.collection("user").document(username)

        ref.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val email = document.getString("email")
                    if (email != null) {
                        mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    irActividad(Inicio::class.java)
                                    finish()
                                } else {
                                    //Toast.makeText(this,"Error: ${task.exception?.message}",Toast.LENGTH_SHORT).show()
                                    mostrarSnackbar("Usuario o contraseña incorrectos")
                                }
                            }
                    } else {
                        mostrarSnackbar("Usuario o contraseña incorrectos")
                    }

                } else {
                    Toast.makeText(
                        this,
                        "No se encontró el usuario",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Error al obtener el correo: $exception",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}