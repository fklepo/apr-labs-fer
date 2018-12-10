package hr.fer.apr.lab2.task

import hr.fer.apr.lab1.util.Matrix
import hr.fer.apr.lab2.algorithm.CoordinateAxisSearch
import hr.fer.apr.lab2.algorithm.HookeJeeves
import hr.fer.apr.lab2.algorithm.NelderMeadSimplex
import hr.fer.apr.lab2.function.MultivariableFunction

class FT1: MultivariableFunction {

    var numberOfCalls = 0

    override fun numberOfCalls(): Int {
        return numberOfCalls
    }

    override fun evaluate(x: Matrix): Double {
        numberOfCalls++
        return Math.pow(x[0] - 3, 2.0)
    }
}

fun main(args: Array<String>) {
    for(x0i in doubleArrayOf(10.0, 20.0, 60.0)) {
        val x0 = Matrix(arrayOf(doubleArrayOf(x0i)))
        val f1 = FT1()
        println()
        println("*************************")
        println("*** x0 = ${x0i} ***")
        println("*************************")
        println()
        println("##### GOLDEN CUT ###")
        println(CoordinateAxisSearch.evaluate(f1, x0))
        println("    NUMBER OF EVALUATIONS: ${f1.numberOfCalls}")
        println()
        val f2 = FT1()
        println("##### COORDINATE AXIS SEARCH ###")
        println(CoordinateAxisSearch.evaluate(f2, x0))
        println("    NUMBER OF EVALUATIONS: ${f2.numberOfCalls}")
        println()
        val f3 = FT1()
        println("##### NELDER MEAD SIMPLEX ###")
        println(NelderMeadSimplex.evaluate(f3, x0))
        println("    NUMBER OF EVALUATIONS: ${f3.numberOfCalls}")
        println()
        val f4 = FT1()
        println("##### HOOKE JEEVES ###")
        println(HookeJeeves.evaluate(f3, x0))
        println("    NUMBER OF EVALUATIONS: ${f3.numberOfCalls}")
    }
}