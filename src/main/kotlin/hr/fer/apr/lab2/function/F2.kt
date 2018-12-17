package hr.fer.apr.lab2.function

import hr.fer.apr.util.Matrix

class F2: MultivariableFunction() {

    override fun invoke(x: Matrix): Double {
        numberOfCalls++
        return Math.pow(x[0] - 4, 2.0) + 4 * Math.pow(x[1] - 2, 2.0)
    }
}