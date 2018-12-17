package hr.fer.apr.lab3.util

import hr.fer.apr.util.Matrix
import java.lang.IllegalArgumentException

class ExplicitConstraint(private val constraints: Matrix) {

    init {
        if (constraints.size().second != 2) {
            throw IllegalArgumentException("Matrix must have 2 cols, for lower and upper bounds")
        }
    }

    fun lowerBound(i: Int): Double {
        return constraints[i, 0]
    }

    fun upperBound(i: Int): Double {
        return constraints[i, 1]
    }

    fun conform(vector: Matrix) {
        assertArgument(vector)

        for (i in (0..this.constraints.size().first-1)) {
            if (vector[i].compareTo(this.constraints[i, 0]) < 0) {
                vector[i] = this.constraints[i, 0]
            } else if (vector[i].compareTo(this.constraints[i, 1]) > 0) {
                vector[i] = this.constraints[i, 1]
            }
        }
    }

    fun satisfies(vector: Matrix): Boolean {
        assertArgument(vector)

        for (i in (0..this.constraints.size().first-1)) {
            if (vector[i].compareTo(this.constraints[i, 0]) < 0 ||
                    vector[i].compareTo(this.constraints[i, 1]) > 0) {
                return false
            }
        }
        return true
    }

    private fun assertArgument(argument: Matrix) {
        if (argument.size().second != 1) {
            throw IllegalArgumentException("Expected vector!")
        }

        if (this.constraints.size().first != argument.size().first) {
            throw IllegalArgumentException("Expected vector!")
        }
    }
}

typealias ImplicitConstraint = (Matrix) -> Double
typealias EqualityConstraint = (Matrix) -> Double