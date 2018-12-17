package hr.fer.apr.lab3.algorithm

import hr.fer.apr.lab2.algorithm.GoldenCut
import hr.fer.apr.util.Matrix
import hr.fer.apr.util.InputParser
import hr.fer.apr.util.toDouble
import hr.fer.apr.lab3.function.DifferentiableFunction
import java.lang.RuntimeException
import java.util.Optional

class NewtonRhapson {

    companion object {

        private const val E = 1e-6
        private const val MAX_NO_PROGRESS_COUNT = 100

        fun evaluate(f: DifferentiableFunction, start: Matrix, e: Double = E, useGoldenCut: Boolean = false, verbose: Boolean = false): Matrix {
            var curArg = start
            var curValue: Double? = null
            var curMinimum: Double? = null
            var curMinimumArg: Matrix? = null
            var iterationsSinceLastMinimum = 0

            do {
                val direction = f.hessian(curArg).inverse()!! * f.gradient(curArg)
                val v = direction / direction.norm()
                if (verbose) {
                    println("cur_arg:")
                    println(curArg)
                    println()
                    println("f(cur_arg):")
                    println(f.invoke(curArg))
                    println()
                    if (useGoldenCut) {
                        println("v:")
                        println(v)
                    } else {
                        println("direction:")
                        println(direction)
                    }
                    println()
                    println("====================")
                    println()
                }
                if (!useGoldenCut) {
                    curArg -= direction
                } else {
                    val l = GoldenCut.evaluate({ l ->  f.invoke(curArg + v * l)}, 0.0).let { it.first / it.second }
                    curArg += v * l
                }
                curValue = f.invoke(curArg)
                if (curMinimum == null || curMinimum.compareTo(curValue) > 0) {
                    curMinimum = curValue
                    curMinimumArg = curArg
                    iterationsSinceLastMinimum = 0
                } else {
                    iterationsSinceLastMinimum++
                }

                if (iterationsSinceLastMinimum >= MAX_NO_PROGRESS_COUNT) {
                    throw RuntimeException("Newton Rhapson diverged, last minimum was: " + curMinimum
                            + " for argument " + curMinimumArg)
                }
            } while (direction.norm() > E)

            return curArg
        }

        fun evaluate(f: DifferentiableFunction, propertiesPath: String): Matrix {
            val propertiesMap: Map<String, String> = InputParser.readPropertiesFile(propertiesPath)

            val e = Optional.ofNullable(propertiesMap["e"]).toDouble().orElseGet { E }
            val start = InputParser.readVector(propertiesMap["start"]!!)
            val useGoldenCut = Optional.ofNullable(propertiesMap["useGoldenCut"]).map { it.toBoolean() }.orElseGet({ false })
            val verbose = Optional.ofNullable(propertiesMap["verbose"]).map { it.toBoolean() }.orElseGet({ false })

            return evaluate(f, start, e, useGoldenCut, verbose)
        }
    }
}
