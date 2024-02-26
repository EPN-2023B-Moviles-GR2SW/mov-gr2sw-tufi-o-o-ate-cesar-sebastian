package com.example.proyecto02.modelo

enum class Genero(
    val nombre: String,
    val id: Int,
) {
    ACCION("Acción", 1),
    TERROR("Terror", 2),
    AVENTURAS("Aventuras", 3),
    COMEDIA("Comedia", 4),
    DRAMA("Drama", 5);

    companion object {
        fun obtenerPorId(id: Int): Genero? {
            return values().find { it.id == id }
        }
    }

    override fun toString(): String {
        return nombre
    }


}