package hr.fer.apr.lab2.task

import hr.fer.apr.lab1.util.Matrix
import hr.fer.apr.lab2.algorithm.HookeJeeves
import hr.fer.apr.lab2.algorithm.NelderMeadSimplex
import hr.fer.apr.lab2.function.F4

fun main(args: Array<String>) {
    val x0 = Matrix(arrayOf(doubleArrayOf(5.0, 5.0))).transpose()
    val f1 = F4()
    println("##### Hooke-Jeeves ###")

    println(HookeJeeves.evaluate(f1, x0))
    println("          NUMBER OF EVALUATIONS: ${f1.numberOfCalls}")
    println()
    val f2 = F4()
    println("##### Nelder-Mead ###")
    println(NelderMeadSimplex.evaluate(f2, x0))
    println("    NUMBER OF EVALUATIONS: ${f2.numberOfCalls}")

}