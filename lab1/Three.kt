package hr.fer.apr.lab1

import hr.fer.apr.lab1.Matrix

fun main(args: Array<String>) {
    val A = Matrix("files/lab1/A3.txt")
    val b = Matrix("files/lab1/b3.txt")

    println("### A")
    println(A)
    println()

    println("### b")
    println(b)
    println()

    val lu = A.luDecompose()
    println("### LU ###")
    println(lu)
    println()
    val luY = lu.forwardSubstitution(b)
    println("### y")
    println(luY)
    println()
    val luX = lu.backSubstitution(luY)
    println("### X")
    println(luX)

    println()

    val lup = A.lupDecompose()
    println("### LUP ###")
    println(lup.first)
    val lupY = lup.first.forwardSubstitution(lup.second * b)
    val lupX = lup.first.backSubstitution(lupY)
    println()
    println("### X")
    println(lupX)
}