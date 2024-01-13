package com.example.examenbi.datos

import com.example.examenbi.entidad.Condominio
import com.example.examenbi.entidad.Departamento
import java.time.LocalDate

class BaseDeDatos {

    companion object {
        var listaDeCondominios: ArrayList<Condominio> = arrayListOf()
        var listaDeDepartamentos: ArrayList<Departamento> = arrayListOf()
        init {
            listaDeDepartamentos.add(
                Departamento(
                    0,
                    1,
                    "César Tufiño",
                    2,
                    15.0,
                    false
                )
            )
            listaDeDepartamentos.add(
                Departamento(
                    1,
                    2,
                    "Cecibel Oñate",
                    3,
                    20.0,
                    true
                )
            )
            listaDeDepartamentos.add(
                Departamento(
                    2,
                    1,
                    "Ricardo Franco",
                    3,
                    30.0,
                    true
                )
            )

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
            listaDeCondominios.get(0).departamentos.add(listaDeDepartamentos.get(0))
            listaDeCondominios.get(0).departamentos.add(listaDeDepartamentos.get(1))
            listaDeCondominios.get(1).departamentos.add(listaDeDepartamentos.get(2))
            listaDeDepartamentos.get(0).condominio = listaDeCondominios.get(0)
            listaDeDepartamentos.get(1).condominio = listaDeCondominios.get(0)
            listaDeDepartamentos.get(2).condominio = listaDeCondominios.get(1)
        }
    }

}