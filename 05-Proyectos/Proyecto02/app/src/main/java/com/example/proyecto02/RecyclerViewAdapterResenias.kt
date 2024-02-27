package com.example.proyecto02

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto02.modelo.Resenia
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class RecyclerViewAdapterResenias(
    private val contexto: ReseniaActivity,
    private val lista: ArrayList<Resenia>,
    private val recyclearView: RecyclerView
) : RecyclerView.Adapter<RecyclerViewAdapterResenias.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val usuarioTextView: TextView
        val fechaTextView: TextView
        val comentarioTextView: TextView
        val calificacion: RatingBar

        init {
            usuarioTextView = view.findViewById(R.id.tv_nombre_comentario)
            fechaTextView = view.findViewById(R.id.tv_fecha_comentario)
            comentarioTextView = view.findViewById(R.id.tv_comentario)
            calificacion = view.findViewById(R.id.rb_reseña)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_resenia,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val reseniaActual = this.lista[position]
        holder.usuarioTextView.text = reseniaActual.usuario.nombre
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        holder.fechaTextView.text = reseniaActual.fecha.format(formatter)
        holder.comentarioTextView.text = reseniaActual.comentrario
        val calificacionNormalizada = reseniaActual.calificacion.toFloat().coerceIn(0f, 5f)
        holder.calificacion.rating = calificacionNormalizada
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(contexto, clase)
        contexto.startActivity(intent)
    }

}