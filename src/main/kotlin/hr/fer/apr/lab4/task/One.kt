package hr.fer.apr.lab4.task

import hr.fer.apr.lab4.TournamentEliminationGA
import hr.fer.apr.lab4.function.F1
import hr.fer.apr.lab4.function.F3
import hr.fer.apr.lab4.function.F6
import hr.fer.apr.lab4.function.F7
import hr.fer.apr.lab4.genetic.function.GoalFunction
import hr.fer.apr.lab4.util.mergeWith
import hr.fer.apr.lab4.util.fromPath
import hr.fer.apr.lab4.util.update

fun main(args: Array<String>) {

    val baseProperties = fromPath("files/lab4/task1/base.properties")

    for ((name, generator) in
            listOf("f1", "f3", "f6", "f7").zip(listOf<() -> GoalFunction>({ F1() }, { F3() }, { F6() }, { F7() }))) {

        println(name)
        println("binary")
        val gaBinary = TournamentEliminationGA(baseProperties.mergeWith("files/lab4/task1/base_binary.properties").update("dimensionality", "2"))
        println(gaBinary.evaluate(generator()))
        val gaFloating = TournamentEliminationGA(baseProperties.mergeWith("files/lab4/task1/base_floating.properties").update("dimensionality", "2"))
        println("floating")
        println(gaFloating.evaluate(generator()))
        println()
    }
}
