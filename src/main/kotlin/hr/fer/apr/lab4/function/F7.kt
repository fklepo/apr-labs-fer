package hr.fer.apr.lab4.function

import hr.fer.apr.lab2.function.MultivariableFunction
import hr.fer.apr.lab4.genetic.function.GoalFunction
import hr.fer.apr.lab4.genetic.model.Chromosome
import hr.fer.apr.util.Matrix

class F7: MultivariableFunction(), GoalFunction {

    override fun invoke(x: Matrix): Double {
        numberOfCalls++
        val squaresSum = (0..x.size().first-1).map { Math.pow(x[it], 2.0) }.sum()
        return Math.pow(squaresSum, 0.25) * (1 + Math.pow(Math.sin(50 * Math.pow(squaresSum, 0.1)), 2.0))
    }

    override fun invoke(x: Chromosome): Double {
        return this.invoke(x.value())
    }
}