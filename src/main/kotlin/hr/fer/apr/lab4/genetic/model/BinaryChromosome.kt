package hr.fer.apr.lab4.genetic.model

import hr.fer.apr.lab4.genetic.function.GoalFunction
import hr.fer.apr.util.Matrix
import hr.fer.apr.lab4.util.Either
import hr.fer.apr.lab4.util.Left

class BinaryChromosome: Chromosome {

    val intervalMinimum: Double

    val intervalMaximum: Double
    val value: List<List<Boolean>>
    constructor(n: Int,
                precision: Int,
                intervalMinimum: Double,
                intervalMaximum: Double,
                generator: () -> Boolean = { Math.random().compareTo(0.5) > 0 }):
            super(ChromosomeRepresentation.BINARY) {

        this.intervalMinimum = intervalMinimum
        this.intervalMaximum = intervalMaximum

        val chromosomeLength =
                Math.ceil(Math.log10(1 + (intervalMaximum - intervalMinimum) *
                        Math.pow(10.0, precision.toDouble())) / Math.log10(2.0)).toInt()
        this.value = (1..n).map { (1..chromosomeLength).map { generator() }.toList() }
    }

    constructor(intervalLow: Double,
                intervalHigh: Double,
                value: List<List<Boolean>>):
            super(ChromosomeRepresentation.BINARY) {

        this.intervalMinimum = intervalLow
        this.intervalMaximum = intervalHigh
        this.value = value
    }

    private fun binaryValue(unitBinaryValue: List<Boolean>): Int {
        return unitBinaryValue
                .reversed()
                .zip((0..unitBinaryValue.size - 1).map { Math.pow(2.0, it.toDouble()).toInt() })
                .filter { it.first }
                .map { it.second }
                .sum()
    }

    private fun value(unitBinaryValue: List<Boolean>): Double {
        return intervalMinimum +
                binaryValue(unitBinaryValue) *
                (intervalMaximum - intervalMinimum) /
                (Math.pow(2.0, unitBinaryValue.size.toDouble()) - 1)
    }

    override fun value(): Matrix {
        return Matrix(arrayOf(this.value.map({ value(it) }).toDoubleArray())).transpose()
    }

    override fun crossover(
            that: Chromosome,
            f: GoalFunction,
            crossoverType: Either<BinaryCrossoverType, FloatingPointCrossoverType>): Chromosome {

        crossoverType as Left<BinaryCrossoverType>
        that as BinaryChromosome
        return crossoverType.value(this, that, f)
    }

    override fun mutate(probability: Double,
                        mutationType: Either<BinaryMutationType, FloatingPointMutationType>): Chromosome {
        mutationType as Left<BinaryMutationType>
        return mutationType.value(this, probability)
    }

    override fun toString(): String {
        return value.map { it.map { if(it) '1' else '0' }.joinToString(separator="") }.toString()
    }
}
