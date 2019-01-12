package hr.fer.apr.lab4.genetic.model

import hr.fer.apr.lab4.genetic.function.GoalFunction
import hr.fer.apr.lab4.util.split
import hr.fer.apr.util.Matrix
import java.util.*

enum class BinaryCrossoverType {

    /**
     * Selects a single splitting point and switches segments accordingly.
     * In multi-dimensional chromosomes, operation is performed for each dimension.
     * Chrosmose with better fitness is selected.
     */
    SINGLE_POINT {
        override fun invoke(chromosome: BinaryChromosome, thatChromosome: BinaryChromosome, f: GoalFunction): BinaryChromosome {
            val values = chromosome.value
                    .zip(thatChromosome.value)
                    .map {
                        var crossoverPoint = 1
                        if (chromosome.value.size > 2) {
                            crossoverPoint += java.util.Random().nextInt(chromosome.value.size - 2)
                        }
                        val thisSplit = it.first.split(crossoverPoint)
                        val thatSplit = it.second.split(crossoverPoint)
                        Pair(thisSplit.first + thatSplit.second, thatSplit.first + thisSplit.second) }
                    .toList()

            val result = Pair(
                    BinaryChromosome(chromosome.intervalMinimum, chromosome.intervalMaximum, values.map { it.first }.toList()),
                    BinaryChromosome(chromosome.intervalMinimum, chromosome.intervalMaximum, values.map { it.second }.toList()))

            return if (f(result.first) < f(result.second)) result.first else result.second
        }
    },
    /**
     * Child = AB + R * (A XOR B), where R is randomly generated unit number
     */
    UNIFORM {
        override fun invoke(chromosome: BinaryChromosome, thatChromosome: BinaryChromosome, f: GoalFunction): BinaryChromosome {
            val values = chromosome.value
                    .zip(thatChromosome.value)
                    .map {
                        it.first
                                .zip(it.second)
                                .map {
                                    it.first and it.second or java.util.Random().nextBoolean() and it.first.xor(it.second)
                                }
                                .toList() }
                    .toList()
            return BinaryChromosome(chromosome.intervalMinimum, chromosome.intervalMaximum, values)
        }
    };

    abstract operator fun invoke(
            chromosome: BinaryChromosome,
            thatChromosome: BinaryChromosome,
            f: GoalFunction): BinaryChromosome
}

enum class FloatingPointCrossoverType {

    /**
     * a * X1 + (1 - a) * X2, where 'a' is random number from [0,1] interval
     *
     * NOTE: New value of 'a' is generated for every dimension.
     */
    LOCAL_ARITHMETIC {
        override fun invoke(chromosome: FloatingPointChromosome, thatChromosome: FloatingPointChromosome, f: GoalFunction): FloatingPointChromosome {
            val value = Matrix(arrayOf(chromosome.value.rawValue()
                    .zip(thatChromosome.value.rawValue())
                    .map {
                        val a = Random().nextDouble()
                        a * it.first[0] + (1 - a) * it.second[0] }
                    .toDoubleArray()))
                    .transpose()
            return FloatingPointChromosome(chromosome.intervalMinimum, chromosome.intervalMaximum, value)
        }
    },
    /**
     * a * (X2 - X1) + X2, where 'a' is random number from [0,1] interval and X2 is fitter chromosome
     *
     * NOTE: Needs explicit constraints satisfaction check, where in case of violation recalculation
     * is done
     */
    HEURISTIC {
        override fun invoke(chromosome: FloatingPointChromosome, thatChromosome: FloatingPointChromosome, f: GoalFunction): FloatingPointChromosome {
            val (X1, X2) =
                    if (f(chromosome) < f(thatChromosome)) {
                        Pair(thatChromosome, chromosome)
                    } else {
                        Pair(chromosome, thatChromosome)
                    }

            val value = Matrix(
                    arrayOf(X1.value.transpose().rawValue().first()
                        .zip(X2.value.transpose().rawValue().first())
                        .map {
                            var result: Double
                            do {
                                val a = Random().nextDouble()
                                result = a * (it.second - it.first) + it.second
                            } while (result < chromosome.intervalMinimum || result > chromosome.intervalMaximum)
                            result }
                        .toDoubleArray()))
                        .transpose()
            return FloatingPointChromosome(chromosome.intervalMinimum, chromosome.intervalMaximum, value)
        }
    };

    abstract operator fun invoke(
            chromosome: FloatingPointChromosome,
            thatChromosome: FloatingPointChromosome,
            f: GoalFunction): FloatingPointChromosome
}
