package hr.fer.apr.lab4.function

import hr.fer.apr.lab2.function.F5
import hr.fer.apr.lab4.genetic.function.GoalFunction
import hr.fer.apr.lab4.genetic.model.Chromosome

class F6: F5(), GoalFunction {

    override fun invoke(x: Chromosome): Double {
        return this.invoke(x.value())
    }
}