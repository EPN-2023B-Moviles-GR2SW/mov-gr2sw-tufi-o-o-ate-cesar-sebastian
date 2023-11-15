import java.util.*

fun main() {
    println("Hola mundo")
    // INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Adrian";
    // inmutable = "Vicente";

    //Mutables (Re asignar)
    var mutable: String = "Vicente";
    mutable = "Adrian";

    // val > var
    // Duck Typing
    var ejemploVariable = "Adrian Eguez"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable = edadEjemplo

    // Variables primitivas
    val nombreProfesor: String = "Adrian Equez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    // Clases Java
    val fechaNacimiento: Date = Date()
}