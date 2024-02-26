package com.example.examen02.entidad

import java.time.LocalDate

class Condominio(
    var id: String?,
    var nombre: String,
    var direccion: String,
    var fechaDeConstruccion: LocalDate,
    var tienePiscina: Boolean = false,
    var tieneCancha: Boolean = false
) {
    constructor() : this(null, "", "", LocalDate.now())

    override fun toString(): String {
        return "$nombre"
    }

}
