package hr.fer.apr.lab4.genetic.model

import hr.fer.apr.lab4.function.F3
import hr.fer.apr.lab4.genetic.function.GoalFunction
import hr.fer.apr.util.Matrix
import hr.fer.apr.lab4.util.Either
import hr.fer.apr.lab4.util.Left
import hr.fer.apr.lab4.util.Right
import hr.fer.apr.lab4.util.split
import java.util.*
import kotlin.math.roundToInt

enum class ChromosomeRepresentation {
    BINARY,
    FLOATING_POINT
}

abstract class Chromosome {

    val representation: ChromosomeRepresentation

    constructor(representation: ChromosomeRepresentation) {
        this.representation = representation
    }

    abstract fun mutate(probability: Double,
                        mutationType: Either<BinaryMutationType, FloatingPointMutationType>): Chromosome

    abstract fun value(): Matrix

    abstract fun crossover(
            that: Chromosome,
            f: GoalFunction,
            crossoverType: Either<BinaryCrossoverType, FloatingPointCrossoverType>): Chromosome
}

fun main(args: Array<String>) {
    val chr1 = BinaryChromosome(5, 3, 10.0, 20.0)
    val chr2 = BinaryChromosome(5, 3, 10.0, 20.0)

    println(chr1)
    println(chr2)
    println("SINGLE_POINT")
    println(chr1.crossover(chr2, F3(), Left(BinaryCrossoverType.SINGLE_POINT)))
    println("UNIFORM")
    println(chr1.crossover(chr2, F3(), Left(BinaryCrossoverType.UNIFORM)))

    val fChr1 = FloatingPointChromosome(5, 10.0, 20.0)
    val fChr2 = FloatingPointChromosome(5, 10.0, 20.0)

    println(fChr1)
    println(fChr2)
    println("LOCAL ARITHMETIC")
    println(fChr1.crossover(fChr2, F3(), Right(FloatingPointCrossoverType.LOCAL_ARITHMETIC)))
    println("HEURISTIC")
    println(fChr1.crossover(fChr2, F3(), Right(FloatingPointCrossoverType.HEURISTIC)))
    println()

    println("BINARY MUTATION")
    println("BEFORE")
    println(chr1)
    println("AFTER")
    println(chr1.mutate(1.0, Left(BinaryMutationType.SIMPLE)))
    println()
    println("FLOATING MUTATION")
    println("BEFORE")
    println(fChr1)
    println("AFTER")
    println(fChr1.mutate(1.0, Right(FloatingPointMutationType.UNIFORM)))
}