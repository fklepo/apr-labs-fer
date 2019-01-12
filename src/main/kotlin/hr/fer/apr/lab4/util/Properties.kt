package hr.fer.apr.lab4.util

import java.io.FileInputStream
import java.util.*

fun fromPath(path: String): Properties {
    val properties = Properties()
    properties.load(FileInputStream(path))
    return properties
}

fun Properties.mergeWith(path: String): Properties {
    val properties = Properties()
    properties.load(FileInputStream(path))
    this.forEach({ k, v -> properties.setProperty(k.toString(), v.toString())})
    return properties
}

fun Properties.update(key: String, value: String): Properties {
    this.setProperty(key, value)
    return this
}
