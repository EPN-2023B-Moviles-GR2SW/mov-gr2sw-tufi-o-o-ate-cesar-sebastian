package modelo.dao

import modelo.entidad.Condominio
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CondominioDAO {
    private var condominioDatos: File = File("src/main/kotlin/modelo/datos/condominios.txt")
        .apply { takeIf { !exists() }?.run { createNewFile() } }

    companion object {
        private var listaDeCondominios: ArrayList<Condominio> = arrayListOf()
    }

    fun getById(id: Int): Condominio? {
        var condominioEncontrado: Condominio? = null
        getAll().forEach { condominio: Condominio ->
            if (condominio.getId() == id) condominioEncontrado = condominio
        }
        return condominioEncontrado;
    }

    fun getAll(): ArrayList<Condominio> {
        if (listaDeCondominios.isEmpty()) {
            condominioDatos.readLines().forEach {
                val contenido = it.split(";")
                val condominio = Condominio(
                    contenido[0].toInt(),
                    contenido[1],
                    contenido[2],
                    LocalDate.parse(contenido[3]),
                    contenido[4].toBoolean(),
                    contenido[5].toBoolean()
                )
                listaDeCondominios.add(condominio)
            }
        }
        return listaDeCondominios;
    }

    fun create(condominio: Condominio) {
        val listaCondominio = getAll()
        if (listaCondominio.isEmpty()) {
            condominio.setId(0)
        } else {
            condominio.setId(listaCondominio.last().getId()?.plus(1)!!)
        }
        listaCondominio.add(condominio)
        val nuevaLinea = "${condominio.getId()};" +
                "${condominio.getNombre()};" +
                "${condominio.getDireccion()};" +
                "${condominio.getFechaDeConstruccion()};" +
                "${condominio.getTienePiscina()};" +
                "${condominio.getTieneCancha()}"
        condominioDatos.appendText("$nuevaLinea\n")
        println("\nCondominio creado exitosamente")
    }

    fun update(condominioActualizado: Condominio) {
        val listaCondominio = getAll()
        listaCondominio.forEachIndexed { index, condominio ->
            if (condominio.getId() == condominioActualizado.getId()) {
                listaCondominio[index] = condominioActualizado
                actualizacionDeLineas()
                println("Condominio actualizado correctamente")
                return
            }
        }
    }

    private fun actualizacionDeLineas() {
        val lineasActualizadas = listaDeCondominios.map {
            "${it.getId()};" +
                    "${it.getNombre()};" +
                    "${it.getDireccion()};" +
                    "${it.getFechaDeConstruccion().format(DateTimeFormatter.ISO_LOCAL_DATE)};" +
                    "${it.getTienePiscina()};" +
                    "${it.getTieneCancha()}"
        }
        condominioDatos.writeText(lineasActualizadas.joinToString("\n"))
    }

    fun deleteById(id: Int) {
        val departamentoDAO = DepartamentoDAO()
        departamentoDAO.eliminarByCondominioId(id)
        val removido = getAll().removeIf { condominio -> (condominio.getId() == id) }
        if (removido) {
            actualizacionDeLineas()
            println("\nCondominio eliminado correctamente.")
        } else {
            println("\nEl Condominio con ID $id no fue encontrado para eliminar.")
        }
    }

}