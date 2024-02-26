package com.example.proyecto02

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto02.modelo.Pelicula
import com.google.firebase.storage.FirebaseStorage
import java.util.ArrayList

class RecyclerViewAdapterPeliculas (
    private val contexto: Context,
    private val lista: ArrayList<Pelicula>,
    private val recyclearView: RecyclerView
): RecyclerView.Adapter <RecyclerViewAdapterPeliculas.MyViewHolder>(){
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imageView: ImageView
        val tituloTextView: TextView
        val calificacionTextView: TextView
        val duracionTextView: TextView
        val informacionButton: ImageButton

        init {
            imageView = view.findViewById(R.id.iv_pelicula)
            tituloTextView = view.findViewById(R.id.tv_titulo)
            calificacionTextView = view.findViewById(R.id.tv_calificacion)
            duracionTextView = view.findViewById(R.id.tv_duracion)
            informacionButton = view.findViewById(R.id.ib_informacion)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_pelicula,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val peliculaActual = this.lista[position]
        val storage = FirebaseStorage.getInstance()
        val storageReference = storage.reference

        val imageReference = storageReference.child(peliculaActual.imagen)

        imageReference.downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()
            Glide.with(contexto)
                .load(imageUrl)
                .into(holder.imageView)
        }.addOnFailureListener { exception ->
        }

        holder.tituloTextView.text = peliculaActual.titulo
        holder.calificacionTextView.text = "${peliculaActual.calificacion}/5"
        holder.duracionTextView.text = "${peliculaActual.duracion} min"

        holder.informacionButton.setOnClickListener{
            irActividad(DetallePelicula::class.java, peliculaActual)
        }
    }

    fun irActividad(
        clase: Class<*>,
        pelicula: Pelicula
    ){
        val intent = Intent(contexto, clase)
        intent.putExtra("pelicula", pelicula)
        contexto.startActivity(intent)
    }

}