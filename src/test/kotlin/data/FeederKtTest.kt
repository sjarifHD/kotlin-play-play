package data

import helper.decreasePercentage
import helper.gzip
import helper.ungzip
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

        assert(feedLogsUnzipped == feedLogsJsonString)
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