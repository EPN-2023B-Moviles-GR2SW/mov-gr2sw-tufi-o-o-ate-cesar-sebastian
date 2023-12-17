package vistaControlador

import modelo.dao.CondominioDAO
import modelo.dao.DepartamentoDAO

class MenuVista {
    init {
        mostrarMenuVista()
    }

    private fun mostrarMenuVista() {
        println(
            "\nIngrese el número de la operación que desea utilizar:" +
                    "\n1. Operaciones CRUD en Departamento" +
                    "\n2. Operaciones CRUD en Condominio" +
                    "\n3. Finalizar" +
                    "\nOpción: "
        )
        val opcion = readln().toInt()
        when (opcion) {
            1 -> {
                val condominioDAO = CondominioDAO()
                if (condominioDAO.getAll().isNotEmpty()) {
                    DepartamentoVista()
                } else {
                    println("\nNo existen condominios actualmente, debe crear uno primero.")
                    MenuVista()
                }
            }
            2 -> {
                CondominioVista()
            }

            3 -> {
                print("\nFin de la aplicación")
            }

            else -> {
                println("\nOpción no valida, intentelo nuevamente")
                mostrarMenuVista()
            }
        }

    }

}