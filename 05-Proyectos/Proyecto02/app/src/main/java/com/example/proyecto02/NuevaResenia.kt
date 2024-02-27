package com.example.proyecto02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.proyecto02.modelo.Pelicula
import com.example.proyecto02.modelo.Resenia
import com.example.proyecto02.modelo.Usuario
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import java.time.ZoneId
import java.util.Date

class NuevaResenia : AppCompatActivity() {
    private lateinit var rbResenia: RatingBar
    private lateinit var etComentario: EditText
    private lateinit var btnGuardarResenia: Button

    private var pelicula: Pelicula? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_resenia)

        pelicula = intent.getSerializableExtra("pelicula") as? Pelicula

        if (pelicula != null) {
            findViewById<TextView>(R.id.tv_titulo_informacion).text = pelicula!!.titulo
            findViewById<TextView>(R.id.tv_calificacion_informacion).text = "${pelicula!!.calificacion}/5.0"
            findViewById<TextView>(R.id.tv_duracion_informacion).text = "${pelicula!!.duracion} min"

            val storage = FirebaseStorage.getInstance()
            val storageReference = storage.reference
            val imageReference = storageReference.child(pelicula!!.imagen)

            imageReference.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                Glide.with(this)
                    .load(imageUrl)
                    .into(findViewById(R.id.iv_pelicula_calificacion))
            }.addOnFailureListener { exception ->
            }
        }

        rbResenia = findViewById(R.id.rb_resenia)
        etComentario = findViewById(R.id.et_comentario)
        btnGuardarResenia = findViewById(R.id.btn_agregar_resenia)

        btnGuardarResenia.setOnClickListener {
            guardarResenia()
        }


        var perfil = findViewById<LinearLayout>(R.id.ly_perfil)
        perfil.setOnClickListener{
            irActividad(Perfil::class.java)
            finish()
        }

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

        val toolbar = findViewById<MaterialToolbar>(R.id.mtb_nueva_reseña)
        toolbar.setNavigationOnClickListener {
            irActividadConPelicula(ReseniaActivity::class.java, pelicula!!)
            finish()
        }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun irActividadConPelicula(
        clase: Class<*>,
        pelicula: Pelicula
    ){
        val intent = Intent(this, clase)
        intent.putExtra("pelicula", pelicula)
        startActivity(intent)
    }

    private fun guardarResenia() {
        val calificacion = rbResenia.rating.toDouble()
        val comentario = etComentario.text.toString()
        var mAuth = FirebaseAuth.getInstance()

        if (pelicula != null) {
            val date = Timestamp.now()
            val nuevaResenia = Resenia(
                usuario = Usuario(mAuth.currentUser?.displayName.toString()), // Aquí deberías obtener el usuario real
                fecha = date.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                comentrario = comentario,
                calificacion = calificacion
            )

            val db = Firebase.firestore
            val peliculaRef = db.collection("movie").document(pelicula!!.id)
            val reseniaRef = peliculaRef.collection("review")

            val datosResenia = hashMapOf(
                "user" to nuevaResenia.usuario.nombre,
                "date" to date,
                "comment" to nuevaResenia.comentrario,
                "score" to nuevaResenia.calificacion,
            )
            val identificador = Date().time
            reseniaRef // (crear/actualizar)
                .document(identificador.toString())
                .set(datosResenia)
                .addOnSuccessListener {
                    peliculaRef.get()
                        .addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot.exists()) {
                                val scoreActual = (documentSnapshot.data!!.get("score") as? Number)?.toDouble() ?: 0.0
                                val cantidadDeResenias = pelicula!!.resenias.size
                                val nuevoScore = ((scoreActual * cantidadDeResenias) + nuevaResenia.calificacion) / (cantidadDeResenias + 1)

                                val scoreFormateado = String.format("%.2f", nuevoScore).toDouble()

                                peliculaRef.update("score", scoreFormateado)
                                pelicula!!.resenias.add(nuevaResenia)
                                pelicula!!.calificacion = scoreFormateado
                                irActividadConPelicula(ReseniaActivity::class.java, pelicula!!)
                                finish()
                            }
                        }
                        .addOnFailureListener { }
                }
                .addOnFailureListener { }
        }
    }

}