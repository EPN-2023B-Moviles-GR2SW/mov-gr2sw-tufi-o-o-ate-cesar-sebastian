package modelo.entidad

class Departamento(
    private var id: Int?,
    private var numero: Int,
    private var inquilino: String?,
    private var cantidadDeHabitaciones: Int,
    private var area: Double,
    private var tieneBalcon: Boolean = false,
    private var condominio: Condominio
) {
    init {
        this.id
        this.numero
        this.cantidadDeHabitaciones
        this.area
        this.inquilino
        this.tieneBalcon
        this.condominio
    }

    constructor() : this(null,0, "", 0, 0.0, false, Condominio())

    // Getters y Setters
    fun getId(): Int? {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getNumero(): Int {
        return numero
    }

    fun setNumero(numero: Int) {
        this.numero = numero
    }

    fun getInquilino(): String? {
        return inquilino
    }

    fun setInquilino(inquilino: String) {
        this.inquilino = inquilino
    }

    fun getCantidadDeHabitaciones(): Int {
        return cantidadDeHabitaciones
    }

    fun setCantidadDeHabitaciones(cantidadDeHabitaciones: Int) {
        this.cantidadDeHabitaciones = cantidadDeHabitaciones
    }

    fun getArea(): Double {
        return area
    }

    fun setArea(area: Double) {
        this.area = area
    }

    fun getTieneBalcon(): Boolean {
        return tieneBalcon
    }

    fun setTieneBalcon(tieneBalcon: Boolean) {
        this.tieneBalcon = tieneBalcon
    }

    fun getCondominio(): Condominio {
        return condominio
    }

    fun setCondominio(condominio: Condominio) {
        this.condominio = condominio
    }

}
