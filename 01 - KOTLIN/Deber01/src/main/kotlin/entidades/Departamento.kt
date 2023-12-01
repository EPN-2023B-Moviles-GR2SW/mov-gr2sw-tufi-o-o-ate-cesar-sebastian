package entidades

class Departamento(
    private var id: Int,
    private var numero: String,
    private var inquilino: String?,
    private var cantidadDeHabitaciones: Int,
    private var area: Double,
    private var tieneBalcon: Boolean = false
) {

    init {
        this.id
        this.numero
        this.cantidadDeHabitaciones
        this.area
        this.inquilino
        this.tieneBalcon
    }

    // Getters y Setters
    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getNumero(): String {
        return numero
    }

    fun setNumero(numero: String) {
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

    // Reglas de negocio
    fun getById(id: Int): Departamento? {
        var departamentoEncontrado: Departamento? = null
        getAll().forEach { departamento: Departamento ->
            if (departamento.id == id) departamentoEncontrado = departamento
        }
        return departamentoEncontrado;
    }

    fun getAll(): ArrayList<Departamento> {
        if (Departamento.listaDeDepartamentos.isEmpty()) {
            //TODO
        }
        return Departamento.listaDeDepartamentos;
    }

    fun create(departamento: Departamento) {
        getAll().add(departamento)
        // TODO
    }

    fun update(departamentoActualizado: Departamento) {
        val listaDeDepartamento = getAll()
        listaDeDepartamento.forEachIndexed { index, departamento ->
            if (departamento.id == departamentoActualizado.id) {
                listaDeDepartamento[index] = departamentoActualizado
                // TODO
                return
            }
        }
    }

    fun delete(departamentoEliminado: Departamento) {
        deleteById(departamentoEliminado.id)
        // TODO
    }

    fun deleteById(id: Int) {
        getAll().removeIf { departamento -> departamento.id == id }
        // TODO
    }

    companion object {
        private var listaDeDepartamentos: ArrayList<Departamento> = arrayListOf<Departamento>()
    }

}
