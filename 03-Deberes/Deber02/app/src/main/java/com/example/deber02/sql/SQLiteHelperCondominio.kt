package com.example.deber02.sql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deber02.entidad.Condominio
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SQLiteHelperCondominio(
    val contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "CondominiosBD", // Nombre
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaCondominio =
            """
               CREATE TABLE CONDOMINIO(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre VARCHAR(100),
               direccion VARCHAR(100),
               fechaDeConstruccion TEXT,
               tienePiscina INTEGER,
               tieneCancha INTEGER
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaCondominio)
        val scriptSQLCrearTablaDepartamento =
            """
               CREATE TABLE DEPARTAMENTO(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               numero INTEGER,
               inquilino VARCHAR(50),
               cantidadDeHabitaciones INTEGER,
               area REAL,
               tieneBalcon INTEGER,
               condominioId INTEGER,
               FOREIGN KEY (condominioId) REFERENCES CONDOMINIO(id)
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaDepartamento)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearCondominio(condominio: Condominio): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", condominio.nombre)
        valoresAGuardar.put("direccion", condominio.direccion)
        valoresAGuardar.put(
            "fechaDeConstruccion", condominio.fechaDeConstruccion.format(
                DateTimeFormatter.ISO_LOCAL_DATE
            )
        )
        valoresAGuardar.put("tienePiscina", if (condominio.tienePiscina) 1 else 0)
        valoresAGuardar.put("tieneCancha", if (condominio.tieneCancha) 1 else 0)

        val resultadoGuardar = basedatosEscritura.insert("CONDOMINIO", null, valoresAGuardar)
        basedatosEscritura.close()

        return resultadoGuardar.toInt() != -1
    }

    fun eliminarCondominioPorID(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura.delete(
            "CONDOMINIO", // Nombre tabla
            "id=?", // Consulta Where
            parametrosConsultaDelete
        )
        conexionEscritura.close()

        return resultadoEliminacion.toInt() != -1
    }

    fun actualizarCondominio(
        condominio: Condominio
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", condominio.nombre)
        valoresAActualizar.put("direccion", condominio.direccion)
        valoresAActualizar.put(
            "fechaDeConstruccion",
            condominio.fechaDeConstruccion.toString()
        )
        valoresAActualizar.put("tienePiscina", if (condominio.tienePiscina) 1 else 0)
        valoresAActualizar.put("tieneCancha", if (condominio.tieneCancha) 1 else 0)

        val parametrosConsultaActualizar = arrayOf(condominio.id.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "CONDOMINIO", // Nombre tabla
            valoresAActualizar, // Valores
            "id=?", // Consulta Where
            parametrosConsultaActualizar
        )
        conexionEscritura.close()

        return resultadoActualizacion.toInt() != -1
    }

    fun consultarCondominioPorID(id: Int): Condominio {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM CONDOMINIO WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        // lógica de búsqueda
        val existeCondominio = resultadoConsultaLectura.moveToFirst()
        val condominioEncontrado = Condominio()

        if (existeCondominio) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val direccion = resultadoConsultaLectura.getString(2)
                val fechaDeConstruccionString = resultadoConsultaLectura.getString(3)
                val fechaDeConstruccion =
                    LocalDate.parse(fechaDeConstruccionString)
                val tienePiscina = resultadoConsultaLectura.getInt(4) == 1
                val tieneCancha = resultadoConsultaLectura.getInt(5) == 1

                condominioEncontrado.id = id
                condominioEncontrado.nombre = nombre
                condominioEncontrado.direccion = direccion
                condominioEncontrado.fechaDeConstruccion = fechaDeConstruccion
                condominioEncontrado.tienePiscina = tienePiscina
                condominioEncontrado.tieneCancha = tieneCancha
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return condominioEncontrado
    }

    fun consultarTodosLosCondominios(): ArrayList<Condominio> {
        val condominios = arrayListOf<Condominio>()
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM CONDOMINIO
        """.trimIndent()

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val direccion = resultadoConsultaLectura.getString(2)
                val fechaDeConstruccionString = resultadoConsultaLectura.getString(3)
                val fechaDeConstruccion = LocalDate.parse(fechaDeConstruccionString)
                val tienePiscina = resultadoConsultaLectura.getInt(4) == 1
                val tieneCancha = resultadoConsultaLectura.getInt(5) == 1

                val condominio = Condominio(
                    id,
                    nombre,
                    direccion,
                    fechaDeConstruccion,
                    tienePiscina,
                    tieneCancha
                )
                condominios.add(condominio)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return condominios
    }
}