package hr.fer.apr.lab1.task

import hr.fer.apr.util.Matrix

fun main(args: Array<String>) {
    val A = Matrix("files/lab1/A2.txt")
    val b = Matrix("files/lab1/b2.txt")

    println("### A ###")
    println(A)
    println()

    println("### b ###")
    println(b)
    println()

    val lup = A.lupDecompose()
    println("### LR ###")
    println(lup.first)
    println()
    println("### P ###")
    println(lup.second)
    println()

    val y = lup.first.forwardSubstitution(lup.second * b)

    val x = lup.first.backSubstitution(y)
    println("### x ###")
    println(x)
    // LU ne jer je jedan od pivota broj 0

//    println("### LU ###")
//    val lu = A.luDecompose()
//    println(lu)
//
//    println("### Y ###")
//    val y = lu.forwardSubstitution(Matrix("b2.txt"))
//    println(y)
//    println("### X ###")
//    val x = lu.backSubstitution(y)
//    println(x)


}