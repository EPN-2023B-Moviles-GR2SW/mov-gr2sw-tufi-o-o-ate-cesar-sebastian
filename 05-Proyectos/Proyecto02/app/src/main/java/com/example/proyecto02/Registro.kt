package com.example.proyecto02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Locale

class Registro : AppCompatActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        mAuth = FirebaseAuth.getInstance()

        editTextUsername = findViewById(R.id.pt_usuario_registro)
        editTextEmail = findViewById(R.id.pt_correo_registro)
        editTextPassword = findViewById(R.id.pt_contrasenia_registro)
        buttonRegister = findViewById(R.id.btn_registrarme)

        buttonRegister.setOnClickListener {
            checkUsernameAvailability()
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.mtb_registro)
        toolbar.setNavigationOnClickListener {
            finish()
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
            findViewById(R.id.id_ly_registro),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    private fun checkUsernameAvailability() {
        val username = editTextUsername.text.toString().trim()
        val db = Firebase.firestore
        val ref = db.collection("user")

        ref.document(username).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Usuario con el mismo nombre ya existe
                    mostrarSnackbar("¡El nombre de usuario ya está en uso!")
                } else {
                    // Usuario no existe, proceder con el registro
                    registerUser(username)
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores en la consulta
                Toast.makeText(this, "Error al verificar la disponibilidad del nombre de usuario: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    private fun registerUser(username: String) {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (password.length<6){
            mostrarSnackbar("La contraseña debe tener al menos 6 caracteres")
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registro exitoso
                    val user = mAuth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { /* Manejar la actualización del perfil */ }
                    crearUsuario()
                    irActividad(Inicio::class.java)
                } else {
                    // Si el registro falla, muestra un mensaje al usuario
                    Toast.makeText(
                        this,
                        "Error: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun crearUsuario() {
        val username = editTextUsername.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val db = Firebase.firestore
        val ref = db.collection("user")
        val datos = hashMapOf(
            "email" to email,
            "username" to username
        )
        val identificador = username
        ref
            .document(identificador)
            .set(datos)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

}