package hr.fer.apr.lab2.function

import hr.fer.apr.util.Matrix

open class F5: MultivariableFunction() {

    override fun invoke(x: Matrix): Double {
        numberOfCalls++
        var squareSum = Math.sqrt((0..x.size().first - 1).map { Math.pow(x[it], 2.0) }.sum())
        return 0.5 + (Math.pow(Math.sin(Math.sqrt(squareSum)), 2.0) - 0.5) / Math.pow(1 + 0.001 * squareSum, 2.0)
    }
}