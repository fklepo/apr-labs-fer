package hr.fer.apr.lab2.function

import hr.fer.apr.lab1.util.Matrix

class F1: MultivariableFunction {

    var numberOfCalls = 0

    override fun evaluate(x: Matrix): Double {
        numberOfCalls++
        return 100 * Math.pow(x[1] - Math.pow(x[0], 2.0), 2.0) + Math.pow(1 - x[0], 2.0)
    }

    override fun numberOfCalls(): Int {
        return numberOfCalls
    }
}