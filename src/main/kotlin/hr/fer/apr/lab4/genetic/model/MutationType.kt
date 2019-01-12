package hr.fer.apr.lab4.genetic.model

import java.util.*
import kotlin.math.roundToInt

enum class BinaryMutationType {

    SIMPLE {
        override fun invoke(chromosome: BinaryChromosome, p: Double): BinaryChromosome {
            if (Random().nextDouble() > p) {
                return chromosome
            }

            val value = chromosome.value
            val mutationIndex = (Math.random() * value.size * value[0].size).roundToInt()
            val dimensionIndex = mutationIndex / value[0].size
            val unitIndex = mutationIndex % value[0].size

            val mutatedValue =
                    value.zip((0..value.size - 1)).map {
                        if (it.second != dimensionIndex) {
                            it.first
                        } else {
                            it.first.zip((0..it.first.size - 1))
                                    .map { if (it.second == unitIndex) it.first.not() else it.first }
                        }
                    }
            return BinaryChromosome(chromosome.intervalMinimum, chromosome.intervalMaximum, mutatedValue)
        }
    };

    abstract operator fun invoke(
            chromosome: BinaryChromosome,
            p: Double): BinaryChromosome
}

enum class FloatingPointMutationType {

    UNIFORM {
        override fun invoke(chromosome: FloatingPointChromosome, p: Double): FloatingPointChromosome {
            var value = chromosome.value
            val mutatedDimensionIndex = (Math.random() * value.size().first).toInt()
            value = value.copy()
            value[mutatedDimensionIndex] =
                    chromosome.intervalMinimum + Math.random() * (chromosome.intervalMaximum - chromosome.intervalMinimum)

            return FloatingPointChromosome(chromosome.intervalMinimum, chromosome.intervalMaximum, value)
        }
    },
    BORDER {
        override fun invoke(chromosome: FloatingPointChromosome, p: Double): FloatingPointChromosome {
            if (Random().nextDouble() > p) {
                return chromosome
            }
            return FloatingPointChromosome(
                    chromosome.value.size().first,
                    chromosome.intervalMinimum,
                    chromosome.intervalMaximum)
        }
    };

    abstract operator fun invoke(
            chromosome: FloatingPointChromosome,
            p: Double): FloatingPointChromosome
}
