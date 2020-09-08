package data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import helper.calculatePercentage
import helper.gzip
import helper.ungzip
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

internal class FeederKtTest {

    @Test
    fun `generate feedLogs`() {
        val feedLogs = defaultGenerator()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val dataAdapter = ResponseJsonAdapter<FeedLog>(moshi, arrayOf(FeedLog::class.java))
        val feedLogsJsonString = dataAdapter.toJson(feedLogs)

        val path = "src/test/resources/feedlogs.json"
        val file = File(path)
        val absolutePath = file.absolutePath

        val feedLogsStringFromFile = File(absolutePath).readText(Charsets.UTF_8)
        val feedLogsFromFile = dataAdapter.fromJson(feedLogsStringFromFile)

        println("Json from generator size: ${feedLogsJsonString.length}")
        println("Json from file size: ${feedLogsStringFromFile.length}")

        assertEquals(feedLogsStringFromFile, feedLogsJsonString)
        assertEquals(feedLogs, feedLogsFromFile)
    }

    @Test
    fun `compare size gzip`() {
        val feedLogs = defaultGenerator()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val dataAdapter = ResponseJsonAdapter<FeedLog>(moshi, arrayOf(FeedLog::class.java))
        val feedLogsJsonString = dataAdapter.toJson(feedLogs)
        println("size of original: ${feedLogsJsonString.length}")

        val feedLogsZipped = gzip(feedLogsJsonString)
        println("size zipped: ${feedLogsZipped.size}")

        val feedLogsUnzipped = ungzip(feedLogsZipped)
        println("size unzipped: ${feedLogsUnzipped.length}")

        val decreaseSize = feedLogsJsonString.length - feedLogsZipped.size
        val percentageDecrease = calculatePercentage(decreaseSize.toDouble(), feedLogsJsonString.length.toDouble())
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