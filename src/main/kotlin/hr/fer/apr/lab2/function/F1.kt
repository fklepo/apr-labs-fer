package hr.fer.apr.lab2.function

import hr.fer.apr.util.Matrix

class F1: MultivariableFunction() {

    override fun invoke(x: Matrix): Double {
        numberOfCalls++
        return 100 * Math.pow(x[1] - Math.pow(x[0], 2.0), 2.0) + Math.pow(1 - x[0], 2.0)
    }
}