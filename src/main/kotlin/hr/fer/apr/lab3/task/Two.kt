package hr.fer.apr.lab3.task

import hr.fer.apr.lab3.algorithm.GradientDescent
import hr.fer.apr.lab3.algorithm.NewtonRhapson
import hr.fer.apr.lab3.function.F1
import hr.fer.apr.lab3.function.F2
import hr.fer.apr.lab3.function.F3
import hr.fer.apr.util.Matrix

fun main(args: Array<String>) {
    println("F1 ***")
    println()
    println("Gradient descent")
    var f1 = F1()
    val x0F1 = Matrix.vector(-1.9, 2.0)
    try {
        println(GradientDescent.evaluate(f1, x0F1, useGoldenCut = true).transpose())
    } catch (e: Exception) {
        println(e)
        println()
    }
    println("number of function calls: " + f1.numberOfCalls)
    println("number of gradient calls: " + f1.numberOfGradientCalls)
    println()

    println("Newton Rhapson")
    f1 = F1()
    try {
        println(NewtonRhapson.evaluate(f1, x0F1, useGoldenCut = true).transpose())
    } catch (e: Exception) {
        println(e)
        println()
    }
    println("number of function calls: " + f1.numberOfCalls)
    println("number of gradient calls: " + f1.numberOfGradientCalls)
    println("number of hessian calls: " + f1.numberOfHessianCalls)
    println()

    println("F2 ***")
    println()

    println("Gradient descent")
    var f2 = F2()
    val x0F2 = Matrix.vector(0.1, 0.3)
    try {
        println(GradientDescent.evaluate(f2, x0F2, useGoldenCut = true).transpose())
    } catch (e: Exception) {
        println(e)
    }
    println("number of function calls: " + f2.numberOfCalls)
    println("number of gradient calls: " + f2.numberOfGradientCalls)
    println()

    println("Newton Rhapson")
    f2 = F2()
    try {
        println(NewtonRhapson.evaluate(f2, x0F2, useGoldenCut = true).transpose())
    } catch (e: Exception) {
        println(e)
        println()
    }
    println("number of function calls: " + f2.numberOfCalls)
    println("number of gradient calls: " + f2.numberOfGradientCalls)
    println("number of hessian calls: " + f2.numberOfHessianCalls)
    println()

}
