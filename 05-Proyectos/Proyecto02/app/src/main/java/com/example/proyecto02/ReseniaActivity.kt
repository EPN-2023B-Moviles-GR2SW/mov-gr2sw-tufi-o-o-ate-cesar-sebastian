package com.example.proyecto02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto02.modelo.Pelicula
import com.example.proyecto02.modelo.Resenia
import com.example.proyecto02.modelo.Usuario
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReseniaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resenia)

        val pelicula: Pelicula? = intent.getSerializableExtra("pelicula") as? Pelicula

        if (pelicula != null) {
            findViewById<TextView>(R.id.tv_titulo_informacion).text = pelicula.titulo
            findViewById<TextView>(R.id.tv_calificacion_informacion).text = "${pelicula.calificacion}/5"
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
        }

        var perfil = findViewById<LinearLayout>(R.id.ly_perfil)
        perfil.setOnClickListener{
            irActividad(Perfil::class.java)
        }

        var busqueda = findViewById<LinearLayout>(R.id.ly_busqueda)
        busqueda.setOnClickListener{
            irActividad(Busqueda::class.java)
        }

        var inicio = findViewById<LinearLayout>(R.id.ly_inicio)
        inicio.setOnClickListener{
            irActividad(Inicio::class.java)
        }

        val agregarReseñia = findViewById<Button>(R.id.btn_agregar_reseña)
        agregarReseñia.setOnClickListener(){
            irActividad(NuevaResenia::class.java)
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.mtb_reseña)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        var arregloResenias = arrayListOf<Resenia>()
        arregloResenias.add(
            Resenia(
                Usuario("Juan Perez","jp@gmail.com"),
                LocalDate.now(),
                "La película de cautiva con su trama intrigante y efectos" +
                        " visuales impresionantes Aunque algunos puntos podrían mejorarse " +
                        "en general es una experiencia emocionante y recomendable.",
                4.0)
        )
        arregloResenias.add(
            Resenia(
                Usuario("Juan Perez","jp@gmail.com"),
                LocalDate.now(),
                "La película de cautiva con su trama intrigante y efectos" +
                        " visuales impresionantes Aunque algunos puntos podrían mejorarse " +
                        "en general es una experiencia emocionante y recomendable.",
                4.0)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.rv_reseñas)
        val adaptador = RecyclerViewAdapterResenias(
            this,
            arregloResenias,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget
            .DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget
            .LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()

    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}