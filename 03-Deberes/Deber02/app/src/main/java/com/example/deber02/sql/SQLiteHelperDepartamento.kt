package com.example.deber02.sql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deber02.entidad.Departamento

class SQLiteHelperDepartamento(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "CondominiosBD", // nombre BDD
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {}

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearDepartamento(departamento: Departamento, condominioId: Int): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("numero", departamento.numero)
        valoresAGuardar.put("inquilino", departamento.inquilino)
        valoresAGuardar.put("cantidadDeHabitaciones", departamento.cantidadDeHabitaciones)
        valoresAGuardar.put("area", departamento.area)
        valoresAGuardar.put("tieneBalcon", if (departamento.tieneBalcon) 1 else 0)
        valoresAGuardar.put("condominioId", condominioId)

        val resultadoGuardar = basedatosEscritura.insert("DEPARTAMENTO", null, valoresAGuardar)
        basedatosEscritura.close()

        return resultadoGuardar.toInt() != -1
    }

    fun eliminarDepartamentoPorID(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura.delete(
            "DEPARTAMENTO", // Nombre tabla
            "id=?", // Consulta Where
            parametrosConsultaDelete
        )
        conexionEscritura.close()

        return resultadoEliminacion.toInt() != -1
    }

    fun actualizarDepartamento(departamento: Departamento): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("numero", departamento.numero)
        valoresAActualizar.put("inquilino", departamento.inquilino)
        valoresAActualizar.put("cantidadDeHabitaciones", departamento.cantidadDeHabitaciones)
        valoresAActualizar.put("area", departamento.area)
        valoresAActualizar.put("tieneBalcon", if (departamento.tieneBalcon) 1 else 0)

        val parametrosConsultaActualizar = arrayOf(departamento.id.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "DEPARTAMENTO", // Nombre tabla
            valoresAActualizar, // Valores
            "id=?", // Consulta Where
            parametrosConsultaActualizar
        )
        conexionEscritura.close()

        return resultadoActualizacion.toInt() != -1
    }

    fun consultarDepartamentoPorID(id: Int): Departamento {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM DEPARTAMENTO WHERE ID = ?
            """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        // lógica de búsqueda
        val existeDepartamento = resultadoConsultaLectura.moveToFirst()
        val departamentoEncontrado = Departamento()

        if (existeDepartamento) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Índice 0
                val numero = resultadoConsultaLectura.getInt(1)
                val inquilino = resultadoConsultaLectura.getString(2)
                val cantidadDeHabitaciones = resultadoConsultaLectura.getInt(3)
                val area = resultadoConsultaLectura.getDouble(4)
                val tieneBalcon = resultadoConsultaLectura.getInt(5) == 1
                val condominioId = resultadoConsultaLectura.getInt(6)

                departamentoEncontrado.id = id
                departamentoEncontrado.numero = numero
                departamentoEncontrado.inquilino = inquilino
                departamentoEncontrado.cantidadDeHabitaciones = cantidadDeHabitaciones
                departamentoEncontrado.area = area
                departamentoEncontrado.tieneBalcon = tieneBalcon
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return departamentoEncontrado
    }

    fun consultarTodosLosDepartamentos(): ArrayList<Departamento> {
        val departamentos = arrayListOf<Departamento>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM DEPARTAMENTO"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0) // Índice 0
                val numero = resultadoConsultaLectura.getInt(1)
                val inquilino = resultadoConsultaLectura.getString(2)
                val cantidadDeHabitaciones = resultadoConsultaLectura.getInt(3)
                val area = resultadoConsultaLectura.getDouble(4)
                val tieneBalcon = resultadoConsultaLectura.getInt(5) == 1
                val condominioId = resultadoConsultaLectura.getInt(6)

                val departamento = Departamento(id, numero, inquilino, cantidadDeHabitaciones, area, tieneBalcon)
                departamentos.add(departamento)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return departamentos
    }

    fun consultarDepartamentosPorCondominioId(condominioId: Int): ArrayList<Departamento> {
        val departamentos = arrayListOf<Departamento>()
        val baseDatosLectura = readableDatabase

        val scriptConsultaLectura = "SELECT * FROM DEPARTAMENTO WHERE condominioId = ?"
        val parametrosConsultaLectura = arrayOf(condominioId.toString())

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val numero = resultadoConsultaLectura.getInt(1)
                val inquilino = resultadoConsultaLectura.getString(2)
                val cantidadDeHabitaciones = resultadoConsultaLectura.getInt(3)
                val area = resultadoConsultaLectura.getDouble(4)
                val tieneBalcon = resultadoConsultaLectura.getInt(5) == 1

                val departamento = Departamento(id, numero, inquilino, cantidadDeHabitaciones, area, tieneBalcon)
                departamentos.add(departamento)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return departamentos
    }

}