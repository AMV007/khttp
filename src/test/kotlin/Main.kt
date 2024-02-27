import java.io.File

fun writeBytes(filename: String, bytes: List<Byte>) {
    File(filename).writeBytes(bytes.toByteArray())
}

fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) {
}



