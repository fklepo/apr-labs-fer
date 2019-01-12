package hr.fer.apr.lab4

import hr.fer.apr.lab4.genetic.function.GoalFunction
import hr.fer.apr.lab4.genetic.model.*
import hr.fer.apr.lab4.util.Either
import hr.fer.apr.lab4.util.Left
import hr.fer.apr.lab4.util.Right
import java.io.FileInputStream
import java.util.*

val CONFIG_FILE_NAME = "files/lab4/binary_ga.properties.example"

fun chromosomeFromRepresentation(geneticAlgorithm: TournamentEliminationGA,
                                 n: Int): Chromosome {
    return if (geneticAlgorithm.chromosomeRepresentation == ChromosomeRepresentation.BINARY) {
        BinaryChromosome(
                n,
                geneticAlgorithm.precision.get(),
                geneticAlgorithm.intervalMinimum,
                geneticAlgorithm.intervalMaximum)
    } else {
        FloatingPointChromosome(
                n,
                geneticAlgorithm.intervalMinimum,
                geneticAlgorithm.intervalMaximum)
    }
}

class TournamentEliminationGA {

    companion object {
        fun fromFile(file: String = CONFIG_FILE_NAME): TournamentEliminationGA {
            val properties = Properties()
            properties.load(FileInputStream(file))
            return TournamentEliminationGA(properties)
        }
    }

    val k: Int
    val dimensionality: Int
    val precision: Optional<Int>
    val intervalMinimum: Double
    val intervalMaximum: Double
    val populationSize: Int
    val chromosomeRepresentation: ChromosomeRepresentation
    val crossoverType: Either<BinaryCrossoverType, FloatingPointCrossoverType>
    val mutationType: Either<BinaryMutationType, FloatingPointMutationType>
    val mutationProbability: Double
    val noOfIterations: Int

    constructor(properties: Properties) {
        this.k = properties.get("k").toString().toInt()
        this.dimensionality = properties.get("dimensionality").toString().toInt()
        this.intervalMinimum = properties.get("intervalMinimum").toString().toDouble()
        this.intervalMaximum = properties.get("intervalMaximum").toString().toDouble()
        this.populationSize = properties.get("populationSize").toString().toInt()
        this.chromosomeRepresentation = (properties.get("chromosomeRepresentation").toString()).let {
            when(it) {
                "BINARY" -> ChromosomeRepresentation.BINARY
                "FLOATING_POINT" -> ChromosomeRepresentation.FLOATING_POINT
                else -> throw IllegalArgumentException("Illegal chromosomeRepresentation value: ${it}")
            }
        }
        this.precision = if(this.chromosomeRepresentation.equals(ChromosomeRepresentation.BINARY)) {
            Optional.of(properties.get("precision").toString().toInt())
        } else {
            Optional.empty()
        }
        this.crossoverType = (properties.get("crossoverType") as String).let {
            when(this.chromosomeRepresentation) {
                ChromosomeRepresentation.BINARY -> Left(BinaryCrossoverType.valueOf(it))
                ChromosomeRepresentation.FLOATING_POINT -> Right(FloatingPointCrossoverType.valueOf(it))
            }
        }
        this.mutationType = (properties.get("mutationType") as String).let {
            when(this.chromosomeRepresentation) {
                ChromosomeRepresentation.BINARY -> Left(BinaryMutationType.valueOf(it))
                ChromosomeRepresentation.FLOATING_POINT -> Right(FloatingPointMutationType.valueOf(it))
            }
        }
        this.mutationProbability = properties.get("mutationProbability").toString().toDouble()
        this.noOfIterations = properties.get("noOfIterations").toString().toInt()
    }

    fun evaluate(f: GoalFunction, verbose: Boolean = false): Double {
        var iteration = 0
        val population = (1..this.populationSize)
                .map { chromosomeFromRepresentation(this, dimensionality) }
                .toMutableList()
        var currentBestChromosome = Optional.empty<Chromosome>()
        do {
            val crossoverCandidatesMutable = (0..populationSize - 1).toMutableList()
            Collections.shuffle(crossoverCandidatesMutable)
            var crossoverCandidates = crossoverCandidatesMutable.toList()
            crossoverCandidates = crossoverCandidates.take(k).sortedBy { Math.abs(f(population[it])) }

            var childChromosome =
                    population[crossoverCandidates[0]].crossover(population[crossoverCandidates[1]], f, this.crossoverType)

            childChromosome = childChromosome.mutate(mutationProbability, mutationType)
            population.removeAt(crossoverCandidates.last())
            population.add(childChromosome)
            val populationBestChromosome = population
                    .sortedBy { Math.abs(f(it)) }
                    .first()

            iteration++
            if(currentBestChromosome.isPresent.not() || Math.abs(f(populationBestChromosome)) < Math.abs(f(currentBestChromosome.get()))) {
                currentBestChromosome = Optional.of(populationBestChromosome)
                if (verbose) {
                    println("Iteration $iteration: New best unit ${populationBestChromosome.value()} " +
                            "with fitness ${f(populationBestChromosome)}")
                }
            }
        } while(iteration < this.noOfIterations)

        if (verbose) {
            println("Evaluation finished after ${this.noOfIterations} iterations!")
        }
        return f(currentBestChromosome.get())
    }
}