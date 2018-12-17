package hr.fer.apr.lab1.task

import hr.fer.apr.util.Matrix

fun main(args: Array<String>) {
    val A = Matrix("files/lab1/A4.txt")
    val b = Matrix("files/lab1/b4.txt")

    val lu = A.luDecompose()

    val luY = lu.forwardSubstitution(b)
    val luX = lu.backSubstitution(luY)
    println("### LU ###")
    println()
    println("### X")
    println(luX)
    println()

    val lup = A.lupDecompose()
    val lupY = lup.first.forwardSubstitution(lup.second * b)
    val lupX = lup.first.backSubstitution(lupY)
    println("### LUP ###")
    println()
    println("### P")
    println(lup.second)
    println()
    println("### X")
    println(lupX)
}