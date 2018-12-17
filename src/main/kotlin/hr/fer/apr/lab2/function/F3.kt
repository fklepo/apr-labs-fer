package hr.fer.apr.lab2.function

import hr.fer.apr.util.Matrix

class F3: MultivariableFunction() {

    override fun invoke(x: Matrix): Double {
        numberOfCalls++
        return (0..x.size().first - 1).map { Math.pow(x[it] - (it + 1), 2.0) }.sum()
    }
}