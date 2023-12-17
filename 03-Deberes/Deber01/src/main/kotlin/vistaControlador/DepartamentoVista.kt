package vistaControlador

import modelo.dao.CondominioDAO
import modelo.dao.DepartamentoDAO
import modelo.entidad.Departamento
import java.time.format.DateTimeFormatter

class DepartamentoVista {
    init {
        mostrarDepartamentoVista()
    }

    private fun mostrarDepartamentoVista() {
        println(
            "\n--Departamentos--" +
                    "\nIngrese el número de la operación CRUD que desea utilizar:" +
                    "\n1. Crear" +
                    "\n2. Actualizar" +
                    "\n3. Consultar Por ID" +
                    "\n4. Consultar Todos" +
                    "\n5. Eliminar Por ID" +
                    "\n6. Volver" +
                    "\n7. Finalizar" +
                    "\nOpción:"
        )
        val opcion = readln().toInt()
        val departamentoDAO = DepartamentoDAO()
        val condominioDAO = CondominioDAO()
        when (opcion) {
            1 -> {
                var departamento: Departamento = Departamento()
                println("Ingrese la información del nuevo departamento:")
                println("Número:")
                departamento.setNumero(readln().toInt())
                println("Inquilino:")
                departamento.setInquilino(readln())
                println("Cantidad De Habitaciones:")
                departamento.setCantidadDeHabitaciones(readln().toInt())
                println("Área:")
                departamento.setArea(readln().toDouble())
                println("¿Tiene balcón?")
                println("1. No")
                println("2. Sí")
                if (readln().toInt() == 1) {
                    departamento.setTieneBalcon(false)
                } else {
                    departamento.setTieneBalcon(true)
                }
                println("Condominios: ")
                val listaDeCondominios = condominioDAO.getAll()
                listaDeCondominios.forEach {
                    println(
                        "ID: " + it.getId()
                                + "\nNombre: " + it.getNombre()
                                + "\nDirección: " + it.getDireccion()
                                + "\nFecha de Construcción: " + it.getFechaDeConstruccion()
                            .format(DateTimeFormatter.ISO_LOCAL_DATE)
                                + "\nTiene Piscina: " + (if (it.getTienePiscina()) "Sí" else "No")
                                + "\nTiene Cancha: " + (if (it.getTieneCancha()) "Sí" else "No")
                                + "\n"
                    )
                }
                println("Ingrese el ID del condominio:")
                val condominio = condominioDAO.getById(readln().toInt())
                if (condominio == null) {
                    println("El ID ingresado no existe.")
                    mostrarDepartamentoVista()
                } else {
                    departamento.setCondominio(condominio)
                }
                departamentoDAO.create(departamento)
                mostrarDepartamentoVista()
            }

            2 -> {
                println("Ingrese el ID del departamento que desea actualizar:")
                val idDepartamentoActualizar = readln().toInt()
                val departamentoExistente = departamentoDAO.getById(idDepartamentoActualizar)

                if (departamentoExistente != null) {
                    println(
                        "\nID: " + departamentoExistente.getId() +
                                "\nNúmero: " + departamentoExistente.getNumero() +
                                "\nInquilino: " + departamentoExistente.getInquilino() +
                                "\nCantidad de Habitaciones: " + departamentoExistente.getCantidadDeHabitaciones() +
                                "\nÁrea: " + departamentoExistente.getArea() +
                                "\n¿Tiene Balcón?: " + (if (departamentoExistente.getTieneBalcon()) "Sí" else "No") +
                                "\nCondominio: " + departamentoExistente.getCondominio().getId()

                    )
                    println("Ingrese la información actualizada del departamento:")
                    println("Número:")
                    departamentoExistente.setNumero(readln().toInt())
                    println("Inquilino:")
                    departamentoExistente.setInquilino(readln())
                    println("Cantidad De Habitaciones:")
                    departamentoExistente.setCantidadDeHabitaciones(readln().toInt())
                    println("Área:")
                    departamentoExistente.setArea(readln().toDouble())
                    println("¿Tiene balcón?")
                    println("1. No")
                    println("2. Sí")
                    if (readln().toInt() == 1) {
                        departamentoExistente.setTieneBalcon(false)
                    } else {
                        departamentoExistente.setTieneBalcon(true)
                    }
                    println("Condominios: ")
                    val listaDeCondominios = condominioDAO.getAll()
                    listaDeCondominios.forEach {
                        println(
                            "ID: " + it.getId() +
                                    "\nNombre: " + it.getNombre() +
                                    "\nDirección: " + it.getDireccion() +
                                    "\nFecha de Construcción: " + it.getFechaDeConstruccion()
                                .format(DateTimeFormatter.ISO_LOCAL_DATE) +
                                    "\nTiene Piscina: " + it.getTienePiscina() +
                                    "\nTiene Cancha: " + it.getTieneCancha() +
                                    "\n"
                        )
                    }
                    println("Ingrese el ID del condominio:")
                    val condominio = condominioDAO.getById(readln().toInt())
                    if (condominio == null) {
                        println("El ID ingresado no existe.")
                        mostrarDepartamentoVista()
                    } else {
                        departamentoExistente.setCondominio(condominio)
                    }
                    departamentoDAO.update(departamentoExistente)
                } else {
                    println("El ID del departamento no existe.")
                }
                mostrarDepartamentoVista()
            }

            3 -> {
                println("Ingrese el ID del departamento que desea consultar:")
                val idDepartamentoConsultar = readln().toInt()
                val departamentoEncontrado = departamentoDAO.getById(idDepartamentoConsultar)
                if (departamentoEncontrado != null) {
                    println(
                        "\nID: " + departamentoEncontrado.getId() +
                                "\nNúmero: " + departamentoEncontrado.getNumero() +
                                "\nInquilino: " + departamentoEncontrado.getInquilino() +
                                "\nCantidad de Habitaciones: " + departamentoEncontrado.getCantidadDeHabitaciones() +
                                "\nÁrea: " + departamentoEncontrado.getArea() +
                                "\n¿Tiene Balcón?: " + (if (departamentoEncontrado.getTieneBalcon()) "Sí" else "No") +
                                "\nCondominio: " + departamentoEncontrado.getCondominio().getId() + "\n"
                    )
                } else {
                    println("\nEl ID del departamento no existe.")
                }
                mostrarDepartamentoVista()
            }

            4 -> {
                val listaDepartamentos = departamentoDAO.getAll()
                if (listaDepartamentos.isNotEmpty()) {
                    listaDepartamentos.forEach { departamento ->
                        println(
                            "\nID: " + departamento.getId() +
                                    "\nNúmero: " + departamento.getNumero() +
                                    "\nInquilino: " + departamento.getInquilino() +
                                    "\nCantidad de Habitaciones: " + departamento.getCantidadDeHabitaciones() +
                                    "\nÁrea: " + departamento.getArea() + " m2" +
                                    "\n¿Tiene Balcón?: " + (if (departamento.getTieneBalcon()) "Sí" else "No") +
                                    "\nCondominio: " + departamento.getCondominio().getId()
                        )
                    }
                } else {
                    println("\nNo hay departamentos registrados.")
                }
                mostrarDepartamentoVista()
            }

            5 -> {
                println("Ingrese el ID del departamento que desea eliminar:")
                val idDepartamentoEliminar = readln().toInt()
                departamentoDAO.deleteById(idDepartamentoEliminar)
                mostrarDepartamentoVista()
            }

            6 -> {
                MenuVista()
            }

            7 -> {
                print("\nFin de la aplicación")
            }

            else -> {
                println("\nOpción no valida, intentelo nuevamente")
                mostrarDepartamentoVista()
            }
        }
    }

}