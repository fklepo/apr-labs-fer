package hr.fer.apr.lab5.task

import hr.fer.apr.lab5.algorithm.Trapeze
import hr.fer.apr.util.Matrix

fun main(args: Array<String>) {
    val a = Matrix(
            arrayOf(
                    doubleArrayOf(0.0, 1.0),
                    doubleArrayOf(-1.0, 0.0)
            )
    )
    val b = Matrix(arrayOf(doubleArrayOf(0.0, 0.0), doubleArrayOf(0.0, 0.0))).transpose()
    val x0 = Matrix(arrayOf(doubleArrayOf(1.0, 1.0))).transpose()
    Trapeze.evaluate(a, b, x0, 0.1, 20.0, verbose = true, outputFilePath = "trapeze_3.csv", verboseEveryNIter = 1)
}
