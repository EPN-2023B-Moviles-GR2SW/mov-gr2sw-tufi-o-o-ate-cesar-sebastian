package modelo.entidad

import java.time.LocalDate

class Condominio(
    private var id: Int?,
    private var nombre: String,
    private var direccion: String,
    private var fechaDeConstruccion: LocalDate,
    private var tienePiscina: Boolean = false,
    private var tieneCancha: Boolean = false
) {
    init {
        this.id
        this.nombre
        this.direccion
        this.fechaDeConstruccion
        this.tienePiscina
        this.tieneCancha
    }

    constructor() : this(null,"", "", LocalDate.now(), false, false)

    // Getters y Setters
    fun getId(): Int? {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getNombre(): String {
        return nombre
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun getDireccion(): String {
        return direccion
    }

    fun setDireccion(direccion: String) {
        this.direccion = direccion
    }

    fun getFechaDeConstruccion(): LocalDate {
        return fechaDeConstruccion
    }

    fun setFechaDeConstruccion(fechaDeConstruccion: LocalDate) {
        this.fechaDeConstruccion = fechaDeConstruccion
    }

    fun getTienePiscina(): Boolean {
        return tienePiscina
    }

    fun setTienePiscina(tienePiscina: Boolean) {
        this.tienePiscina = tienePiscina
    }

    fun getTieneCancha(): Boolean {
        return tieneCancha
    }

    fun setTieneCancha(tieneCancha: Boolean) {
        this.tieneCancha = tieneCancha
    }

}