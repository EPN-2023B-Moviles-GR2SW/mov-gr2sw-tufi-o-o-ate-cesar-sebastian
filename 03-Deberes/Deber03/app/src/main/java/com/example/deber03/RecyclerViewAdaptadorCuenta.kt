package com.example.deber03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber03.modelo.Banco
import com.example.deber03.modelo.Cuenta

class RecyclerViewAdaptadorCuenta(
    private val contexto: RecyclerViewTransferencia,
    private val lista: ArrayList<Cuenta>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerViewAdaptadorCuenta.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView
        val bancoTextView: TextView
        val cuentaTextView: TextView
        val inicialTextView: TextView

        init {
            nombreTextView = view.findViewById(R.id.tv_nombre_cuenta)
            bancoTextView = view.findViewById(R.id.tv_banco_cuenta)
            cuentaTextView = view.findViewById(R.id.tv_cuenta)
            inicialTextView = view.findViewById(R.id.tv_inicial_cuenta)
        }
    }

    // Setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            Banco.PICHINCHA.id -> {
                val itemView = inflater.inflate(
                    R.layout.recycler_view_cuenta_pichincha,
                    parent,
                    false
                )
                MyViewHolder(itemView)
            }
            Banco.GUAYAQUIL.id -> {
                val itemView = inflater.inflate(
                    R.layout.recycler_view_cuenta_guayaquil,
                    parent,
                    false
                )
                MyViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Tipo de vista desconocido: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val banco = lista[position].banco
        return banco?.id ?: 0
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    // Setear los datos para la iteración
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val contactoActual = this.lista[position]
        holder.nombreTextView.text = contactoActual.nombre
        holder.bancoTextView.text = contactoActual.banco!!.nombre
        holder.cuentaTextView.text =
            contactoActual.tipoDeCuenta!!.nombre + " " + contactoActual.numeroDeCuenta
        holder.inicialTextView.text = contactoActual.inicial
    }
}