package hr.fer.apr.lab1

import java.io.File
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

val EPSILON = 1e-6

class Matrix {

    companion object {
        fun deepCopy(arr: Array<DoubleArray>): Array<DoubleArray> {
            return arr.map { it.clone() }.toTypedArray()
        }

        fun doubleEquals(d1: Double, d2: Double): Boolean {
            return abs(d1 - d2) < EPSILON
        }
    }

    private val elems: Array<DoubleArray>

    constructor(elems: Array<DoubleArray>) {
        this.elems = assertMatrixRepresentationValidity(elems)
    }

    constructor(filePath: String) {
        val file = File(filePath)
        if(!file.isFile) {
            throw IllegalArgumentException("Path to a representation file was expected.")
        }

        this.elems = assertMatrixRepresentationValidity(
                file
                .readLines()
                .map { it.trim().replace("//s+", " ").split(" ").map { it.toDouble() }.toDoubleArray() }
                .toTypedArray())
    }

    fun assertMatrixRepresentationValidity(array: Array<DoubleArray>): Array<DoubleArray> {
        if (array.isEmpty() || array.size > 1 && array.any { array[0].size.equals(it.size).not() }) {
            throw IllegalArgumentException("Invalid matrix representation.")
        }
        return array
    }

    fun size(): Pair<Int, Int> {
        return Pair(elems.size, elems[0].size)
    }

    operator fun get(i: Int): Double {
        if (!1.equals(size().second)) {
            throw UnsupportedOperationException("Operation is supported for row vectors only.")
        }
        if ((i < 0 || i >= size().first)) {
            throw IllegalArgumentException("Given position is invalid.")
        }

        return elems[i][0]
    }

    operator fun get(i: Int, j: Int): Double {
        if ((i < 0 || i >= size().first) || (j < 0 || j >= elems[0].size)) {
            throw IllegalArgumentException("Given position is invalid.")
        }

        return elems[i][j]
    }

    operator fun set(i: Int, value: Double) {
        if (!1.equals(size().second)) {
            throw UnsupportedOperationException("Operation is supported for row vectors only.")
        }
        if ((i < 0 || i >= size().first)) {
            throw IllegalArgumentException("Given position is invalid.")
        }

        elems[i][0] = value
    }

    operator fun set(i: Int, j: Int, value: Double) {
        if ((i < 0 || i >= size().first) || (j < 0 || j >= elems[0].size)) {
            throw IllegalArgumentException("Given position is invalid.")
        }

        elems[i][j] = value
    }

    private fun calculateElementWise(a: Matrix, b: Matrix, f: (Double, Double) -> Double): Matrix {
        if (a.size() != b.size()) {
            throw IllegalArgumentException("Matrices must be of same dimensions, got ${a.size()} and ${b.size()}")
        }

        return Matrix(
                a.elems
                        .zip(b.elems)
                        .map { it.first.zip(it.second).map { f(it.first, it.second) }.toDoubleArray() }
                        .toTypedArray())
    }

    operator fun plus(other: Matrix): Matrix {
        if(size() != other.size()) {
            throw IllegalArgumentException("Incompatible matrices.")
        }

        return calculateElementWise(this, other, { a, b -> a + b })
    }

    operator fun minus(other: Matrix): Matrix {
        if(size() != other.size()) {
            throw IllegalArgumentException("Incompatible matrices.")
        }

        return calculateElementWise(this, other, { a, b -> a - b })
    }

    operator fun times(other: Matrix): Matrix {
        if(size().second != other.size().first) {
            throw IllegalArgumentException("Incompatible matrices.")
        }

        return Matrix(
                elems.map { row -> IntRange(0, other.size().second - 1)
                                    .map { other.getCol(it) }
                                    .map { row.zip(it).map { it.first * it.second }
                                                      .reduce { a, i -> a + i } }
                                                      .toDoubleArray() }.toTypedArray())
    }

    operator fun times(c: Double): Matrix {
        return Matrix(elems.map { it.map { x -> x * c }.toDoubleArray() }.toTypedArray())
    }

    operator fun div(c: Double): Matrix {
        return Matrix(elems.map { it.map { x -> x / c }.toDoubleArray() }.toTypedArray())
    }

    private fun getCol(i: Int): DoubleArray {
        if(i < 0 || i >= size().second) {
            throw IllegalArgumentException("Index out of range.")
        }

        return elems.map { it.get(i) }.toDoubleArray()
    }

    fun transpose(): Matrix {
        return Matrix(IntRange(0, size().second - 1).map { getCol(it) }.toTypedArray())
    }

    fun parse(file: File): Matrix {
        val elems = arrayListOf<DoubleArray>()

        for(line in file.readLines()) {
            elems.add(line.trim().replace("//s+", " ").split(" ").map { it.toDouble() }.toDoubleArray())
        }

        return Matrix(elems.toTypedArray())
    }

    fun toFile(file: File) {
        file.writeText(toString())
    }

    fun swapRows(row1: Int, row2: Int): Matrix {
        if(row1 < 0 || row1 >= size().first) {
            throw IllegalArgumentException("Invalid row position ${row1}.")
        }
        if(row2 < 0 || row2 >= size().first) {
            throw IllegalArgumentException("Invalid row position ${row2}.")
        }

        val newElems = Matrix.deepCopy(elems)
        val copy = newElems[row1]
        newElems[row1] = newElems[row2]
        newElems[row2] = copy

        return Matrix(newElems)
    }

    fun swapCols(col1: Int, col2: Int): Matrix {
        if(col1 < 0 || col1 >= size().second) {
            throw IllegalArgumentException("Invalid position ${col1}.")
        }
        if(col2 < 0 || col2 >= size().second) {
            throw IllegalArgumentException("Invalid position ${col2}.")
        }

        val newElems = elems.map {
            val x = it[col1]
            it[col1] = it[col2]
            it[col2] = x
            it
        }.toTypedArray()

        return Matrix(newElems)
    }

    fun forwardSubstitution(b: Matrix): Matrix {
        if(size().first != size().second) {
            throw UnsupportedOperationException("Forward substitution is not defined for non-square matrices.")
        }
        if(b.size().first != size().first) {
            throw IllegalArgumentException("Vector b's dimension is not appropriate for this matrix.")
        }

        val y = Matrix(arrayOf(DoubleArray(size().first))).transpose()
        y[0] = b[0]

        IntRange(1, size().first - 1)
                .forEach { row -> y[row] = b[row] - IntRange(0, row - 1).map { col -> this[row, col] * y[col]}.sum() }

        return y
    }

    fun backSubstitution(y: Matrix): Matrix {
        if(size().first != size().second) {
            throw UnsupportedOperationException("Forward substitution is not defined for non-square matrices.")
        }
        if(y.size().first != size().first) {
            throw IllegalArgumentException("Vector y's dimension is not appropriate for this matrix.")
        }

        val x = Matrix(arrayOf(DoubleArray(size().first))).transpose()
        x[size().first - 1] = y[size().first - 1] / this[size().first - 1, size().first - 1]

        IntRange(0, size().first - 2)
                .reversed()
                .forEach { row ->
                    x[row] = (y[row] -
                                IntRange(row + 1, size().first - 1)
                                        .map { col -> this[row, col] * x[col]}
                                        .sum()) /
                              this[row, row]
                }

        return x
    }

    fun identity(n: Int): Matrix {
        if(n < 1) {
            throw IllegalArgumentException("Dimension must be at least 1.")
        }

        val elems = ArrayList<DoubleArray>()

        Collections.nCopies(n, DoubleArray(n)).map { it.copyOf() }.forEach { elems.add(it) }
        (0..n-1).asSequence().forEach { elems[it][it] = 1.0 }

        return Matrix(elems.toTypedArray())
    }

    fun luDecompose(): Matrix {
        if(this.size().let { it.first != it.second }) {
            throw UnsupportedOperationException("LU decomposition is not defined for non-squared matrices.")
        }

        val lu = Matrix(Matrix.deepCopy(this.elems))

        for (k in 0..lu.size().first-2) {
            if(Math.abs(lu[k, k]).compareTo(EPSILON) < 0) {
                throw UnsupportedOperationException("Zero pivot.")
            }
            for(i in k+1..this.size().first-1) {
                lu[i, k] /= lu[k, k]
                for(j in k+1..this.size().first-1) {
                    lu[i, j] = lu[i, j] - lu[i, k] * lu[k, j]
                }
            }
        }

        return lu
    }

    fun lupDecompose(): Pair<Matrix, Matrix>  {
        if(this.size().let { it.first != it.second }) {
            throw UnsupportedOperationException("LUP decomposition is not defined for non-squared matrices.")
        }

        var lup = Matrix(Matrix.deepCopy(this.elems))
        var perm = identity(lup.size().first)

        for (k in 0..lup.size().first - 2) {
            var pivot = 0.0
            var pivotRow = 0
            for (i in k..lup.size().first - 1) {
                if(Math.abs(lup[i, k]).compareTo(pivot) > 0) {
                    pivot = Math.abs(lup[i, k])
                    pivotRow = i
                }
            }
            if(Math.abs(pivot).compareTo(EPSILON) < 0) {
                throw UnsupportedOperationException("Zero pivot.")
            }
            lup = lup.swapRows(k, pivotRow)
            perm = perm.swapRows(k, pivotRow)

            for(i in k + 1..lup.size().first - 1) {
                lup[i, k] /= lup[k, k]
                for(j in k + 1..this.size().first - 1) {
                    lup[i, j] = lup[i, j] - lup[i, k] * lup[k, j]
                }
            }
        }

        return Pair(lup, perm)
    }

    override fun toString(): String {
        return elems.map { it.joinToString(separator = " ") }.joinToString(separator = System.lineSeparator())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Matrix

        if (!Arrays.deepEquals(elems, other.elems)) {
            return false
        }

        return true
    }
}

fun main(args: Array<String>) {}