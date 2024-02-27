package com.example.proyecto02.modelo

import java.io.Serializable
import java.time.LocalDate

class Pelicula(
    var id: String,
    var titulo: String,
    var calificacion: Double,
    var duracion: Int,
    var imagen: String,
    var resumen: String,
    var director: String,
    var fecha: LocalDate,
    var generos: Array<Genero?>,
    var elenco: Array<String>,
    var resenias: ArrayList<Resenia>
) :Serializable {

    override fun toString(): String {
        return "Pelicula(titulo='$titulo')"
    }

}