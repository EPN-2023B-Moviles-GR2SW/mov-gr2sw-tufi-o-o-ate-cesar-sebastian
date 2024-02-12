package com.example.deber03.datos

import com.example.deber03.modelo.Banco
import com.example.deber03.modelo.Contacto
import com.example.deber03.modelo.Cuenta
import com.example.deber03.modelo.TipoDeCuenta

class BaseDeDatos {

    companion object {
        val arregloCuentas = arrayListOf<Cuenta>()
        val arregloContactos = arrayListOf<Contacto>()

        init {
            arregloCuentas
                .add(
                    Cuenta(1, "Madre", "M", Banco.PICHINCHA, TipoDeCuenta.AHORROS, "2261465041")
                )
            arregloCuentas
                .add(
                    Cuenta(2, "Madre G", "MG", Banco.GUAYAQUIL, TipoDeCuenta.AHORROS, "0033601666")
                )
            arregloCuentas
                .add(
                    Cuenta(3, "Mateo Perez", "MP", Banco.PICHINCHA, TipoDeCuenta.AHORROS, "2261465041")
                )
            arregloCuentas
                .add(
                    Cuenta(4, "Padre", "P", Banco.PICHINCHA, TipoDeCuenta.AHORROS, "2261465041")
                )
            arregloCuentas
                .add(
                    Cuenta(5, "Papag", "P", Banco.GUAYAQUIL, TipoDeCuenta.AHORROS, "0033601666")
                )
        }

        init {
            arregloContactos
                .add(
                    Contacto(1, "César", "C", true)
                )
            arregloContactos
                .add(
                    Contacto(2, "Anahí", "A", false)
                )
            arregloContactos
                .add(
                    Contacto(3, "Ab Anibal", "AA", false)
                )
            arregloContactos
                .add(
                    Contacto(4, "Ab Rosa", "AR", false)
                )
            arregloContactos
                .add(
                    Contacto(5, "Adriana", "AD", false)
                )
            arregloContactos
                .add(
                    Contacto(6, "Aidee Proaño", "AP", false)
                )
            arregloContactos
                .add(
                    Contacto(7, "Andrés Tufiño", "AT", false)
                )
        }
    }
}