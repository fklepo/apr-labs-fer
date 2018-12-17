package hr.fer.apr.lab3.function

import hr.fer.apr.util.Matrix

class F1: DifferentiableFunction() {

    override fun invoke(x: Matrix): Double {
        numberOfCalls++
        return 100 * Math.pow(x[1] - Math.pow(x[0], 2.0), 2.0) + Math.pow(1 - x[0], 2.0)
    }

    override fun gradient(x: Matrix): Matrix {
        numberOfGradientCalls++
        val dX1 = 100 * 2 * (-2) * x[0] * (x[1] - Math.pow(x[0], 2.0)) - 2 * (1 - x[0])
        val dX2 = 100 * 2 * (x[1] - Math.pow(x[0], 2.0))
        return Matrix(arrayOf(doubleArrayOf(dX1, dX2))).transpose()
    }

    override fun hessian(x: Matrix): Matrix {
        numberOfHessianCalls++
        return Matrix(
                arrayOf(
                    doubleArrayOf(800 * x[0] + 2, -400.0),
                    doubleArrayOf(200.0, -400 * x[0])
                )
        )
    }
}