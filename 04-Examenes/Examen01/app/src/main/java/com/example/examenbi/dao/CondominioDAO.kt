package com.example.examenbi.dao

import com.example.examenbi.datos.BaseDeDatos
import com.example.examenbi.entidad.Condominio
import com.example.examenbi.entidad.Departamento
import java.time.LocalDate

class CondominioDAO {

    // Reglas de negocio
    fun getById(id: Int): Condominio? {
        var condominioEncontrado: Condominio? = null
        getAll().forEach { condominio: Condominio ->
            if (condominio.id == id) condominioEncontrado = condominio
        }
        return condominioEncontrado;
    }

    fun getAll(): ArrayList<Condominio> {
        return BaseDeDatos.listaDeCondominios;
    }

    fun create(condominio: Condominio) {
        val listaCondominio = getAll()
        if (listaCondominio.isEmpty()) {
            condominio.id = 0
        } else {
            condominio.id = listaCondominio.last().id?.plus(1)!!
        }
        listaCondominio.add(condominio)
    }

    fun update(condominioActualizado: Condominio) {
        val listaCondominio = getAll()
        listaCondominio.forEachIndexed { index, condominio ->
            if (condominio.id == condominioActualizado.id) {
                listaCondominio[index] = condominioActualizado
                return
            }
        }
    }

    fun deleteById(id: Int): Boolean {
        val departamentoDAO = DepartamentoDAO()
        getAll().forEach { condominio: Condominio ->
            if (condominio.id == id) {
                condominio.departamentos.forEach { departamento: Departamento ->
                    departamentoDAO.deleteById(departamento.id!!)
                }
            }
        }
        return getAll().removeIf { condominio -> (condominio.id == id) }
    }
}