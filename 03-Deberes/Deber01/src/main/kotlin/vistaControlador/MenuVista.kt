package vistaControlador

import modelo.dao.DepartamentoDAO

class MenuVista {
    init {
        mostrarMenuVista()
    }

    private fun mostrarMenuVista() {
        val contenidoMenu = "\nIngrese el número de la operación que desea utilizar:\n" +
                "1. Operaciones CRUD en Departamento\n" +
                "2. Operaciones CRUD en Condominio\n" +
                "3. Finalizar\n"
        println(contenidoMenu)
        val opcion = readln().toInt()
        when (opcion) {
            1 -> {
                val departamentoDAO = DepartamentoDAO()
                if (departamentoDAO.getAll().isNotEmpty()) {
                    DepartamentoVista()
                } else {
                    println("No existen condominios actualmente, debe crear uno primero.\n")
                    MenuVista()
                }
            }

            2 -> {
                CondominioVista()
            }

            3 -> {}

            else -> {
                println("Opción no valida, intentelo nuevamente")
                mostrarMenuVista()
            }
        }

    }

}