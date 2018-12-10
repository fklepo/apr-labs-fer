package hr.fer.apr.lab2.task

import hr.fer.apr.lab1.util.Matrix
import hr.fer.apr.lab2.algorithm.NelderMeadSimplex
import hr.fer.apr.lab2.function.F1

fun main(args: Array<String>) {
    val x0s = listOf(
            Matrix(arrayOf(doubleArrayOf(0.5, 0.5))).transpose(),
            Matrix(arrayOf(doubleArrayOf(20.0, 20.0))).transpose()
    )

    for(x0 in x0s) {
        println("*************")
        println()
        println("   X0 = ${x0}")
        println()
        println("*************")
        println()

        for (d in (1..20)) {
            val f = F1()
            println("##### d = ${d} ###")
            println(NelderMeadSimplex.evaluate(f, x0, dX = d.toDouble()))
            println("    NUMBER OF EVALUATIONS: ${f.numberOfCalls}")
            println()
        }
    }
}