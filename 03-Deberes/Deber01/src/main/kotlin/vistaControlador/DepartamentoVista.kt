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
        val contenidoMenu = "\nDepartamentos:\n" +
                "Ingrese el número de la operación CRUD que desea utilizar:\n" +
                "1. Crear\n" +
                "2. Actualizar\n" +
                "3. Consultar Por ID\n" +
                "4. Consultar Todos\n" +
                "5. Eliminar Por ID\n" +
                "6. Volver\n" +
                "7. Finalizar\n"
        println(contenidoMenu)
        val departamentoDAO = DepartamentoDAO()
        val condominioDAO = CondominioDAO()
        val opcion = readln().toInt()
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
                println("ID\t|Nombre\t|Dirección\t|Fecha de Construcción\t|Tiene Piscina\t|Tiene Cancha")
                listaDeCondominios.forEach {
                    println(
                        "" + it.getId()
                                + "|" + it.getNombre()
                                + "|" + it.getDireccion()
                                + "|" + it.getFechaDeConstruccion().format(DateTimeFormatter.ISO_LOCAL_DATE)
                                + "|" + it.getTienePiscina()
                                + "|" + it.getTieneCancha()
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
            }

            2 -> {}

            3 -> {}

            4 -> {}

            5 -> {}

            6 -> {}

            7 -> {}

            else -> {
                println("Opción no valida, intentelo nuevamente")
                mostrarDepartamentoVista()
            }
        }
    }

}