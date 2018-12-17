package hr.fer.apr.lab1.task

import hr.fer.apr.util.Matrix

fun main(args: Array<String>) {
    var matrix = Matrix(arrayOf(doubleArrayOf(3.412, 2.321, 5.59)))
    var matrix2 = Matrix(arrayOf(doubleArrayOf(3.412, 2.321, 5.59)))
    matrix = matrix * 4.592876234786234236138174623874234234
    matrix = matrix / 4.592876234786234236138174623874234234
    println(matrix.equals(matrix2))
}