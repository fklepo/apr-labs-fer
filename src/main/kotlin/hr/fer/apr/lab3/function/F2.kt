package hr.fer.apr.lab3.function

import hr.fer.apr.util.Matrix

class F2: DifferentiableFunction() {

    override fun invoke(x: Matrix): Double {
        numberOfCalls++
        return Math.pow(x[0] - 4, 2.0) + 4 * Math.pow(x[1] - 2, 2.0)
    }

    override fun gradient(x: Matrix): Matrix {
        numberOfGradientCalls++
        val dX1 = 2 * (x[0] - 4)
        val dX2 = 8 * (x[1] - 2)
        return Matrix(arrayOf(doubleArrayOf(dX1, dX2))).transpose()
    }

    override fun hessian(x: Matrix): Matrix {
        numberOfHessianCalls++
        return Matrix(
                arrayOf(
                    doubleArrayOf(2.0, 0.0),
                    doubleArrayOf(8.0, 0.0)
                )
        )
    }
}