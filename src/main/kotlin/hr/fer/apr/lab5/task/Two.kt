package hr.fer.apr.lab5.task

import hr.fer.apr.util.Matrix

fun main(args: Array<String>) {
    val matrix = Matrix(
            arrayOf(doubleArrayOf(4.0, -5.0, -2.0),
                    doubleArrayOf(5.0, -6.0, -2.0),
                    doubleArrayOf(-8.0, 9.0, 3.0)))

    println(matrix.inverse())
}