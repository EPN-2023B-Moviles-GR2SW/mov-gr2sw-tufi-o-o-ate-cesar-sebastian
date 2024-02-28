package com.example.proyecto02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto02.modelo.Genero
import com.example.proyecto02.modelo.Pelicula
import com.example.proyecto02.modelo.Resenia
import com.example.proyecto02.modelo.Usuario
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.ZoneId
import java.util.Locale

class Inicio : AppCompatActivity() {
    var arregloPeliculas = arrayListOf<Pelicula>()
    var query: Query? = null
    lateinit var adaptador: RecyclerViewAdapterPeliculas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        var perfil = findViewById<LinearLayout>(R.id.ly_perfil)
        perfil.setOnClickListener{
            irActividad(Perfil::class.java)
        }

        var busqueda = findViewById<LinearLayout>(R.id.ly_busqueda)
        busqueda.setOnClickListener{
            irActividad(Busqueda::class.java)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rv_peliculas_catalogo)
        adaptador = RecyclerViewAdapterPeliculas(
            this,
            arregloPeliculas,
            recyclerView
        )
        //consultarColeccion()
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

    fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_ly_inicio),
            texto, Snackbar.LENGTH_LONG
        )
        snack.show()
    }

    fun consultarColeccion() {
        val db = Firebase.firestore
        val ref = db.collection("movie")
        var tarea: Task<QuerySnapshot>? = null
        tarea = ref.get() // 1era vez
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        if (tarea != null) {
            tarea
                .addOnSuccessListener { documentSnapshots ->
                    guardarQuery(documentSnapshots, ref)
                    for (pelicula in documentSnapshots) {
                        anadirAArreglo(pelicula)
                    }
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener { }
        }
    }

    fun limpiarArreglo() {
        arregloPeliculas.clear()
    }

    fun guardarQuery(
        documentSnapshots: QuerySnapshot,
        ref: Query
    ) {
        if (documentSnapshots.size() > 0) {
            val ultimoDocumento = documentSnapshots
                .documents[documentSnapshots.size() - 1]
            query = ref
                // Start After nos ayuda a paginar
                .startAfter(ultimoDocumento)
        }
    }

    fun anadirAArreglo(
        document: QueryDocumentSnapshot
    ) {
        val pelicula = Pelicula(
            document.id,
            document.data.get("title") as String,
            (document.data.get("score") as? Number)?.toDouble() ?: 0.0,
            (document.data.get("duration") as Long).toInt(),
            document.data.get("image") as String,
            document.data.get("synopsis") as String,
            document.data.get("director") as String,
            (document.data.get("date") as Timestamp).toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            (document.data.get("genres") as ArrayList<Long>).map { Genero.obtenerPorId(it.toInt()) }.toTypedArray(),
            (document.data.get("cast") as ArrayList<String>).toTypedArray(),
            ArrayList()
        )
        val reviewCollection = document.reference.collection("review")

        reviewCollection.get()
            .addOnSuccessListener { reviewsSnapshot ->
                val listaResenias = reviewsSnapshot.documents.map { resenia ->
                    Resenia(
                        Usuario(resenia.get("user") as String),
                        (resenia.get("date") as Timestamp).toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        resenia.get("comment") as String,
                        (resenia.get("score") as? Number)?.toDouble() ?: 0.0
                    )
                }
                pelicula.resenias = ArrayList(listaResenias)

                arregloPeliculas.add(pelicula)

                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
            }
    }

    override fun onResume() {
        super.onResume()
        consultarColeccion()
        adaptador.notifyDataSetChanged()
    }

}