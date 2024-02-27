package com.example.proyecto02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.proyecto02.modelo.Pelicula
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.storage.FirebaseStorage
import java.time.format.DateTimeFormatter

class DetallePelicula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pelicula)

        val pelicula: Pelicula? = intent.getSerializableExtra("pelicula") as? Pelicula

        if (pelicula != null) {
            findViewById<TextView>(R.id.tv_titulo_informacion).text = pelicula.titulo
            findViewById<TextView>(R.id.tv_calificacion_informacion).text = "${pelicula.calificacion}/5.0"
            findViewById<TextView>(R.id.tv_duracion_informacion).text = "${pelicula.duracion} min"

            val storage = FirebaseStorage.getInstance()
            val storageReference = storage.reference
            val imageReference = storageReference.child(pelicula.imagen)

            imageReference.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                Glide.with(this)
                    .load(imageUrl)
                    .into(findViewById(R.id.im_pelicula_informacion))
            }.addOnFailureListener { exception ->
            }
            findViewById<TextView>(R.id.tv_nombre_generos).text = pelicula.generos.joinToString("\n") { it?.nombre.orEmpty() }
            findViewById<TextView>(R.id.tv_nombre_directores).text = pelicula.director
            findViewById<TextView>(R.id.tv_nombre_actores).text = pelicula.elenco.joinToString("\n")
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val fechaFormateada = pelicula.fecha.format(formatter)
            findViewById<TextView>(R.id.tv_fecha_estreno).text = fechaFormateada
            findViewById<TextView>(R.id.tv_texto_resumen).text = pelicula.resumen
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

        var reseñas = findViewById<Button>(R.id.btn_reseñas)
        reseñas.setOnClickListener{
            if (pelicula != null) {
                irActividadConPelicula(ReseniaActivity::class.java, pelicula)
                finish()
            }
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.mtb_informacion)
        toolbar.setNavigationOnClickListener {
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
}