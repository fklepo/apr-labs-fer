package hr.fer.apr.lab2.function

import hr.fer.apr.lab1.util.Matrix

class F4: MultivariableFunction {

    var numberOfCalls = 0

    override fun evaluate(x: Matrix): Double {
        numberOfCalls++
        return Math.abs((x[0] - x[1]) * (x[0] + x[1])) + Math.sqrt(Math.pow(x[0], 2.0) + Math.pow(x[1], 2.0))
    }

    override fun numberOfCalls(): Int {
        return numberOfCalls
    }
}