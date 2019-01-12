package hr.fer.apr.lab4.task

import hr.fer.apr.lab4.TournamentEliminationGA
import hr.fer.apr.lab4.function.F6
import hr.fer.apr.lab4.function.F7
import hr.fer.apr.lab4.util.*
import java.util.*

fun main(args: Array<String>) {
    //using floating point chromosomes
    val properties =
            fromPath("files/lab4/task1/base.properties")
                    .mergeWith("files/lab4/task1/base_floating.properties")
                    .update("dimensionality", "2")

    val bestPopulationValue = Optional.empty<Double>()
    val populationResult = HashMap<Int, MutableList<Double>>()
    var ga: TournamentEliminationGA

    for (population in listOf(30, 50, 100, 200)) {
        populationResult.put(population, mutableListOf())
        properties.update("populationSize", population.toString())
        ga = TournamentEliminationGA(properties)

        for (i in (1..NO_OF_ITERATIONS)) {
            populationResult.get(population)!!.add(ga.evaluate(F7()))
        }
        //determine best population
    }

    val bestPopulationSize = populationResult.map { Pair(it.key, it.value.doubleMedian()) }.sortedBy { it.second }.first().first

    println("Best population size: ${bestPopulationSize}")
    properties.update("populationSize", bestPopulationSize.toString())
    println(populationResult.map { Pair(it.key, it.value.doubleMedian()) })
    println(populationResult)
    val values = populationResult.entries.sortedBy { it.key }.map { it.value }.toList()
    println()
    println("30,50,100,200")
    for (i in (0..19)) {
        println("${values[0][i]},${values[1][i]},${values[2][i]},${values[3][i]}")
    }

    val mutationProbabilityResult = HashMap<Double, MutableList<Double>>()
    ga = TournamentEliminationGA(properties)

    for (mutationProbability in listOf(0.1, 0.3, 0.6, 0.9)) {
        mutationProbabilityResult.put(mutationProbability, mutableListOf())
        properties.update("mutationProbability", mutationProbability.toString())
        ga = TournamentEliminationGA(properties)

        for (i in (1..NO_OF_ITERATIONS)) {
            mutationProbabilityResult.get(mutationProbability)!!.add(ga.evaluate(F7()))
        }
    }

    val bestMutationProbability = mutationProbabilityResult.map { Pair(it.key, it.value.doubleMedian()) }.sortedBy { it.second }.first().first

    println("Best mutation probability: ${bestMutationProbability}")
    println(mutationProbabilityResult.map { Pair(it.key, it.value.doubleMedian()) })
    println(mutationProbabilityResult)
}