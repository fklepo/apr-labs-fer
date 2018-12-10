package hr.fer.apr.lab2.task

import hr.fer.apr.lab1.util.Matrix
import hr.fer.apr.lab2.algorithm.CoordinateAxisSearch
import hr.fer.apr.lab2.algorithm.HookeJeeves
import hr.fer.apr.lab2.algorithm.NelderMeadSimplex
import hr.fer.apr.lab2.function.*

fun main(args: Array<String>) {
    val functionsContextes = listOf(
                    Triple("f1", { F1() }, Matrix(arrayOf(doubleArrayOf(-1.9, 2.0))).transpose()),
                    Triple("f2", { F2() }, Matrix(arrayOf(doubleArrayOf(0.1, 0.3))).transpose()),
                    Triple("f3", { F3() }, Matrix(arrayOf(doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0))).transpose()),
                    Triple("f4", { F4() }, Matrix(arrayOf(doubleArrayOf(5.1, 1.1))).transpose())
    )

    for((functionName, functionProducer, x0) in functionsContextes) {
        val f1 = functionProducer()
        println("********* ${functionName}  *************")
        println()
        println("##### NELDER MEAD SIMPLEX ###")
        println(NelderMeadSimplex.evaluate(f1, x0))
        println("    NUMBER OF EVALUATIONS: ${f1.numberOfCalls()}")
        println()
        val f2 = functionProducer()
        println("##### HOOKE JEEVES ###")
        println(HookeJeeves.evaluate(f2, x0))
        println("    NUMBER OF EVALUATIONS: ${f2.numberOfCalls()}")
        val f3 = functionProducer()
        println("##### COORDINATE AXIS SEARCH ###")
        println(CoordinateAxisSearch.evaluate(f3, x0))
        println("    NUMBER OF EVALUATIONS: ${f3.numberOfCalls()}")
        println()
        println()
    }
}