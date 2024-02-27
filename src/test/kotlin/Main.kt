import khttp.Async
import khttp.get
import khttp.responses.Response
import org.awaitility.kotlin.await
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

fun writeBytes(filename: String, bytes: List<Byte>) {
    File(filename).writeBytes(bytes.toByteArray())
}

fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) {
    val url = "https://httpbin.org/bytes/1690?seed=1"

    var error: Throwable? = null
    var response: Response? = null

    Async.get(url, stream = true, onError = {
        error = this
    }, onResponse = {
        response = this
    })
    await.atMost(5, TimeUnit.SECONDS).until { response != null }

    error?.let { throw it }
    val iterator = response!!.lineIterator()
    val bytes = iterator.asSequence().toList().flatMap { it.toList() }
    val contentWithoutBytes =
        get(url).content.toList().filter { it != '\r'.code.toByte() && it != '\n'.code.toByte() }

    writeBytes("bytes", bytes)
    writeBytes("contentWithoutBytes", contentWithoutBytes)
    writeBytes("contentWithoutBytesFull", get(url).content.toList())
    assertEquals(contentWithoutBytes, bytes)
}



