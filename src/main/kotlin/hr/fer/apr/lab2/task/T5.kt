package hr.fer.apr.lab2.task

import hr.fer.apr.util.Matrix
import hr.fer.apr.lab2.algorithm.NelderMeadSimplex
import hr.fer.apr.lab2.function.F5
import java.util.*

fun randomCoordinate(): Double {
    val start = -50.0
    val end = 50.0

    return start + Random().nextDouble() * (end - start)
}

fun main(args: Array<String>) {
    for(i in (0..10)) {
        val x0 = Matrix(arrayOf(doubleArrayOf(randomCoordinate(), randomCoordinate()))).transpose()
        val f = F5()
        println("##### Nelder-Mead ###")

        println(NelderMeadSimplex.evaluate(f, x0))
        println("          NUMBER OF EVALUATIONS: ${f.numberOfCalls}")
        println()
    }
}