package hr.fer.apr.lab5.task

import hr.fer.apr.util.Matrix

fun main(args: Array<String>) {
    val matrix = Matrix(
            arrayOf(doubleArrayOf(1.0, 2.0, 3.0),
                    doubleArrayOf(4.0, 5.0, 6.0),
                    doubleArrayOf(7.0, 8.0, 9.0)))

    println(matrix.inverse())
}