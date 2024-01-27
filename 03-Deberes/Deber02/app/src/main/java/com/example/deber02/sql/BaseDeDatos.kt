package com.example.deber02.sql

import android.content.Context
import java.time.LocalDate

class BaseDeDatos {
    companion object {
        var tablaCondominio: SQLiteHelperCondominio? = null
            private set
        var tablaDepartamento: SQLiteHelperDepartamento? = null
            private set

        fun inicializar(contexto: Context) {
            tablaCondominio = SQLiteHelperCondominio(contexto)
            tablaDepartamento = SQLiteHelperDepartamento(contexto)
        }

        fun cerrarConexiones() {
            tablaCondominio?.close()
            tablaDepartamento?.close()
        }
    }

}