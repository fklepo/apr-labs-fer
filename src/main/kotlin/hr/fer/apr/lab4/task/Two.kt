package hr.fer.apr.lab4.task

import hr.fer.apr.lab4.TournamentEliminationGA
import hr.fer.apr.lab4.function.F6
import hr.fer.apr.lab4.function.F7
import hr.fer.apr.lab4.util.mergeWith
import hr.fer.apr.lab4.util.fromPath
import hr.fer.apr.lab4.util.update

fun main(args: Array<String>) {

    val baseProperties = fromPath("files/lab4/task1/base.properties")

    for (dimensionality in listOf("1", "3", "6", "10")) {
        println("dimensionality = ${dimensionality}")
        val gaBinary = TournamentEliminationGA(baseProperties.mergeWith("files/lab4/task1/base_binary.properties").update("dimensionality", dimensionality))
        val gaFloating = TournamentEliminationGA(baseProperties.mergeWith("files/lab4/task1/base_floating.properties").update("dimensionality", dimensionality))
        println("f6")
        println("binary")
        println(gaBinary.evaluate(F6()))
        println("floating")
        println(gaFloating.evaluate(F6()))
        println("f7")
        println("binary")
        println(gaBinary.evaluate(F7()))
        println("floating")
        println(gaFloating.evaluate(F7()))
        println()
    }
}
