package hr.fer.apr.lab2.function

import hr.fer.apr.util.Matrix

abstract class MultivariableFunction {

    var numberOfCalls = 0

    fun numberOfCalls(): Int {
        return numberOfCalls
    }

    abstract operator fun invoke(x: Matrix): Double
}