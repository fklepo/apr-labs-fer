package hr.fer.apr.lab1.tasks

import hr.fer.apr.lab1.Matrix

fun main(args: Array<String>) {
    val A = Matrix("files/lab1/A6.txt")
    val b = Matrix("files/lab1/b6.txt")

    val lu = A.luDecompose()
    println("### LU ###")
    val y = lu.forwardSubstitution(b)
    val x = lu.backSubstitution(y)
    println()
    println("### X")
    println(x)

    println()
    val AT = Matrix("files/lab1/A6_transformed.txt")
    val bT = Matrix("files/lab1/b6_transformed.txt")
    val luT = AT.luDecompose()
    println("### LU t###")
    val yT = luT.forwardSubstitution(bT)
    val xT = luT.backSubstitution(yT)
    println()
    println("### X t")
    println(xT)

}