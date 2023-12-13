package modelo.dao

import modelo.entidad.Departamento
import java.io.File

class DepartamentoDAO {
    private var departamentoDatos: File = File("src/main/kotlin/modelo/datos/departamentos.txt")
        .apply { takeIf { !exists() }?.run { createNewFile() } }

    companion object {
        private var listaDeDepartamentos: ArrayList<Departamento> = arrayListOf<Departamento>()
    }

    fun getById(id: Int): Departamento? {
        var departamentoEncontrado: Departamento? = null
        getAll().forEach { departamento: Departamento ->
            if (departamento.getId() == id) departamentoEncontrado = departamento
        }
        return departamentoEncontrado;
    }

    fun getAll(): ArrayList<Departamento> {
        if (listaDeDepartamentos.isEmpty()) {
            val condominioDAO = CondominioDAO()
            departamentoDatos.readLines().forEach {
                val contenido = it.split(";")
                val departamento = Departamento(
                    contenido[0].toInt(),
                    contenido[1].toInt(),
                    contenido[2],
                    contenido[3].toInt(),
                    contenido[4].toDouble(),
                    contenido[5].toBoolean(),
                    condominioDAO.getById(contenido[6].toInt())!!
                )
                listaDeDepartamentos.add(departamento)
            }
        }
        return listaDeDepartamentos;
    }

    fun create(departamento: Departamento) {
        getAll().add(departamento)
        val nuevaLinea = "${getAll().size-1};" +
                "${departamento.getNumero()};" +
                "${departamento.getInquilino()};" +
                "${departamento.getCantidadDeHabitaciones()};" +
                "${departamento.getArea()};" +
                "${departamento.getTieneBalcon()}" +
                "${departamento.getCondominio().getId()}"
        departamentoDatos.appendText("$nuevaLinea\n")
    }

    fun update(departamentoActualizado: Departamento) {
        val listaDeDepartamento = getAll()
        listaDeDepartamento.forEachIndexed { index, departamento ->
            if (departamento.getId() == departamentoActualizado.getId()) {
                listaDeDepartamento[index] = departamentoActualizado
                actualizacionDeLineas()
                return
            }
        }
    }

    private fun actualizacionDeLineas() {
        val lineasActualizadas = DepartamentoDAO.listaDeDepartamentos.map {
            "${it.getId()};" +
                    "${it.getNumero()};" +
                    "${it.getInquilino()};" +
                    "${it.getCantidadDeHabitaciones()};" +
                    "${it.getArea()};" +
                    "${it.getTieneBalcon()}" +
                    "${it.getCondominio().getId()}"
        }
        departamentoDatos.writeText(lineasActualizadas.joinToString("\n"))
    }

    fun deleteById(id: Int) {
        getAll().removeIf { departamento -> departamento.getId() == id }
        actualizacionDeLineas()
    }

}