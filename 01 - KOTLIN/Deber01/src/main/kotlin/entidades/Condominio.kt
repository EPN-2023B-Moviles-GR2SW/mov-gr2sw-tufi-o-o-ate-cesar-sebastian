package entidades

import java.util.*
import kotlin.collections.ArrayList

class Condominio(
    private var id: Int,
    private var nombre: String,
    private var direccion: String,
    private var fechaDeConstruccion: Date,
    private var tienePiscina: Boolean = false,
    private var tieneCancha: Boolean = false,
    private var departamentos: ArrayList<Departamento>
) {
    init {
        this.id
        this.nombre
        this.direccion
        this.fechaDeConstruccion
        this.tienePiscina
        this.tieneCancha
        this.departamentos
    }

    // Getters y Setters
    fun getId(): Int {
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

    fun getFechaDeConstruccion(): Date {
        return fechaDeConstruccion
    }

    fun setFechaDeConstruccion(fechaDeConstruccion: Date) {
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

    fun getDepartamentos(): ArrayList<Departamento> {
        return departamentos
    }

    fun setDepartamentos(departamentos: ArrayList<Departamento>) {
        this.departamentos = departamentos
    }

    // Reglas de negocio
    fun getById(id: Int): Condominio? {
        var condominioEncontrado: Condominio? = null
        getAll().forEach { condominio: Condominio ->
            if (condominio.id == id) condominioEncontrado = condominio
        }
        return condominioEncontrado;
    }

    fun getAll(): ArrayList<Condominio> {
        if (listaDeCondominios.isEmpty()) {
            //TODO
        }
        return listaDeCondominios;
    }

    fun create(condominio: Condominio) {
        getAll().add(condominio)
        // TODO
    }

    fun update(condominioActualizado: Condominio) {
        val listaDeCondominio = getAll()
        listaDeCondominio.forEachIndexed { index, condominio ->
            if (condominio.id == condominioActualizado.id) {
                listaDeCondominio[index] = condominioActualizado
                // TODO
                return
            }
        }
    }

    fun delete(condominioEliminado: Condominio) {
        deleteById(condominioEliminado.id)
        // TODO
    }

    fun deleteById(id: Int) {
        getAll().removeIf { condominio -> condominio.id == id }
        // TODO
    }

    companion object {
        private var listaDeCondominios: ArrayList<Condominio> = arrayListOf<Condominio>()
    }

}