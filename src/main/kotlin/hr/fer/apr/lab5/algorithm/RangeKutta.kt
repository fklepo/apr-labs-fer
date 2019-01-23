package hr.fer.apr.lab5.algorithm

import hr.fer.apr.util.Matrix
import java.io.File

object RangeKutta {

    fun evaluate(a: Matrix, b: Matrix, x0: Matrix,
                 t: Double, tMax: Double,
                 r: (Double) -> Matrix = { Matrix(arrayOf(doubleArrayOf(0.0, 0.0))).transpose() },
                 outputFilePath: String = "range_kutta.out",
                 verbose: Boolean = false, verboseEveryNIter: Int = 10) {

        val outputFile = File(outputFilePath)
        outputFile.writeText("")
        val inverseHelper = (Matrix.identity(a.size().first) - a * (t / 2)).inverse()
        var iteration = 1
        var curT = 0.0
        var curX = x0

        while (curT < tMax) {
            val m1 = a * curX + b * r(curT)
            val m2 = a * (curX + m1 * 0.5 * t) + b * r(curT + 0.5 * t)
            val m3 = a * (curX + m2 * 0.5 * t) + b * r(curT + 0.5 * t)
            val m4 = a * (curX + m3 * t) + b * r(curT + t)

            curX += (m1 + m2 * 2.0 + m3 * 2.0 + m4) * (t / 6)

            if (verbose && iteration % verboseEveryNIter == 0) {
                outputFile.appendText("${curT},${curX[0]},${curX[1]}\n")
                println("${curT},${curX[0]},${curX[1]}\n")
            }
            curT += t
            iteration++
        }
    }
}