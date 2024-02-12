package com.example.deber03.modelo

class Contacto (
    var id: Int,
    var nombre: String?,
    var inicial: String?,
    var esDelPropietario: Boolean?
) {
    override fun toString(): String {
        return "Contacto(nombre=$nombre, inicial=$inicial)"
    }
}