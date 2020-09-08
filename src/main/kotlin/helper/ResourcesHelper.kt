package helper

import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets.UTF_8
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

fun loadResource(resource: String): String {
    return try {
        object {}.javaClass.getResource(resource).readText(Charsets.UTF_8)
    } catch (e: Exception) {
        throw RuntimeException("Failed to load resource=$resource!", e)
    }
}

fun gzip(content: String): ByteArray {
    val bos = ByteArrayOutputStream()
    GZIPOutputStream(bos).bufferedWriter(UTF_8).use { it.write(content) }
    return bos.toByteArray()
}

fun ungzip(content: ByteArray): String =
    GZIPInputStream(content.inputStream()).bufferedReader(UTF_8).use { it.readText() }