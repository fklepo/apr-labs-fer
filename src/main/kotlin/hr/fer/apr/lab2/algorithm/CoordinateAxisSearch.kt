package hr.fer.apr.lab2.algorithm

import hr.fer.apr.lab1.util.Matrix
import hr.fer.apr.lab2.util.InputParser
import hr.fer.apr.lab2.function.MultivariableFunction
import java.util.*

val CAS_E = 1e-6

class CoordinateAxisSearch {
    companion object {
        fun evaluate(
                f: MultivariableFunction,
                x0: Matrix,
                e: Matrix =
                    Matrix(arrayOf(List(x0.size().first, { i -> CAS_E }).toDoubleArray())).transpose(),
                verbose: Boolean = false): Matrix {

            if(x0.size().first.compareTo(e.size().first) != 0) {
                throw IllegalArgumentException("x0 and e are not of same size")
            }

            var x = x0.copy()
            do {
                val xS = x.copy()
                for(i in (0..x.size().first-1)) {
                    val identityVectorI = Matrix.identity(x.size().first).getColAsMatrix(i)
                    val lMinTuple = GoldenCut.evaluate(
                            { l -> f.evaluate(x + identityVectorI * l) },
                            xS[i],
                            verbose=verbose)
                    val lMin = lMinTuple.let { (it.first + it.second) / 2 }
                    x = x + identityVectorI * lMin
                }
            } while((0..x.size().first-1).any { Math.abs(x[it] - xS[it]).compareTo(e[it]) > 0 })

            return x
        }

        fun evaluate(f: MultivariableFunction, propertiesPath: String? = null): Matrix {
            var propertiesMap: Map<String, String> = HashMap<String, String>()
            if (propertiesPath != null) {
                propertiesMap = InputParser.readPropertiesFile(propertiesPath)
            }

            val x0 = InputParser.readMultipleDoubleArguments(propertiesMap["x0"]!!).get()
            val e = Optional.ofNullable(propertiesMap["e"])
                        .map { InputParser.readVector(it) }
                        .orElse(
                                Matrix(arrayOf(
                                        List(x0.size().first, { i -> CAS_E }).toDoubleArray())
                                ).transpose())

            return evaluate(f, x0, e)
        }
    }
}
