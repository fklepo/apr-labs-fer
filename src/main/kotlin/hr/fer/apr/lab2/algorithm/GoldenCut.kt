package hr.fer.apr.lab2.algorithm

import hr.fer.apr.lab2.util.InputParser
import hr.fer.apr.lab2.util.toDouble
import java.util.Optional

val E = 1e-6
val H = 0.2
val K = 0.5 * (Math.sqrt(5.0) - 1)

class GoldenCut {

    companion object {
        fun unimodalInterval(f: (Double) -> Double, start: Double, h: Double): Pair<Double, Double> {
            var l = start - h
            var r = start + h
            var m = start
            var step = 1

            var fM = f(start)
            var fL = f(l)
            var fR = f(r)

            if(fM < fR && fM < fL) {
                return Pair(l, r)
            } else if(fM > fR) {
                do {
                    l = m
                    m = r
                    fM = fR
                    step *= 2
                    r = start + h*step
                    fR = f(r)
                } while(fM > fR)
            } else {
                do {
                    r = m
                    m = l
                    fM = fL
                    step *= 2
                    l = start - h*step
                    fL = f(l)
                } while(fM > fL)
            }

            return Pair(l, r)
        }

        fun evaluate(f: (Double) -> Double, aAndB: Pair<Double, Double>, e: Double = E, verbose: Boolean = false): Pair<Double, Double> {
            var mA = aAndB.first
            var mB = aAndB.second

            var c = mB - K * (mB - mA)
            var d = mA + K * (mB - mA)
            var fC = f(c)
            var fD = f(d)

            var step = 0
            while(Math.abs(mB - mA).compareTo(e) > 0) {
                if(fC.compareTo(fD) < 0) {
                    mB = d
                    d = c
                    c = mB - K * (mB - mA)
                    fD = fC
                    fC = f(c)
                } else {
                    mA = c
                    c = d
                    d = mA + K * (mB - mA)
                    fC = fD
                    fD = f(d)
                }

                if(verbose) {
                    println("### STEP ${step++}: ")
                    println("A = ${mA}")
                    println("C = ${c} f(C) = ${fC}")
                    println("D = ${d} f(D) = ${fD}")
                    println("B = ${mB}")
                    println()
                }
            }
            return Pair(mA, mB)
        }

        fun evaluate(f: (Double) -> Double, start: Double, h: Double = H, e: Double = E, verbose: Boolean = true): Pair<Double, Double> {
            return evaluate(f, unimodalInterval(f, start, h), e, verbose = verbose)
        }

        fun evaluate(f: (Double) -> Double, propertiesPath: String? = null, verbose: Boolean = true): Pair<Double, Double> {
            var propertiesMap: Map<String, String> = HashMap<String, String>()
            if (propertiesPath != null) {
                propertiesMap = InputParser.readPropertiesFile(propertiesPath)
            }
            val e = Optional.ofNullable(propertiesMap["e"]).toDouble().orElseGet { E }
            val firstArgument = Optional.ofNullable(propertiesMap["a"]).toDouble().get()
            val secondArgument = Optional.ofNullable(propertiesMap["b"]).toDouble()

            return if(!secondArgument.isPresent) {
                val h = Optional.ofNullable(propertiesMap["h"]).toDouble().orElseGet { H }
                evaluate(f, firstArgument, h, e, verbose = verbose)
            } else {
                evaluate(f, Pair(firstArgument, secondArgument.get()), e, verbose = verbose)
            }
        }
    }
}

fun main(args: Array<String>) {
    println(GoldenCut.evaluate({ a -> Math.pow(a - 2, 2.0) }))
}