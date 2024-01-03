package com.example.examenbi.dao

import com.example.myapplication.entidad.Departamento

class DepartamentoDAO {

    companion object {
        private var listaDeDepartamentos: ArrayList<Departamento> = arrayListOf()

        init {
            val condominioDAO = CondominioDAO()
            condominioDAO.getAll()
            listaDeDepartamentos.add(
                Departamento(
                    0,
                    1,
                    "César Tufiño",
                    2,
                    15.0,
                    false,
                    condominioDAO.getById(0)!!
                )
            )
            listaDeDepartamentos.add(
                Departamento(
                    1,
                    2,
                    "Cecibel Oñate",
                    3,
                    20.0,
                    true,
                    condominioDAO.getById(0)!!
                )
            )
            listaDeDepartamentos.add(
                Departamento(
                    2,
                    1,
                    "Ricardo Franco",
                    3,
                    30.0,
                    true,
                    condominioDAO.getById(1)!!
                )
            )
        }
    }

    // Reglas de negocio
    fun getById(id: Int): Departamento? {
        var departamentoEncontrado: Departamento? = null
        getAll().forEach { departamento: Departamento ->
            if (departamento.getId() == id) departamentoEncontrado = departamento
        }
        return departamentoEncontrado
    }

    fun getAll(): ArrayList<Departamento> {
        return listaDeDepartamentos
    }

    fun create(departamento: Departamento) {
        val listaDepartamento = getAll()
        if (listaDepartamento.isEmpty()) {
            departamento.setId(0)
        } else {
            departamento.setId(listaDepartamento.last().getId()?.plus(1)!!)
        }
        listaDepartamento.add(departamento)
    }

    fun update(departamentoActualizado: Departamento) {
        val listaDepartamento = getAll()
        listaDepartamento.forEachIndexed { index, departamento ->
            if (departamento.getId() == departamentoActualizado.getId()) {
                listaDepartamento[index] = departamentoActualizado
                return
            }
        }
    }

    fun deleteById(id: Int) {
        val removido = getAll().removeIf { departamento -> departamento.getId() == id }
    }

    fun eliminarByCondominioId(id: Int) {
        val removidos =
            getAll().removeIf { departamento -> departamento.getCondominio().getId() == id }
    }

}