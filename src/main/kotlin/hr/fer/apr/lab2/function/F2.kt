package hr.fer.apr.lab2.function

import hr.fer.apr.lab1.util.Matrix

class F2: MultivariableFunction {

    var numberOfCalls = 0

    override fun evaluate(x: Matrix): Double {
        numberOfCalls++
        return Math.pow(x[0] - 4, 2.0) + 4 * Math.pow(x[1] - 2, 2.0)
    }

    override fun numberOfCalls(): Int {
        return numberOfCalls
    }
}