package hr.fer.apr.lab2.function

import hr.fer.apr.lab1.util.Matrix

interface MultivariableFunction {

    fun numberOfCalls(): Int

    fun evaluate(x: Matrix): Double
}