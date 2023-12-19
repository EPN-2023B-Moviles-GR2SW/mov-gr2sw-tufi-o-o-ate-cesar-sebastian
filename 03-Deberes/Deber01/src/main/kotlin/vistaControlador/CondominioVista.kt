package vistaControlador

import modelo.dao.CondominioDAO
import modelo.dao.DepartamentoDAO
import modelo.entidad.Condominio
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CondominioVista {
    init {
        mostrarCondominioVista()
    }

    private fun mostrarCondominioVista() {
        println(
            "\n--Condominios--" +
                    "\nIngrese el número de la operación CRUD que desea utilizar:" +
                    "\n1. Crear" +
                    "\n2. Actualizar" +
                    "\n3. Consultar Por ID" +
                    "\n4. Consultar Todos" +
                    "\n5. Eliminar Por ID" +
                    "\n6. Volver" +
                    "\n7. Finalizar" +
                    "\nOpción: "
        )
        val condominioDAO = CondominioDAO()
        val opcion = readln().toInt()
        when (opcion) {
            1 -> {
                var nuevoCondominio = Condominio()
                println("Ingrese la información del nuevo condominio:")
                println("Nombre:")
                nuevoCondominio.setNombre(readln())
                println("Dirección:")
                nuevoCondominio.setDireccion(readln())
                println("Fecha de construcción (aaaa-mm-dd):")
                nuevoCondominio.setFechaDeConstruccion(LocalDate.parse(readln()))
                println("¿Tiene piscina?")
                println("1. No")
                println("2. Sí")
                if (readln().toInt() == 1) {
                    nuevoCondominio.setTienePiscina(false)
                } else {
                    nuevoCondominio.setTienePiscina(true)
                }
                println("¿Tiene cancha?")
                println("1. No")
                println("2. Sí")
                if (readln().toInt() == 1) {
                    nuevoCondominio.setTieneCancha(false)
                } else {
                    nuevoCondominio.setTieneCancha(true)
                }
                condominioDAO.create(nuevoCondominio)
                mostrarCondominioVista()
            }

            2 -> {
                println("Ingrese el ID del condominio que desea actualizar:")
                val idCondominioActualizar = readln().toInt()
                val condominioExistente = condominioDAO.getById(idCondominioActualizar)

                if (condominioExistente != null) {
                    println(
                        "\nID: " + condominioExistente.getId()
                                + "\nNombre: " + condominioExistente.getNombre()
                                + "\nDirección: " + condominioExistente.getDireccion()
                                + "\nFecha de Construcción: " + condominioExistente.getFechaDeConstruccion()
                            .format(DateTimeFormatter.ISO_LOCAL_DATE)
                                + "\nTiene Piscina: " + (if (condominioExistente.getTienePiscina()) "Sí" else "No")
                                + "\nTiene Cancha: " + (if (condominioExistente.getTieneCancha()) "Sí" else "No") + "\n"
                    )
                    println("Ingrese la información actualizada del condominio:")
                    println("Nombre:")
                    condominioExistente.setNombre(readln())
                    println("Dirección:")
                    condominioExistente.setDireccion(readln())
                    println("Fecha de construcción (aaaa-mm-dd):")
                    condominioExistente.setFechaDeConstruccion(LocalDate.parse(readln()))
                    println("¿Tiene piscina?")
                    println("1. No")
                    println("2. Sí")
                    if (readln().toInt() == 1) {
                        condominioExistente.setTienePiscina(false)
                    } else {
                        condominioExistente.setTienePiscina(true)
                    }
                    println("¿Tiene cancha?")
                    println("1. No")
                    println("2. Sí")
                    if (readln().toInt() == 1) {
                        condominioExistente.setTieneCancha(false)
                    } else {
                        condominioExistente.setTieneCancha(true)
                    }
                    condominioDAO.update(condominioExistente)
                } else {
                    println("El ID del condominio no existe.")
                }
                mostrarCondominioVista()
            }

            3 -> {
                println("Ingrese el id del condominio:")
                val condominioEncontrado = condominioDAO.getById(readln().toInt())
                if (condominioEncontrado != null) {
                    println(
                        "\nID: " + condominioEncontrado.getId()
                                + "\nNombre: " + condominioEncontrado.getNombre()
                                + "\nDirección: " + condominioEncontrado.getDireccion()
                                + "\nFecha de Construcción: " + condominioEncontrado.getFechaDeConstruccion()
                            .format(DateTimeFormatter.ISO_LOCAL_DATE)
                                + "\nTiene Piscina: " + (if (condominioEncontrado.getTienePiscina()) "Sí" else "No")
                                + "\nTiene Cancha: " + (if (condominioEncontrado.getTieneCancha()) "Sí" else "No")
                    )
                } else {
                    println("\nEl Condominio con ID $condominioEncontrado no fue encontrado")
                }
                mostrarCondominioVista()

            }

            4 -> {
                val listaCondominios = condominioDAO.getAll()
                if (listaCondominios.isNotEmpty()) {
                    listaCondominios.forEach { condominio ->
                        println(
                            "\nID: " + condominio.getId() +
                                    "\nNombre: " + condominio.getNombre() +
                                    "\nDirección: " + condominio.getDireccion() +
                                    "\nFecha de Construcción: " + condominio.getFechaDeConstruccion()
                                .format(DateTimeFormatter.ISO_LOCAL_DATE) +
                                    "\n¿Tiene Piscina?: " + (if (condominio.getTienePiscina()) "Sí" else "No") +
                                    "\n¿Tiene Cancha?: " + (if (condominio.getTieneCancha()) "Sí" else "No")
                        )
                    }
                } else {
                    println("\nNo hay condominios registrados.")
                }
                mostrarCondominioVista()
            }

            5 -> {
                println("Ingrese el ID del condominio que desea eliminar:")
                val idCondominioEliminar = readln().toInt()
                condominioDAO.deleteById(idCondominioEliminar)
                mostrarCondominioVista()
            }

            6 -> {
                MenuVista()
            }

            7 -> {
                print("\nFin de la aplicación")
            }

            else -> {
                println("\nOpción no valida, intentelo nuevamente")
                mostrarCondominioVista()
            }
        }
    }

}