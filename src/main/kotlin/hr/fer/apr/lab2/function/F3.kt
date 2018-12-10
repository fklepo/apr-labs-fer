package hr.fer.apr.lab2.function

import hr.fer.apr.lab1.util.Matrix

class F3: MultivariableFunction {

    var numberOfCalls = 0

    override fun evaluate(x: Matrix): Double {
        numberOfCalls++
        return (0..x.size().first - 1).map { Math.pow(x[it] - (it + 1), 2.0) }.sum()
    }

    override fun numberOfCalls(): Int {
        return numberOfCalls
    }
}