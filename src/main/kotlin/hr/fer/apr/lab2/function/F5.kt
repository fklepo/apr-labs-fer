package hr.fer.apr.lab2.function

import hr.fer.apr.lab1.util.Matrix

class F5: MultivariableFunction {

    var numberOfCalls = 0

    override fun evaluate(x: Matrix): Double {
        numberOfCalls++
        var squareSum = Math.sqrt((0..x.size().first - 1).map { Math.pow(x[it], 2.0) }.sum())
        return 0.5 + (Math.pow(Math.sin(Math.sqrt(squareSum)), 2.0) - 0.5) / Math.pow(1 + 0.001 * squareSum, 2.0)
    }

    override fun numberOfCalls(): Int {
        return numberOfCalls
    }
}