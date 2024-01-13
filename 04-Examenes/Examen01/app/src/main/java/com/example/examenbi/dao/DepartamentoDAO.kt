package com.example.examenbi.dao

import com.example.examenbi.datos.BaseDeDatos
import com.example.examenbi.entidad.Departamento

class DepartamentoDAO {

    // Reglas de negocio
    fun getById(id: Int): Departamento? {
        var departamentoEncontrado: Departamento? = null
        getAll().forEach { departamento: Departamento ->
            if (departamento.id == id) departamentoEncontrado = departamento
        }
        return departamentoEncontrado
    }

    fun getAll(): ArrayList<Departamento> {
        return BaseDeDatos.listaDeDepartamentos;
    }

    fun create(departamento: Departamento) {
        val listaDepartamento = getAll()
        if (listaDepartamento.isEmpty()) {
            departamento.id = 0
        } else {
            departamento.id = listaDepartamento.last().id?.plus(1)!!
        }
        listaDepartamento.add(departamento)
        departamento.condominio.departamentos.add(departamento)
    }

    fun update(departamentoActualizado: Departamento) {
        val listaDepartamento = getAll()
        listaDepartamento.forEachIndexed { index, departamento ->
            if (departamento.id == departamentoActualizado.id) {
                listaDepartamento[index] = departamentoActualizado
                return
            }
        }
    }

    fun deleteById(id: Int) {
        val departamento = getById(id)
        if (departamento!=null){
            departamento.condominio.departamentos.remove(departamento)
            getAll().remove(departamento)
        }
    }

}