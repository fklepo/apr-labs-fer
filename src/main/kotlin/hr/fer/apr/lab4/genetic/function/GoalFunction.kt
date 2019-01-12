package hr.fer.apr.lab4.genetic.function

import hr.fer.apr.lab4.genetic.model.Chromosome

interface GoalFunction {

    operator fun invoke(x: Chromosome): Double
}