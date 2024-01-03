package com.example.examenbi

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.example.examenbi.dao.CondominioDAO

class CondominioVer : AppCompatActivity() {
    val arreglo = CondominioDAO().getAll()
    var posicionItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condominio_ver)
        val listView = findViewById<ListView>(R.id.lv_condominio_ver)
        //val adaptador = ArrayAdapter(
          //  this, // Contexto
          //  android.R.layout.simple_list_item_1, // como se va a ver (XML)
            //arreglo
        //)

        //listView.adapter = adaptador
        //adaptador.notifyDataSetChanged()


    }


}