package com.example.deber03

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonTransferencia = findViewById<Button>(R.id.btn_transferencia)
        botonTransferencia
            .setOnClickListener{
                irActividad(RecyclerViewTransferencia::class.java)
            }
        val botonRecarga = findViewById<Button>(R.id.btn_recarga)
        botonRecarga
            .setOnClickListener{
                irActividad(RecyclerViewRecarga::class.java)
            }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}
