package hr.fer.apr.lab3.task

import hr.fer.apr.lab3.algorithm.MixedConstraint
import hr.fer.apr.lab3.function.F1
import hr.fer.apr.lab3.function.F2
import hr.fer.apr.lab3.util.EqualityConstraint
import hr.fer.apr.lab3.util.ImplicitConstraint
import hr.fer.apr.util.Matrix

fun main(args: Array<String>) {
    val implicits = listOf<ImplicitConstraint>({ it[1] - it[0] }, { 2 - it[0] })

    println("F1")
    var x0 = Matrix.vector(-1.9, 2.0)
    val f1 = F1()
    println(MixedConstraint.evaluate(f1, x0, implicits, emptyList<EqualityConstraint>()))

    println("F2")
    x0 = Matrix.vector(0.1, 0.3)
    val f2 = F2()
    println(MixedConstraint.evaluate(f2, x0, implicits, emptyList<EqualityConstraint>()))
}