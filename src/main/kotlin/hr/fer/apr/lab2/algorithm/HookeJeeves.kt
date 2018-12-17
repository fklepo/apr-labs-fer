package hr.fer.apr.lab2.algorithm

import hr.fer.apr.util.Matrix
import hr.fer.apr.lab2.function.MultivariableFunction
import hr.fer.apr.util.InputParser
import hr.fer.apr.util.toDouble
import java.util.*

val D_X = 1.0
val HE_X = 1e-6

class HookeJeeves {
    companion object {
        private fun explore(f: MultivariableFunction, xP: Matrix, dX: Double): Matrix {
            val x = xP.copy()
            for(i in (0..xP.size().first - 1)) {
                val p = f.invoke(x)
                x[i] += dX
                var n = f.invoke(x)

                if(n.compareTo(p) > 0) {
                    x[i] -= 2 * dX
                    n = f.invoke(x)
                    if(n.compareTo(p) > 0) {
                        x[i] += dX
                    }
                }
            }
            return x
        }

        fun evaluate(f: MultivariableFunction, propertiesPath: String? = null, verbose: Boolean = false): Matrix {
            var propertiesMap: Map<String, String> = HashMap<String, String>()
            if (propertiesPath != null) {
                propertiesMap = InputParser.readPropertiesFile(propertiesPath)
            }
            val x0 = InputParser.readVector(propertiesMap.get("x0_i")!!)
            val dX = Optional.ofNullable(propertiesMap.get("dX")).toDouble().orElse(D_X)
            val xB = x0.copy()
            val xP = x0.copy()

            return evaluate(f, x0, dX, xB, xP, verbose)
        }

        fun evaluate(f: MultivariableFunction, x0: Matrix, dX: Double = D_X, xB: Matrix = x0.copy(), xP: Matrix = x0.copy(), verbose: Boolean = false): Matrix {
            var xPc = xP
            var xBc = xB
            var step = 0
            var dXc = dX
            do {
                val xN = explore(f, xPc, dXc)
                if (verbose) {
                    println("${step++}: xB = ${xBc}, xP = ${xPc}, xN = ${xN}, f(xN) < f(xB) = ${f.invoke(xN).compareTo(f.invoke(xBc)) < 0}")
                }
                if(f.invoke(xN).compareTo(f.invoke(xBc)) < 0) {
                    xPc = (xN * 2.0) - xBc
                    xBc = xN.copy()
                } else {
                    dXc /= 2
                    xPc = xBc.copy()
                }
            } while(dXc.compareTo(0.25) > 0)

            return xBc
        }
    }
}