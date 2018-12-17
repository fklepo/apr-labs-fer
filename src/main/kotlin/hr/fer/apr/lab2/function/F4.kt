package hr.fer.apr.lab2.function

import hr.fer.apr.util.Matrix

class F4: MultivariableFunction() {

    override fun invoke(x: Matrix): Double {
        numberOfCalls++
        return Math.abs((x[0] - x[1]) * (x[0] + x[1])) + Math.sqrt(Math.pow(x[0], 2.0) + Math.pow(x[1], 2.0))
    }
}