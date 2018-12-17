package hr.fer.apr.lab3.algorithm

import hr.fer.apr.lab2.function.MultivariableFunction
import hr.fer.apr.lab3.function.DifferentiableFunction
import hr.fer.apr.lab3.util.EqualityConstraint
import hr.fer.apr.lab3.util.ImplicitConstraint
import hr.fer.apr.util.InputParser
import hr.fer.apr.util.Matrix
import hr.fer.apr.util.toDouble
import java.util.*

class MixedConstraint {
    companion object {

        private const val E = 1e-6
        private const val T = 1.0

        private fun mixedFunction(
                                  f: DifferentiableFunction,
                                  implicitConstraints: List<ImplicitConstraint>,
                                  equalityConstraints: List<EqualityConstraint>,
                                  t: Double = T): MultivariableFunction {
            val r = 1 / t

            return object : MultivariableFunction() {
                override operator fun invoke(x: Matrix): Double {
                    numberOfCalls++
                    return f(x) - r * implicitConstraints.map { Math.log(it(x)) }.sum() +
                                  t * equalityConstraints.map { Math.pow(it(x), 2.0) }.sum()
                }
            }
        }

        public fun evaluate(f: DifferentiableFunction,
                            start: Matrix,
                            implicitConstraints: List<ImplicitConstraint>,
                            equalityConstraints: List<EqualityConstraint>,
                            e: Double = E,
                            t: Double = T,
                            verbose: Boolean = false): Matrix {

            val xPrev: Matrix = start
            val xCur: Matrix = start
            do {

            } while((0..xCur.size().first-1).map { Math.abs(xCur[it] - xPrev[it]) }.any { it > e })

            return xCur
        }

        fun evaluate(f: DifferentiableFunction,
                     propertiesPath: String,
                     implicitConstraints: List<ImplicitConstraint>,
                     equalityConstraints: List<EqualityConstraint>): Matrix {
            val propertiesMap: Map<String, String> = InputParser.readPropertiesFile(propertiesPath)

            val start = InputParser.readVector(propertiesMap["start"]!!)
            val e = Optional.ofNullable(propertiesMap["e"]).toDouble().orElseGet { E }
            val t = Optional.ofNullable(propertiesMap["t"]).map { it.toDouble() }.orElseGet({ T })
            val verbose = Optional.ofNullable(propertiesMap["verbose"]).map { it.toBoolean() }.orElseGet({ false })

            return evaluate(f, start, implicitConstraints, equalityConstraints, e, t, verbose)
        }
    }

}