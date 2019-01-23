package hr.fer.apr.lab5.algorithm

import hr.fer.apr.util.Matrix
import java.io.File

object Trapeze {

    fun evaluate(a: Matrix, b: Matrix, x0: Matrix,
                 t: Double, tMax: Double,
                 r: (Double) -> Matrix = { Matrix(arrayOf(doubleArrayOf(0.0, 0.0))).transpose() },
                 outputFilePath: String = "trapeze.out",
                 verbose: Boolean = false, verboseEveryNIter: Int = 10) {

        val outputFile = File(outputFilePath)
        outputFile.writeText("")
        val inverseHelper = (Matrix.identity(a.size().first) - a*(t/2)).inverse()
        var iteration = 1
        var curT = 0.0
        var curX = x0

        while(curT < tMax) {
            curX = inverseHelper * (Matrix.identity(a.size().first) + a * (t/2)) * curX + (inverseHelper * b * (t/2)) * r(t)
            if (verbose && iteration % verboseEveryNIter == 0) {
                outputFile.appendText("${curT},${curX[0]},${curX[1]}\n")
                println("${curT},${curX[0]},${curX[1]}\n")
            }
            curT += t
            iteration++
        }
    }
}