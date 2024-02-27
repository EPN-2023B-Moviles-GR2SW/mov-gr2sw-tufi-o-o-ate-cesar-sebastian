package com.example.proyecto02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto02.modelo.Pelicula
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class ReseniaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resenia)

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
                    .into(findViewById(R.id.iv_pelicula_reseñas))
            }.addOnFailureListener { exception ->
            }
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

        val agregarResenia = findViewById<Button>(R.id.btn_agregar_resenia)
        agregarResenia.setOnClickListener(){
            irActividadConPelicula(NuevaResenia::class.java, pelicula!!)
            finish()
        }

        val toolbar = findViewById<MaterialToolbar>(R.id.mtb_reseña)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rv_reseñas)
        val adaptador = RecyclerViewAdapterResenias(
            this,
            pelicula!!.resenias,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget
            .DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget
            .LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()

        var mAuth = FirebaseAuth.getInstance()

        if (pelicula!!.resenias.any { it.usuario.nombre == mAuth.currentUser?.displayName.toString() }) {
            agregarResenia.visibility = View.GONE
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