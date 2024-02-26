package com.example.proyecto02

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto02.modelo.Genero
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

class RecyclerViewAdapterGeneros (
    private val contexto: Busqueda,
    private val lista: ArrayList<Genero>,
    private val recyclearView: RecyclerView
) : RecyclerView.Adapter <RecyclerViewAdapterGeneros.MyViewHolder>(){

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val btnGenero: Button

        init {
            btnGenero = view.findViewById(R.id.btn_genero)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_genero,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val generoActual = this.lista[position]
        holder.btnGenero.text = generoActual.nombre
        holder.btnGenero.setOnClickListener {
            contexto.consultarColeccion(generoActual)
            contexto.adaptadorPeliculas.notifyDataSetChanged()
        }
    }

}