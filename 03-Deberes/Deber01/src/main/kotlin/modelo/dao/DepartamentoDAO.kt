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
        return departamentoEncontrado
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
        return listaDeDepartamentos
    }

    fun create(departamento: Departamento) {
        val listaDepartamento = getAll()
        if (listaDepartamento.isEmpty()){
            departamento.setId(0)
        } else{
            departamento.setId(listaDepartamento.last().getId()?.plus(1)!!)
        }
        listaDepartamento.add(departamento)
        val nuevaLinea = "${departamento.getId()};" +
                "${departamento.getNumero()};" +
                "${departamento.getInquilino()};" +
                "${departamento.getCantidadDeHabitaciones()};" +
                "${departamento.getArea()};" +
                "${departamento.getTieneBalcon()};" +
                "${departamento.getCondominio().getId()}"
        departamentoDatos.appendText("$nuevaLinea\n")
        println("\nDepartamento creado exitosamente")
    }

    fun update(departamentoActualizado: Departamento) {
        val listaDepartamento = getAll()
        listaDepartamento.forEachIndexed { index, departamento ->
            if (departamento.getId() == departamentoActualizado.getId()) {
                listaDepartamento[index] = departamentoActualizado
                actualizacionDeLineas()
                println("Departamento actualizado correctamente")
                return
            }
        }
    }

    private fun actualizacionDeLineas() {
        val lineasActualizadas = listaDeDepartamentos.map {
            "${it.getId()};" +
                    "${it.getNumero()};" +
                    "${it.getInquilino()};" +
                    "${it.getCantidadDeHabitaciones()};" +
                    "${it.getArea()};" +
                    "${it.getTieneBalcon()};" +
                    "${it.getCondominio().getId()}"
        }
        departamentoDatos.writeText(lineasActualizadas.joinToString("\n"))
    }

    fun deleteById(id: Int) {
        val removido = getAll().removeIf { departamento -> departamento.getId() == id }
        if (removido){
            actualizacionDeLineas()
            println("\nDepartamento eliminado correctamente.")
        } else{
            println("\nEl Departamento con ID $id no fue encontrado para eliminar.")
        }
    }

    fun eliminarByCondominioId(id: Int) {
        val removidos = getAll().removeIf { departamento -> departamento.getCondominio().getId() == id }
        if (removidos){
            println("\nDepartamentos relacionados eliminados correctamente.")
            actualizacionDeLineas()
        }
    }

}