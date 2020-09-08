package data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDate
import java.time.LocalTime

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

        assertEquals(feedLogsStringFromFile, feedLogsJsonString)
        assertEquals(feedLogs, feedLogsFromFile)
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