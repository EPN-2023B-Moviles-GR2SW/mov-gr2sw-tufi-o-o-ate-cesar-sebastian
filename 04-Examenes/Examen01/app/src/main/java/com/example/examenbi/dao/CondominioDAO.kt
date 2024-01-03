package com.example.examenbi.dao

import com.example.myapplication.entidad.Condominio
import java.time.LocalDate

class CondominioDAO {

    companion object {
        private var listaDeCondominios: ArrayList<Condominio> = arrayListOf()
        init {
            listaDeCondominios.add(
                Condominio(
                    0,
                    "La Primavera",
                    "De Los Mortiños Y Av. Eloy Alfaro, Quito",
                    LocalDate.of(2001, 12, 13),
                    false,
                    true
                )
            )
            listaDeCondominios.add(
                Condominio(
                    1,
                    "Los Pinos",
                    "Av. 17 de Septiembre Y Carapungo, Quito",
                    LocalDate.of(2003, 10, 17),
                    true,
                    true
                )
            )
        }
    }

    // Reglas de negocio
    fun getById(id: Int): Condominio? {
        var condominioEncontrado: Condominio? = null
        getAll().forEach { condominio: Condominio ->
            if (condominio.getId() == id) condominioEncontrado = condominio
        }
        return condominioEncontrado;
    }

    fun getAll(): ArrayList<Condominio> {
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
    }

    fun update(condominioActualizado: Condominio) {
        val listaCondominio = getAll()
        listaCondominio.forEachIndexed { index, condominio ->
            if (condominio.getId() == condominioActualizado.getId()) {
                listaCondominio[index] = condominioActualizado
                return
            }
        }
    }

    fun deleteById(id: Int) {
        val departamentoDAO = DepartamentoDAO()
        departamentoDAO.eliminarByCondominioId(id)
        val removido = getAll().removeIf { condominio -> (condominio.getId() == id) }
    }
}