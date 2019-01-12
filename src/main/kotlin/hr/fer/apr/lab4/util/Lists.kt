package hr.fer.apr.lab4.util

import java.util.*

fun <T> List<T>.split(index: Int): Pair<List<T>, List<T>> {
    return Pair(this.subList(0, index), this.subList(index, this.size))
}

fun <T> List<T>.doubleMedian(): Double where T: Number, T: kotlin.Comparable<T> {
    val sortedList = this.map { it }.toMutableList()
    Collections.sort(sortedList)
    val middleIndex = (sortedList.size - 1) / 2

    return if (sortedList.size % 2 == 0) ((sortedList[middleIndex].toDouble() + sortedList[middleIndex+1].toDouble()) / 2) else sortedList[middleIndex].toDouble()
}

fun <T> List<T>.intMedian(): Int where T: Number, T: kotlin.Comparable<T> {
    val sortedList = this.map { it }.toMutableList()
    Collections.sort(sortedList)
    val middleIndex = (sortedList.size - 1) / 2

    return if (sortedList.size % 2 == 0) ((sortedList[middleIndex].toInt() + sortedList[middleIndex+1].toInt()) / 2) else sortedList[middleIndex].toInt()
}