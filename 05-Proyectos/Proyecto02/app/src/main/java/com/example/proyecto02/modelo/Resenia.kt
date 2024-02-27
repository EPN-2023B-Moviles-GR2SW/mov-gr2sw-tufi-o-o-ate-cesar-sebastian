package com.example.proyecto02.modelo

import java.io.Serializable
import java.time.LocalDate

class Resenia (
    val usuario: Usuario,
    val fecha: LocalDate,
    val comentrario: String,
    val calificacion: Double
): Serializable {
    override fun toString(): String {
        return "Reseña(usuario='$usuario', fecha='$fecha', comentrario='$comentrario')"
    }
}