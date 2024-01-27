package com.example.deber02.entidad

class Departamento(
    var id: Int?,
    var numero: Int,
    var inquilino: String?,
    var cantidadDeHabitaciones: Int,
    var area: Double,
    var tieneBalcon: Boolean = false
) {

    constructor() : this(null, 0, "", 0, 0.0)

    override fun toString(): String {
        return "Departamento Número: $numero"
    }

}
