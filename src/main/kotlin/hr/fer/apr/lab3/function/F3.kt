package hr.fer.apr.lab3.function

import hr.fer.apr.util.Matrix

class F3: DifferentiableFunction() {

    override fun invoke(x: Matrix): Double {
        numberOfCalls++
        return Math.pow(x[0] - 2, 2.0) + Math.pow(x[1] + 3, 2.0)
    }

    override fun gradient(x: Matrix): Matrix {
        numberOfGradientCalls++
        val dX1 = 2 * (x[0] - 2)
        val dX2 = 2 * (x[1] + 3)
        return Matrix(arrayOf(doubleArrayOf(dX1, dX2))).transpose()
    }

    override fun hessian(x: Matrix): Matrix {
        numberOfHessianCalls++
        return Matrix(
                arrayOf(
                        doubleArrayOf(2.0, 0.0),
                        doubleArrayOf(2.0, 0.0)
                )
        )
    }
}