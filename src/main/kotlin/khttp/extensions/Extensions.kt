/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package khttp.extensions

import khttp.structures.files.FileLike
import java.io.File
import java.io.Writer
import java.nio.file.Path

/**
 * Creates a [FileLike] from this File and [name]. If [name] is not specified, the filename will be used.
 */
fun File.fileLike(name: String = this.name) = FileLike(name, this)

/**
 * Creates a [FileLike] from the Path.
 */
fun Path.fileLike() = FileLike(this)

/**
 * Creates a [FileLike] from this Path and [name]. If [name] is not specified, the filename will be used.
 */
fun Path.fileLike(name: String) = FileLike(name, this)

/**
 * Creates a [FileLike] from this String and [name].
 */
fun String.fileLike(name: String) = FileLike(name, this)

/**
 * Writes [string] to this writer and then calls [Writer#flush()][java.io.Writer#flush].
 */
internal fun Writer.writeAndFlush(string: String) {
    this.write(string)
    this.flush()
}

fun ByteArray.splitLines(): List<ByteArray> {
    if (this.isEmpty()) return listOf()
    val lines = arrayListOf<ByteArray>()
    var lastSplit = 0
    var skipByte = false
    for ((i, byte) in this.withIndex()) {
        if (skipByte) {
            skipByte = false
            continue
        }

        if (byte == '\r'.code.toByte() && i + 1 < this.size && this[i + 1] == '\n'.code.toByte()) {
            skipByte = true
            lines.add(this.sliceArray(lastSplit until i))
            lastSplit = i + 2
        } else if (byte == '\r'.code.toByte() || byte == '\n'.code.toByte()) {
            lines.add(this.sliceArray(lastSplit until i))
            lastSplit = i + 1
        }
    }

    lines += this.sliceArray(lastSplit until this.size)
    return lines
}

fun ByteArray.split(delimiter: ByteArray): List<ByteArray> {
    val lines = arrayListOf<ByteArray>()
    var lastSplit = 0
    var skip = 0
    for (i in indices) {
        if (skip > 0) {
            skip--
            continue
        }
        if ((i + delimiter.size) >= this.size) break // no space for slice

        if (this.sliceArray(i until i + delimiter.size).toList() == delimiter.toList()) {
            skip = delimiter.size
            lines += this.sliceArray(lastSplit until i)
            lastSplit = i + delimiter.size
        }
    }
    lines += this.sliceArray(lastSplit until this.size)
    return lines
}

internal fun <T> Class<T>.getSuperclasses(): List<Class<in T>> {
    val list = arrayListOf<Class<in T>>()
    var superclass = this.superclass
    while (superclass != null) {
        list.add(superclass)
        superclass = superclass.superclass
    }
    return list
}

fun <K, V> MutableMap<K, V>.putIfAbsentWithNull(key: K, value: V) {
    if (key !in this) {
        this[key] = value
    }
}

fun <K, V> MutableMap<K, V>.putAllIfAbsentWithNull(other: Map<K, V>) {
    for ((key, value) in other) {
        this.putIfAbsentWithNull(key, value)
    }
}
