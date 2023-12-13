package vistaControlador

import modelo.dao.CondominioDAO
import modelo.dao.DepartamentoDAO
import modelo.entidad.Condominio
import modelo.entidad.Departamento
import java.time.LocalDate

class CondominioVista {
    init {
        mostrarCondominioVista()
    }

    private fun mostrarCondominioVista() {
        val contenidoMenu = "\nCondominios:\n" +
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
                var condominio: Condominio = Condominio()
                println("Ingrese la información del nuevo condominio:")
                println("Nombre:")
                condominio.setNombre(readln())
                println("Dirección:")
                condominio.setDireccion(readln())
                println("Fecha de construcción (aaaa-mm-dd):")
                condominio.setFechaDeConstruccion(LocalDate.parse(readln()))
                println("¿Tiene piscina?")
                println("1. No")
                println("2. Sí")
                if (readln().toInt() == 1) {
                    condominio.setTienePiscina(false)
                } else {
                    condominio.setTienePiscina(true)
                }
                println("¿Tiene cancha?")
                println("1. No")
                println("2. Sí")
                if (readln().toInt() == 1) {
                    condominio.setTieneCancha(false)
                } else {
                    condominio.setTieneCancha(true)
                }
                condominioDAO.create(condominio)
            }

            2 -> {}

            3 -> {}

            4 -> {}

            5 -> {}

            6 -> {}

            7 -> {}

            else -> {
                println("Opción no valida, intentelo nuevamente")
                mostrarCondominioVista()
            }
        }
    }

}