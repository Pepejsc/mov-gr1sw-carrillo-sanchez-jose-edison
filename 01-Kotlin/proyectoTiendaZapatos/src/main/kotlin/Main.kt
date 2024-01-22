import java.io.File
import java.text.SimpleDateFormat
import java.util.*

data class Zapato(
    val id: Int,
    val marca: String,
    val talla: Int,
    val precio: Double,
    val disponible: Boolean
)

data class Tienda(
    val id: Int,
    var nombre: String,
    var direccion: String,
    val fechaApertura: Date,
    val zapatos: MutableList<Zapato> = mutableListOf()
)

fun main() {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val tiendas = mutableListOf<Tienda>()
    val zapatosFile = File("zapatos.txt")
    val tiendasFile = File("tiendas.txt")

    // Función para cargar datos desde archivos
    fun cargarDatos() {
        tiendas.clear()
        if (tiendasFile.exists()) {
            tiendasFile.forEachLine { line ->
                val data = line.split(",")
                val id = data[0].toInt()
                val nombre = data[1]
                val direccion = data[2]
                val fechaString = data[3]
                val fecha = dateFormat.parse(fechaString)
                val tienda = Tienda(id, nombre, direccion, fecha)
                tiendas.add(tienda)
            }
        }

        if (zapatosFile.exists()) {
            zapatosFile.forEachLine { line ->
                val data = line.split(",")
                val tiendaId = data[0].toInt()
                val id = data[1].toInt()
                val marca = data[2]
                val talla = data[3].toInt()
                val precio = data[4].toDouble()
                val disponible = data[5].toBoolean()

                val tienda = tiendas.find { it.id == tiendaId }
                tienda?.zapatos?.add(Zapato(id, marca, talla, precio, disponible))
            }
        }
    }

    // Función para guardar datos en archivos
    fun guardarDatos() {
        tiendasFile.bufferedWriter().use { writer ->
            tiendas.forEach { tienda ->
                val fechaString = dateFormat.format(tienda.fechaApertura)
                writer.write("${tienda.id},${tienda.nombre},${tienda.direccion},$fechaString\n")
            }
        }

        zapatosFile.bufferedWriter().use { writer ->
            tiendas.forEach { tienda ->
                tienda.zapatos.forEach { zapato ->
                    writer.write("${tienda.id},${zapato.id},${zapato.marca},${zapato.talla},${zapato.precio},${zapato.disponible}\n")
                }
            }
        }
    }

    // Cargar datos al iniciar
    cargarDatos()

    var tiendaIdCounter = tiendas.map { it.id }.maxOrNull()?.plus(1) ?: 1
    var zapatoIdCounter = tiendas.flatMap { it.zapatos.map { it.id } }.maxOrNull()?.plus(1) ?: 1

    while (true) {
        println("\nSelecciona una opción:")
        println("1. Crear tienda")
        println("2. Ver tiendas")
        println("3. Actualizar tienda")
        println("4. Eliminar tienda")
        println("5. Agregar zapato a tienda")
        println("6. Ver zapatos de una tienda")
        println("7. Salir")

        when (readLine()?.toIntOrNull() ?: 7) {
            1 -> {
                println("Ingrese el nombre de la tienda:")
                val nombre = readLine() ?: ""
                println("Ingrese la dirección de la tienda:")
                val direccion = readLine() ?: ""
                val fechaApertura = Date()

                val nuevaTienda = Tienda(tiendaIdCounter++, nombre, direccion, fechaApertura)
                tiendas.add(nuevaTienda)
                println("Tienda creada con éxito.")
                guardarDatos()
            }
            2 -> {
                if (tiendas.isEmpty()) {
                    println("No hay tiendas registradas.")
                } else {
                    println("Tiendas registradas:")
                    tiendas.forEach { println("${it.id}. ${it.nombre}") }
                }
            }
            3 -> {
                println("Ingrese el ID de la tienda a actualizar:")
                val idActualizar = readLine()?.toIntOrNull()
                val tiendaActualizar = tiendas.find { it.id == idActualizar }

                if (tiendaActualizar != null) {
                    println("Ingrese el nuevo nombre de la tienda:")
                    val nuevoNombre = readLine() ?: ""
                    println("Ingrese la nueva dirección de la tienda:")
                    val nuevaDireccion = readLine() ?: ""

                    tiendaActualizar.nombre = nuevoNombre
                    tiendaActualizar.direccion = nuevaDireccion
                    println("Tienda actualizada con éxito.")
                    guardarDatos()
                } else {
                    println("Tienda no encontrada.")
                }
            }
            4 -> {
                println("Ingrese el ID de la tienda a eliminar:")
                val idEliminar = readLine()?.toIntOrNull()
                val tiendaEliminar = tiendas.find { it.id == idEliminar }

                if (tiendaEliminar != null) {
                    tiendas.remove(tiendaEliminar)
                    println("Tienda eliminada con éxito.")
                    guardarDatos()
                } else {
                    println("Tienda no encontrada.")
                }
            }
            5 -> {
                println("Ingrese el ID de la tienda a la que desea agregar el zapato:")
                val idTiendaAgregarZapato = readLine()?.toIntOrNull()
                val tiendaAgregarZapato = tiendas.find { it.id == idTiendaAgregarZapato }

                if (tiendaAgregarZapato != null) {
                    println("Ingrese la marca del zapato:")
                    val marca = readLine() ?: ""
                    println("Ingrese la talla del zapato:")
                    val talla = readLine()?.toIntOrNull() ?: 0
                    println("Ingrese el precio del zapato:")
                    val precio = readLine()?.toDoubleOrNull() ?: 0.0
                    println("¿El zapato está disponible? (true/false):")
                    val disponible = readLine()?.toBoolean() ?: false

                    val nuevoZapato = Zapato(zapatoIdCounter++, marca, talla, precio, disponible)
                    tiendaAgregarZapato.zapatos.add(nuevoZapato)
                    println("Zapato agregado con éxito a la tienda.")
                    guardarDatos()
                } else {
                    println("Tienda no encontrada.")
                }
            }
            6 -> {
                println("Ingrese el ID de la tienda para ver sus zapatos:")
                val idTiendaVerZapatos = readLine()?.toIntOrNull()
                val tiendaVerZapatos = tiendas.find { it.id == idTiendaVerZapatos }

                if (tiendaVerZapatos != null) {
                    if (tiendaVerZapatos.zapatos.isEmpty()) {
                        println("La tienda no tiene zapatos registrados.")
                    } else {
                        println("Zapatos en la tienda ${tiendaVerZapatos.nombre}:")
                        tiendaVerZapatos.zapatos.forEach {
                            println("ID: ${it.id}, Marca: ${it.marca}, Talla: ${it.talla}, Precio: ${it.precio}, Disponible: ${it.disponible}")
                        }
                    }
                } else {
                    println("Tienda no encontrada.")
                }
            }
            7 -> {
                guardarDatos()
                break
            }
            else -> println("Opción inválida, por favor seleccione una opción válida.")
        }
    }
}