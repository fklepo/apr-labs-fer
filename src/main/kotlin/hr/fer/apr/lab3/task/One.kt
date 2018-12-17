package hr.fer.apr.lab3.task

import hr.fer.apr.lab3.algorithm.GradientDescent
import hr.fer.apr.lab3.function.F3
import hr.fer.apr.util.Matrix

fun main(args: Array<String>) {
    val f1 = F3()
    try {
        println(GradientDescent.evaluate(f1, Matrix(arrayOf(doubleArrayOf(0.0, 0.0))).transpose()))
    } catch (e: Exception) {
        println(e)
    }
    print("number of calls: " + f1.numberOfCalls)
    println()
    println("With golden cut:")
    println()
    val f2 = F3()
    try {
        println(GradientDescent.evaluate(f2, Matrix(arrayOf(doubleArrayOf(0.0, 0.0))).transpose(), useGoldenCut = true))
    } catch (e: Exception) {
        println(e)
    }
    print("number of calls: " + f2.numberOfCalls)
}
