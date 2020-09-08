package data

import com.daveanthonythomas.moshipack.MoshiPack
import com.squareup.moshi.Moshi
import com.squareup.moshi.MsgpackIntByte
import com.squareup.moshi.MsgpackWriter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import helper.decreasePercentage
import helper.gzip
import helper.ungzip
import okio.Buffer
import okio.BufferedSource
import okio.ByteString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

internal class FeederKtTest {

    private val feedLogs = FeedLogData.defaultGenerator()
    private val feedLogsJsonString = FeedLogData.toJsonString(feedLogs)

    @Test
    fun `get feedLogs`() {
        val path = "src/test/resources/feedlogs.json"
        val file = File(path)
        val absolutePath = file.absolutePath

        val feedLogsStringFromFile = File(absolutePath).readText(Charsets.UTF_8)
        val feedLogsFromFile = FeedLogData.jsonStringToModel(feedLogsStringFromFile)

        println("Json from generator size: ${feedLogsJsonString.length}")
        println("Json from file size: ${feedLogsStringFromFile.length}")

        assertEquals(feedLogsStringFromFile, feedLogsJsonString)
        assertEquals(feedLogs, feedLogsFromFile)
    }

    @Test
    fun `compare size gzip`() {
        println("size of original: ${feedLogsJsonString.length} bytes")

        val feedLogsZipped = gzip(feedLogsJsonString)
        println("size zipped: ${feedLogsZipped.size} bytes")

        val feedLogsUnzipped = ungzip(feedLogsZipped)
        println("size unzipped: ${feedLogsUnzipped.length} bytes")

        val percentageDecrease =
            decreasePercentage(feedLogsZipped.size.toDouble(), feedLogsJsonString.length.toDouble())
        println("size decreased: $percentageDecrease%")

        assertEquals(feedLogsJsonString, feedLogsUnzipped)
    }

    @Test
    fun `compare size messagepack`() {
        val path = "src/test/resources/feedlogs.json"
        val file = File(path)
        val absolutePath = file.absolutePath

        val feedLogsStringFromFile = File(absolutePath).readText(Charsets.UTF_8)
        println("size of original: ${feedLogsStringFromFile.length} bytes")

        val packed: BufferedSource = MoshiPack.jsonToMsgpack(feedLogsStringFromFile)
        println("size packed: ${packed.buffer.size()} bytes")

        // masih gagal sih ini
//        val unpacked = MoshiPack.msgpackToJson(packed.readByteArray())
//        println("size unpacked: ${unpacked.length} bytes")
//        println(unpacked)

        val percentageDecrease =
            decreasePercentage(packed.buffer.size().toDouble(), feedLogsStringFromFile.length.toDouble())
        println("size decreased: $percentageDecrease%")
    }

    @Test
    fun `check path`() {
        val path = "src/test/resources/feedlogs.json"

        val file = File(path)
        val absolutePath = file.absolutePath

        println(absolutePath)

        assertTrue(absolutePath.endsWith("src/test/resources/feedlogs.json"))
    }
}
