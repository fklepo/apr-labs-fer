package hr.fer.apr.lab3.function

import hr.fer.apr.util.Matrix
import hr.fer.apr.lab2.function.MultivariableFunction

abstract class DifferentiableFunction : MultivariableFunction() {

    var numberOfGradientCalls = 0
    var numberOfHessianCalls = 0

    fun numberOfGradientCalls(): Int {
        return numberOfGradientCalls
    }

    fun numberOfHessianCalls(): Int {
        return numberOfHessianCalls
    }

    abstract fun gradient(x: Matrix): Matrix

    abstract fun hessian(x: Matrix): Matrix
}