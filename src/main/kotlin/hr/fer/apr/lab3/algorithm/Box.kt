package hr.fer.apr.lab3.algorithm

import hr.fer.apr.lab2.algorithm.NelderMeadSimplex
import hr.fer.apr.lab3.function.DifferentiableFunction
import hr.fer.apr.lab3.util.ExplicitConstraint
import hr.fer.apr.lab3.util.ImplicitConstraint
import hr.fer.apr.util.InputParser
import hr.fer.apr.util.Matrix
import hr.fer.apr.util.toDouble
import java.lang.IllegalArgumentException
import java.util.*

class Box {
    companion object {

        private const val E = 1e-6
        private const val ALPHA = 1.3

        private fun centroid(xs: List<Matrix>, f: DifferentiableFunction): Matrix {
            if (xs.size == 1) {
                return xs[0]
            }

            val h = (0..xs.size-1).zip(xs).maxBy { f.invoke(it.second) }!!.first
            return (0..xs.size - 1)
                    .filter { it != h }
                    .map { xs.get(it) }
                    .reduce { m1, m2 -> m1 + m2 } / (xs.size - 1.0)
        }

        private fun satisfyImplicitConstraints(constrs: List<ImplicitConstraint>, x: Matrix, xC: Matrix): Matrix {
            var xI = x
            while (constrs.any { it(xI) < 0 }) {
                xI = (xC + xI) * 0.5
            }
            return xI
        }

        public fun evaluate(f: DifferentiableFunction,
                             start: Matrix,
                             explicitConstraint: ExplicitConstraint,
                             implicitConstraints: List<ImplicitConstraint>,
                             e: Double = E,
                             alpha: Double = ALPHA,
                             verbose: Boolean = false): Pair<Matrix, Double> {

            if (!explicitConstraint.satisfies(start) ||
                    implicitConstraints.any { it(start) < 0 }) {
                throw IllegalArgumentException("Invalid start point " + start)
            }

            val n = start.size().first
            val simplexPoints: MutableList<Matrix> = mutableListOf()
            var xC = start
            for (t in (1..2*n)) {
                val elems = (0..n - 1)
                        .map { explicitConstraint.lowerBound(it) + Random().nextDouble() * explicitConstraint.upperBound(it) }
                        .toDoubleArray()
                simplexPoints.add(satisfyImplicitConstraints(implicitConstraints, Matrix(arrayOf(elems)).transpose(), xC))
                xC = centroid(simplexPoints, f)
            }

            var h = 0
            var h2 = 0
            var l = 0
            do {
                val maxIndexes = simplexPoints
                        .map { f(it) }
                        .zip((0..2*n - 1))
                        .sortedBy { -it.first }
                        .map { it.second }
                h = maxIndexes.first()
                h2 = maxIndexes[1]
                l = maxIndexes.last()
                xC = centroid(simplexPoints, f)

                var reflected = NelderMeadSimplex.reflect(xC, simplexPoints[h], alpha)
                explicitConstraint.conform(reflected)
                reflected = satisfyImplicitConstraints(implicitConstraints, reflected, xC)

                if (f(reflected) > f(simplexPoints[h2])) {
                    reflected = (reflected + xC) * 0.5
                }
                simplexPoints[h] = reflected
                if (verbose) {
                    println("Centroid points distance: " + Math.sqrt((1.0 / simplexPoints.size) *
                            (0..simplexPoints.size - 1)
                                    .map { Math.pow(f.invoke(simplexPoints[it]) - f(xC), 2.0) }
                                    .sum())
                    )
                }
            } while(!NelderMeadSimplex.stoppageCriteriaMet(f, simplexPoints, e))

            return Pair(simplexPoints[l], f(simplexPoints[l]))
        }

        fun evaluate(f: DifferentiableFunction,
                     propertiesPath: String,
                     explicitConstraint: ExplicitConstraint,
                     implicitConstraints: List<ImplicitConstraint>): Pair<Matrix, Double> {
            val propertiesMap: Map<String, String> = InputParser.readPropertiesFile(propertiesPath)

            val start = InputParser.readVector(propertiesMap["start"]!!)
            val e = Optional.ofNullable(propertiesMap["e"]).toDouble().orElseGet { E }
            val alpha = Optional.ofNullable(propertiesMap["alpha"]).map { it.toDouble() }.orElseGet({ ALPHA })
            val verbose = Optional.ofNullable(propertiesMap["verbose"]).map { it.toBoolean() }.orElseGet({ false })

            return evaluate(f, start, explicitConstraint, implicitConstraints, e, alpha, verbose)
        }
    }
}