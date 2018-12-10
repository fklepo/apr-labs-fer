package hr.fer.apr.lab2.util

import hr.fer.apr.lab1.util.Matrix
import java.lang.IllegalArgumentException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun <T, B> Optional<T>.map(f: (T) -> B): Optional<B> {
    if(this.isPresent) {
        return Optional.of(f(this.get()))
    } else {
        return Optional.empty()
    }
}

fun Optional<String>.toDouble(): Optional<Double> {
    if(this.isPresent) {
        return Optional.of(this.get().toDouble())
    } else {
        return Optional.empty()
    }
}

class InputParser {

    companion object {
        fun readMultipleDoubleArguments(text: String): Optional<Matrix> {
            val sc = Scanner(System.`in`)
            try {
                var result = mutableListOf<Double>()

                while (true) {
                    print("${text} or ENTER to finish > ")
                    var input = sc.nextLine().trim()

                    if(input.isNotEmpty()) {
                        result.add(input.toDouble())
                    } else {
                        break
                    }
                }

                return if(result.isNotEmpty()) {
                    Optional.of(Matrix(arrayOf(result.toDoubleArray())).transpose())
                } else {
                    Optional.empty()
                }
            } catch(e: NumberFormatException) {
                System.err.println(e)
                return Optional.empty()
            }
        }

        fun readDoubleArgument(text: String): Optional<Double> {
            val sc = Scanner(System.`in`)
            print("${text} > ")
            try {
                return Optional.of(sc.nextLine().trim().toDouble())
            } catch(e: NumberFormatException) {
                return Optional.empty()
            }
        }

        fun readPropertiesFile(path: String): Map<String, String> {
            val result = HashMap<String, String>()
            for(line in Files.readAllLines(Paths.get(path))) {
                val trimmedLine = line.trim()
                if(trimmedLine.startsWith("#")) {
                    continue
                }
                val chunks = trimmedLine.split("=")
                if(chunks.size != 2) {
                    throw IllegalArgumentException("Illegal properties file line: \n\t${line}")
                }
                result[chunks[0].trim()] = chunks[1].trim()
            }
            return result
        }

        fun readVector(representation: String): Matrix {
            val numberList = ArrayList<Double>()
            for(number in representation.split(",")) {
                numberList.add(number.trim().toDouble())
            }
            return Matrix(arrayOf(numberList.toDoubleArray())).transpose()
        }
    }
}