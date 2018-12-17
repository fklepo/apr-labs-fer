package hr.fer.apr.lab2.algorithm

import hr.fer.apr.util.Matrix
import hr.fer.apr.lab2.function.MultivariableFunction
import hr.fer.apr.util.InputParser
import hr.fer.apr.util.toDouble
import java.util.*

val NM_E = 1e-6
val D_X0 = 1.0
val ALPHA = 1.0
val BETA = 0.5
val GAMMA = 2.0
val SIGMA = 0.5

class NelderMeadSimplex {

    companion object {
        public fun reflect(xC: Matrix, xH: Matrix, alpha: Double): Matrix {
            return xC * (1 + alpha) - xH * alpha
        }

        public fun expand(xC: Matrix, xR: Matrix, gamma: Double): Matrix {
            return xC * (1 - gamma) + xR * gamma
        }

        public fun contract(xC: Matrix, xH: Matrix, beta: Double): Matrix {
            return xC * (1 - beta) + xH * beta
        }

        private fun centroid(simplexPoints: List<Matrix>, h: Int): Matrix {
            val result = Matrix(arrayOf(DoubleArray(simplexPoints[0].size().first))).transpose()
            val centroidPoints = (0..simplexPoints.size-1).filter { it.compareTo(h) != 0 }.map { simplexPoints[it] }

            (0..result.size().first-1).forEach { d ->
                result[d] = centroidPoints.map { cp -> cp[d] }.average()
            }

            return result
        }

        public fun stoppageCriteriaMet(f: MultivariableFunction, simplexPoints: List<Matrix>, epsilon: Double): Boolean {
            val simplexPointsValuesByIndex = (0..simplexPoints.size-1)
                    .zip(simplexPoints)
                    .map { (i, xi) -> Pair(i, f.invoke(xi)) }

            val h = simplexPointsValuesByIndex.maxBy { it.second }!!.first
            val l = simplexPointsValuesByIndex.minBy { it.second }!!.first
            val xC = centroid(simplexPoints, h)
            val fXc = f.invoke(xC)

            return Math.sqrt((1.0 / simplexPoints.size) *
                    (0..simplexPoints.size - 1)
                            .map { Math.pow(f.invoke(simplexPoints[it]) - fXc, 2.0) }
                            .sum()) < epsilon
        }

        fun evaluate(f: MultivariableFunction,
                     x0: Matrix,
                     epsilon: Double = NM_E,
                     dX: Double = D_X0,
                     alpha: Double = ALPHA,
                     beta: Double = BETA,
                     gamma:Double = GAMMA,
                     sigma: Double = SIGMA,
                     verbose: Boolean = false): Matrix {

            val simplexPoints = mutableListOf(x0)
            for(i in (0..x0.size().first - 1)) {
                simplexPoints.add(x0 + Matrix.identity(x0.size().first).getColAsMatrix(i) * dX)
            }
            var h = 0
            var l = 0
            do {
                val simplexPointsValuesByIndex = (0..simplexPoints.size-1)
                        .zip(simplexPoints)
                        .map { (i, xi) -> Pair(i, f.invoke(xi)) }

                h = simplexPointsValuesByIndex.maxBy { it.second }!!.first
                l = simplexPointsValuesByIndex.minBy { it.second }!!.first
                val xC = centroid(simplexPoints, h)
                var fXc = f.invoke(xC)
                val xR = reflect(xC, simplexPoints[h], alpha)
                val fXR = f.invoke(xR)
                if(fXR.compareTo(f.invoke(simplexPoints[l])) < 0) {
                    val xE = expand(xC, xR, gamma)
                    if(f.invoke(xE).compareTo(f.invoke(simplexPoints[l])) < 0) {
                        simplexPoints[h] = xE
                    } else {
                        simplexPoints[h] = xR
                    }
                } else {
                    if((0..simplexPoints.size - 1).filter { it.compareTo(h) != 0 }
                            .all { fXR.compareTo(f.invoke(simplexPoints[it])) > 0 }) {
                        if(fXR.compareTo(f.invoke(simplexPoints[h])) < 0) {
                            simplexPoints[h] = xR
                        }
                        val xK = contract(xC, simplexPoints[h], beta)
                        if(f.invoke(xK).compareTo(f.invoke(simplexPoints[h])) < 0) {
                            simplexPoints[h] = xK
                        } else {
                            (0..simplexPoints.size - 1)
                                    .filter { it.compareTo(l) != 0 }
                                    .forEach { simplexPoints[it] = (simplexPoints[it] + simplexPoints[l]) * SIGMA }
                        }
                    } else {
                        simplexPoints[h] = xR
                    }
                }
            } while(!stoppageCriteriaMet(f, simplexPoints, epsilon))

            return simplexPoints[l]
        }

        fun evaluate(f: MultivariableFunction, propertiesPath: String? = null, verbose: Boolean = false): Matrix {
            var propertiesMap: Map<String, String> = HashMap<String, String>()
            if (propertiesPath != null) {
                propertiesMap = InputParser.readPropertiesFile(propertiesPath)
            }

            val x0 = InputParser.readVector(propertiesMap.get("x0_i")!!)
            val epsilon = Optional.ofNullable(propertiesMap["e"]).toDouble().orElse(NM_E)
            val dX = Optional.ofNullable(propertiesMap["dX"]).toDouble().orElse(D_X0)
            val alpha = Optional.ofNullable(propertiesMap["alpha"]).toDouble().orElse(ALPHA)
            val beta = Optional.ofNullable(propertiesMap["beta"]).toDouble().orElse(BETA)
            val gamma = Optional.ofNullable(propertiesMap["gamma"]).toDouble().orElse(GAMMA)
            val sigma = Optional.ofNullable(propertiesMap["sigma"]).toDouble().orElse(SIGMA)

            return evaluate(f, x0, epsilon, dX, alpha, beta, gamma, sigma, verbose)
        }
    }
}
