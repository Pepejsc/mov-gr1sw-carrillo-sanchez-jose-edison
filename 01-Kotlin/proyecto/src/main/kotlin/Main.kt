import java.util.*
import kotlin.collections.ArrayList

fun main( ) {

   /* abstract class NumerosJava{
        protected val numeroUno: Int
        private val numeroDos: Int

        constructor(
            uno: Int,
            dos: Int
        ){//bloque de codigo del constructor
            this.numeroUno = uno
            this.numeroDos = dos
            println("Inicializando")
        }
    }

    abstract class Numeros(//Constructor primario
        //Ejemplo
        //uno: Int, // (parametro sin modificador de acceso)
        protected val numeroUno: Int,
        //Propiedad de la clase protected numeros.numeroUno
        protected val numeroDos: Int,
    ){
        // var cedula: string = "" (public es por defecto)
        // private valorCalculador: Int = 0 (private)
        init {
            this.numeroUno; this.numeroDos; //this es opcional
            numeroUno; numeroDos; // sin el "this", es lo mismo
            println("Inicializando")
        }
    }

    class Suma(
        uno:Int, //parametro
        dos:Int  //parametro
    ): Numeros(uno, dos){ //constructor del padre
        init{ //bloque constructor primario
            this.numeroUno; numeroUno;
            this.numeroDos; numeroDos;
        }
        constructor( //Segundo constructor
            uno: Int?, //parametros
            dos: Int //parametros
        ): this( //llamada constructor primario
            if(uno == null) 0 else uno,
            dos
        ){//si necesitamos bloque de codigo lo usamos
            numeroUno;
        }
        constructor( //Tercer constructor
            uno: Int, //parametros
            dos: Int? //parametros
        ): this( //llamada constructor primario
            if(dos == null) 0 else uno, dos
        ){//si no lo necesitamos bloque de codigo "{}" se omite
        }
        constructor( //cuarto constructor
            uno: Int?, //parametros
            dos: Int? //parametros
        ): this( //llamada constructor primario
            uno = if(uno == null) 0 else uno,
            dos = if(dos == null) 0 else uno
        )
        fun sumar(): Int{//public por defecto, o se usa private o protected
            var total = numeroUno + numeroDos
            Suma.Companion.agregarHistorial(total)
            return total
        }

        companion object{
            //Atibutos y Metodos "Compartidos"
            //entre las instancias
            val pi = 3.14
            fun elevarAlCuadrado(num: Int): Int{
                return num * num
            }
            val historialSumas = arrayListOf<Int>()
            fun agregarHistorial(valorNuevaSuma:Int){
                historialSumas.add(valorNuevaSuma)
            }
        }
    }

    println("Hello World!")
    //Tip os de variables
    //Inmutables (No se reasignan "=")
    val inmutable: String = "Edison"
    //inmutable = "Edison"

    //Mutables (Re asignar)
    var mutable: String = "Jose"
    mutable = "Edison"

    //val > var

    //Duck tipyng
    var ejemploVariable = "Edison Sanchez"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable = edadEjemplo

    //Variables primitivas
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo:Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    //Clases Java
    val fechaNacimiento: Date = Date()

    //Switch
    val estadoCivilWhen = "C"
    when(estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else ->{
            println("Se desconoce")
        }
    }
    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

    //Funciones (tipo de dato void = Unit)
    fun imprimirNombre(nombre: String): Unit {
        //template strings
        // "Bienvenido: " + nombre + " S
        println("Nombre: ${nombre}")
    }

    //Funciones complejas
    fun calcularSueldo(
        sueldo: Double, //requerido
        tasa: Double = 12.00, //Opcional(por defecto)
        bonoEspecial: Double? = null, //opcion null -> nullable
    ):Double{
        //Int ->  Int? (nullable)
        //String -> String? (nullable)
        //Date -> Date? (nullable)
        if(bonoEspecial == null){
            return sueldo * (100/tasa)
        }else{
            bonoEspecial.dec()
            return sueldo * (100/tasa) + bonoEspecial
        }
    }

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00)//parametros nombrados
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    val sumaUno = Suma(1, 1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma()*/


    //Tipos de arreglos

    //Arreglos Estaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(arregloEstatico)

    //Arreglos dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //for each -> unit
    //Iterrar un arreglo
    val respuestaForEaach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor actual: ${valorActual} ")
    }
    arregloDinamico.forEach { println(it) } //it es el elemento iterado

    arregloEstatico
        .forEachIndexed{ indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice ${indice}")
        }
    println(respuestaForEaach)

    //Operador MAP -> muta el arreglo
    //1) enviemos el nuevo valor de la iteracion
    //2) nos devuelve es un nuevo arreglo con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it+15 }

    //Operador Filter
    //1) devolver una expresion
    //2) nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            //expresion de condicion
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter {  it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //OR AND
    // OR -> ANY (alguno cumple)
    // AND  -> ALL (todos cumplen)
    val respuestaAny: Boolean = arregloDinamico
        .any{ valorActual: Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) //true

    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) //false

    //Operador REDUCE
    //nos ayuda acumulando un valor
    val respuestaReduce: Int = arregloDinamico
        .reduce{
                acumulado: Int, valorActual:  Int ->
            return@reduce (acumulado + valorActual)
    }
    println(respuestaReduce)
}

