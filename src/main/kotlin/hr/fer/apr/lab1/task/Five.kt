package hr.fer.apr.lab1.task

import hr.fer.apr.util.Matrix

fun main(args: Array<String>) {
    val A = Matrix("files/lab1/A5.txt")
    val b = Matrix("files/lab1/b5.txt")

    println("### LUP ###")
    val lup = A.lupDecompose()
    val y = lup.first.forwardSubstitution(lup.second * b)
    val x = lup.first.backSubstitution(y)
    println(x)
}