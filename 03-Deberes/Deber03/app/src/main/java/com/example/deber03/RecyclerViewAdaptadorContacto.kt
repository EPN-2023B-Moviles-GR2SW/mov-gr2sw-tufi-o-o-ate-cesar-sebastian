package com.example.deber03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber03.modelo.Banco
import com.example.deber03.modelo.Contacto

class RecyclerViewAdaptadorContacto(
    private val contexto: RecyclerViewRecarga,
    private val lista: ArrayList<Contacto>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerViewAdaptadorContacto.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView
        val inicialTextView: TextView

        init {
            nombreTextView = view.findViewById(R.id.tv_nombre_contacto)
            inicialTextView = view.findViewById(R.id.tv_inicial_contacto)
        }
    }

    // Setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            0 -> {
                val itemView = inflater.inflate(
                    R.layout.recycler_view_contacto,
                    parent,
                    false
                )
                MyViewHolder(itemView)
            }
            1 -> {
                val itemView = inflater.inflate(
                    R.layout.recycler_view_contacto_propio,
                    parent,
                    false
                )
                MyViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Tipo de vista desconocido: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val esPropio = lista[position].esDelPropietario
        return if (esPropio!!) {
            1
        } else {
            0
        }
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    // Setear los datos para la iteración
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val contactoActual = this.lista[position]
        holder.nombreTextView.text = contactoActual.nombre
        holder.inicialTextView.text = contactoActual.inicial
    }
}