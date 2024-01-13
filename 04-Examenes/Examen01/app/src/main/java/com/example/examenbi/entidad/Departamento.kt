package com.example.examenbi.entidad

import com.example.examenbi.entidad.Condominio

class Departamento(
    var id: Int?,
    var numero: Int,
    var inquilino: String?,
    var cantidadDeHabitaciones: Int,
    var area: Double,
    var tieneBalcon: Boolean = false,
    var condominio: Condominio = Condominio()
) {

    constructor() : this(null, 0, "", 0, 0.0)

    override fun toString(): String {
        return "Departamento Número: $numero"
    }

}
