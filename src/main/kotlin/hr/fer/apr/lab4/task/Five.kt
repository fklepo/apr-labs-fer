package hr.fer.apr.lab4.task

import hr.fer.apr.lab4.TournamentEliminationGA
import hr.fer.apr.lab4.function.F6
import hr.fer.apr.lab4.util.doubleMedian
import hr.fer.apr.lab4.util.fromPath
import hr.fer.apr.lab4.util.mergeWith
import hr.fer.apr.lab4.util.update

fun main(args: Array<String>) {
    val properties =
            fromPath("files/lab4/task5/base.properties")
                    .mergeWith("files/lab4/task1/base_floating.properties")
                    .update("dimensionality", "2")
    var ga: TournamentEliminationGA

    val kResult = HashMap<Int, MutableList<Double>>()

    for (k in listOf(3,5,10,15,20,30,40,60)) {
        kResult.put(k, mutableListOf())
        properties.update("k", k.toString())
        ga = TournamentEliminationGA(properties)

        for (i in (1..10)) {
            kResult.get(k)!!.add(ga.evaluate(F6()))
        }
    }

    val bestK = kResult.map { Pair(it.key, it.value.doubleMedian()) }.sortedBy { it.second }.first().first
    println("Best k: ${bestK}")
    println(kResult.map { Pair(it.key, it.value.doubleMedian()) })

    //Best k: 10
    //[(3, 0.009745527781428426), (20, 0.009729988458041017), (5, 0.009721285860056078), (40, 0.009721028769095835), (10, 0.009719533340711045), (60, 0.009743706645030992), (30, 0.009722343021439095), (15, 0.009721337544689596)]
}