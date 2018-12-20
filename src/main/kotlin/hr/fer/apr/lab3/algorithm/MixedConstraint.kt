package hr.fer.apr.lab3.algorithm

import hr.fer.apr.lab2.algorithm.HookeJeeves
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

        fun innerPoint(implicitConstraints: List<ImplicitConstraint>, t: Double = T): MultivariableFunction {
            return object : MultivariableFunction() {
                override fun invoke(x: Matrix): Double {
                    numberOfCalls++
                    return - implicitConstraints.map { t * it(x) }.filter { it < 0 }.sum()
                }
            }
        }

        public fun evaluate(f: DifferentiableFunction,
                            start: Matrix,
                            implicitConstraints: List<ImplicitConstraint>,
                            equalityConstraints: List<EqualityConstraint>,
                            dx: Double = HookeJeeves.D_X,
                            e: Double = E,
                            t: Double = T,
                            verbose: Boolean = false): Matrix {

            var prevX: Matrix = start
            var curX: Matrix = start
            if (implicitConstraints.map { it(start) }.any { it < 0 }) {
                curX = HookeJeeves.evaluate(innerPoint(implicitConstraints, t), start, dx)
                prevX = curX
                if (verbose) {
                    println("New start point:" + curX)
                }
            }
            var curT = t
            do {
                prevX = curX
                curX = HookeJeeves.evaluate(mixedFunction(f, implicitConstraints, equalityConstraints, t), prevX, dx)
                curT *= 10
            } while((0..curX.size().first-1).map { Math.abs(curX[it] - prevX[it]) }.any { it > e })

            return curX
        }

        fun evaluate(f: DifferentiableFunction,
                     propertiesPath: String,
                     implicitConstraints: List<ImplicitConstraint>,
                     equalityConstraints: List<EqualityConstraint>,
                     dx: Double): Matrix {
            val propertiesMap: Map<String, String> = InputParser.readPropertiesFile(propertiesPath)

            val start = InputParser.readVector(propertiesMap["start"]!!)
            val e = Optional.ofNullable(propertiesMap["e"]).toDouble().orElseGet { E }
            val t = Optional.ofNullable(propertiesMap["t"]).map { it.toDouble() }.orElseGet({ T })
            val dX = Optional.ofNullable(propertiesMap.get("dX")).toDouble().orElse(HookeJeeves.D_X)
            val verbose = Optional.ofNullable(propertiesMap["verbose"]).map { it.toBoolean() }.orElseGet({ false })

            return evaluate(f, start, implicitConstraints, equalityConstraints, dx, e, t, verbose)
        }
    }
}