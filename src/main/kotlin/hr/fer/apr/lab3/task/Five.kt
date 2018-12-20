package hr.fer.apr.lab3.task

import hr.fer.apr.lab3.algorithm.MixedConstraint
import hr.fer.apr.lab3.function.F4
import hr.fer.apr.lab3.util.EqualityConstraint
import hr.fer.apr.lab3.util.ImplicitConstraint
import hr.fer.apr.util.Matrix

fun main(args: Array<String>) {
    val implicits = listOf<ImplicitConstraint>({ 3 - it[0] - it[1] }, { 3 +1.5*it[0] - it[1] })
    val equalities = listOf<EqualityConstraint>({ it[1] - 1 })

    println("F4")
    val x0 = Matrix.vector(5.0, 5.0)
    val f = F4()
    println(MixedConstraint.evaluate(f, x0, implicits, equalities, verbose = true))
}