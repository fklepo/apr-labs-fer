package hr.fer.apr.lab4.function

import hr.fer.apr.lab2.function.F3
import hr.fer.apr.lab4.genetic.function.GoalFunction
import hr.fer.apr.lab4.genetic.model.Chromosome

class F3: F3(), GoalFunction {

    override fun invoke(x: Chromosome): Double {
        return this.invoke(x.value())
    }
}