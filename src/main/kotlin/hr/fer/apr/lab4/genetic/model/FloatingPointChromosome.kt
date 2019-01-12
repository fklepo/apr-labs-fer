package hr.fer.apr.lab4.genetic.model

import hr.fer.apr.lab4.genetic.function.GoalFunction
import hr.fer.apr.util.Matrix
import hr.fer.apr.lab4.util.Either
import hr.fer.apr.lab4.util.Right

class FloatingPointChromosome: Chromosome {

    val intervalMinimum: Double
    val intervalMaximum: Double
    val value: Matrix

    constructor(n: Int,
                intervalMinimum: Double,
                intervalMaximum: Double,
                elementGenerator: (i: Int) -> Double =
                    { intervalMinimum + Math.random() * (intervalMaximum - intervalMinimum) }):
            super(ChromosomeRepresentation.FLOATING_POINT) {

        this.intervalMinimum = intervalMinimum
        this.intervalMaximum = intervalMaximum
        this.value = Matrix(arrayOf((1..n).map { elementGenerator(it) }.toDoubleArray())).transpose()
    }

    constructor(intervalMinimum: Double,
                intervalMaximum: Double,
                value: Matrix):
            super(ChromosomeRepresentation.FLOATING_POINT) {

        assert(value.size().first >= 1)
        assert(value.size().second == 1)

        this.intervalMinimum = intervalMinimum
        this.intervalMaximum = intervalMaximum
        this.value = value.copy()
    }

    override fun value(): Matrix {
        return value
    }

    override fun crossover(
            that: Chromosome,
            f: GoalFunction,
            crossoverTypeEither: Either<BinaryCrossoverType, FloatingPointCrossoverType>): Chromosome {

        crossoverTypeEither as Right<FloatingPointCrossoverType>
        that as FloatingPointChromosome

        return crossoverTypeEither.value(this, that, f)
    }

    override fun mutate(probability: Double, mutationType: Either<BinaryMutationType, FloatingPointMutationType>): Chromosome {
        mutationType as Right<FloatingPointMutationType>
        return mutationType.value(this, probability)
    }

    override fun toString(): String {
        return this.value.toString()
    }
}