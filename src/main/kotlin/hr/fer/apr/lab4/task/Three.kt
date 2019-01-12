package hr.fer.apr.lab4.task

import hr.fer.apr.lab4.TournamentEliminationGA
import hr.fer.apr.lab4.function.F6
import hr.fer.apr.lab4.function.F7
import hr.fer.apr.lab4.genetic.function.GoalFunction
import hr.fer.apr.lab4.util.mergeWith
import hr.fer.apr.lab4.util.fromPath
import hr.fer.apr.lab4.util.update

val NO_OF_ITERATIONS = 20

fun main(args: Array<String>) {

    val baseProperties = fromPath("files/lab4/task1/base.properties").update("noOfIterations", "10000")
    val f6Data = HashMap<String, Pair<MutableList<Double>, MutableList<Double>>>()
    val f7Data = HashMap<String, Pair<MutableList<Double>, MutableList<Double>>>()

    for((fiData, generator) in
            listOf(f6Data, f7Data).zip(listOf<() -> GoalFunction>({ F6() }, { F7() }))) {

        for (dimensionality in listOf("3", "6")) {

            fiData.put(dimensionality, Pair(mutableListOf(), mutableListOf()))
            val gaBinary = TournamentEliminationGA(
                    baseProperties
                            .mergeWith("files/lab4/task1/base_binary.properties")
                            .update("dimensionality", dimensionality)
                            .update("precision", "4"))
            val gaFloating = TournamentEliminationGA(
                    baseProperties
                            .mergeWith("files/lab4/task1/base_floating.properties")
                            .update("dimensionality", dimensionality))

            for (i in (1..NO_OF_ITERATIONS)) {
                fiData.get(dimensionality)!!.first.add(gaBinary.evaluate(generator()))
                fiData.get(dimensionality)!!.second.add(gaFloating.evaluate(generator()))
            }
            //TODO usporedi binary vs floating, prvo po pogodima pa onda po medijanu
            //TODO usporedi po onim pravilima iz upute
        }
    }

    println(f6Data)
    println(f7Data)
}
