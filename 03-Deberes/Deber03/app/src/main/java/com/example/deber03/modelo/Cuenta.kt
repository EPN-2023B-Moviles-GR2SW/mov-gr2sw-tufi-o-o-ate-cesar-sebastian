package com.example.deber03.modelo

class Cuenta (
    var id: Int,
    var nombre: String?,
    var inicial: String?,
    var banco: Banco?,
    var tipoDeCuenta: TipoDeCuenta?,
    var numeroDeCuenta: String?,
) {
    override fun toString(): String {
        return "Cuenta(nombre=$nombre, banco=$banco, inicial=$inicial, tipoDeCuenta=$tipoDeCuenta, numeroDeCuenta=$numeroDeCuenta)"
    }
}