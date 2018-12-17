package hr.fer.apr.util

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.lang.IllegalArgumentException

class MatrixTest {

    @Test
    fun testConstructorForFile() {
        val matrix = Matrix("files/lab1/A2.txt")
        assert(matrix.equals(
                Matrix(arrayOf(doubleArrayOf(3.0, 9.0, 6.0),
                        doubleArrayOf(4.0, 12.0, 12.0),
                        doubleArrayOf(1.0, -1.0, 1.0))
                )), {  -> "Expected equal" })
    }

    @Test(expected = IllegalArgumentException::class)
    fun testConstructorForInvalidMatrix() {
        Matrix(arrayOf(
                doubleArrayOf(3.0, 9.0, 6.0),
                doubleArrayOf(4.0, 12.0),
                doubleArrayOf(1.0, -1.0, 1.0))
        )
    }

    @Test
    fun testSize() {
        assertEquals(
                Matrix(arrayOf(doubleArrayOf(3.0, 9.0),
                        doubleArrayOf(4.0, 12.0),
                        doubleArrayOf(1.0, -1.0))
                ).size(),
                Pair(3, 2))

        assertEquals(
                Matrix(arrayOf(
                        doubleArrayOf(3.0))
                ).size(),
                Pair(1, 1))

        assertEquals(
                Matrix(arrayOf(
                        doubleArrayOf(3.0, 9.0),
                        doubleArrayOf(4.0, 12.0))
                ).size(),
                Pair(2, 2))
    }

    @Test
    fun testGetVector() {
        val vector = Matrix(arrayOf(
                doubleArrayOf(3.0),
                doubleArrayOf(4.0),
                doubleArrayOf(5.0)))
        assertEquals(vector.get(0), 3.0)
        assertEquals(vector.get(1), 4.0)
        assertEquals(vector.get(2), 5.0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testGetVectorIllegalIndex() {
        val vector = Matrix(arrayOf(doubleArrayOf(3.0),
                doubleArrayOf(4.0),
                doubleArrayOf(5.0)))
        vector.get(3)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testGetVectorIllegalNegativeIndex() {
        val vector = Matrix(arrayOf(doubleArrayOf(3.0),
                doubleArrayOf(4.0),
                doubleArrayOf(5.0)))
        vector.get(-1)
    }

    @Test
    fun testGetMatrix() {
        val vector = Matrix(arrayOf(
                doubleArrayOf(3.0, 4.0, 5.0),
                doubleArrayOf(4.0, 8.0, 9.8),
                doubleArrayOf(5.2, 3.2, 18.3)))
        assertEquals(vector.get(2, 2), 18.3)
        assertEquals(vector.get(1, 1), 8.0)
        assertEquals(vector.get(0, 0), 3.0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testGetMatrixIllegalIndex() {
        val vector = Matrix(arrayOf(
                doubleArrayOf(3.0, 4.0, 5.0),
                doubleArrayOf(4.0, 8.0, 9.8),
                doubleArrayOf(5.2, 3.2, 18.3)))

        vector.get(3, 3)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testGetMatrixIllegalIndex2() {
        val vector = Matrix(arrayOf(
                doubleArrayOf(3.0, 4.0, 5.0),
                doubleArrayOf(4.0, 8.0, 9.8),
                doubleArrayOf(5.2, 3.2, 18.3)))

        vector.get(1, 3)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testGetMatrixIllegalIndex3() {
        val vector = Matrix(arrayOf(
                doubleArrayOf(3.0, 4.0, 5.0),
                doubleArrayOf(4.0, 8.0, 9.8),
                doubleArrayOf(5.2, 3.2, 18.3)))

        vector.get(-1, 0)
    }

    @Test
    fun testCopyOf() {
        val array = arrayOf(
                doubleArrayOf(3.0, 4.0, 5.0),
                doubleArrayOf(4.0, 8.0, 9.8),
                doubleArrayOf(5.2, 3.2, 18.3))
        val array2 = array.copyOf()
        array2[0][0] = 15.0
        assertEquals(15.0, array[0][0])
    }

    @Test
    fun testDeepCopyOf() {
        val array = arrayOf(
                doubleArrayOf(3.0, 4.0, 5.0),
                doubleArrayOf(4.0, 8.0, 9.8),
                doubleArrayOf(5.2, 3.2, 18.3))
        val array2 = Matrix.deepCopy(array)
        array2[0][0] = 15.0
        assertEquals(3.0, array[0][0])
    }

    @Test
    fun testCopyOfDoubleArray() {
        val array = doubleArrayOf(3.0, 4.0, 5.0)
        val array2 = array.copyOf()
        array2[0] = 1.0
        assertEquals(3.0, array[0])
    }

    @Test
    fun testForwardSubstitution() {
        val A = Matrix(arrayOf(
                doubleArrayOf(1.0, 0.0, 0.0),
                doubleArrayOf(0.6, 1.0, 0.0),
                doubleArrayOf(0.2, 0.571, 1.0)))
        val b = Matrix(arrayOf(doubleArrayOf(10.3, 12.5, 0.1))).transpose()
        println(A.forwardSubstitution(b))
        assertEquals(1, 1)
    }

    @Test
    fun testConcat() {
        val A = Matrix(arrayOf(
                doubleArrayOf(1.0, 0.0, 0.0),
                doubleArrayOf(0.6, 1.0, 0.0),
                doubleArrayOf(0.2, 0.571, 1.0)))
        val b = Matrix(arrayOf(doubleArrayOf(10.0, 12.0, 1.0)))
        val concatMatrix = A.concat(b.transpose(), vertically = true)
        assertEquals(10.0, concatMatrix[0, 3])
        assertEquals(Pair(3, 4), concatMatrix.size())

        val concatMatrix2 = A.concat(b)
        assertEquals(12.0, concatMatrix2[3, 1])
        assertEquals(Pair(4, 3), concatMatrix2.size())
    }

    @Test
    fun testInverse() {
        val A = Matrix(arrayOf(
                doubleArrayOf(1.0, 0.0, 0.0),
                doubleArrayOf(0.6, 1.0, 0.0),
                doubleArrayOf(0.2, 0.571, 1.0)))
        A.inverse()
    }
}