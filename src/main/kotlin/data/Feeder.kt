package data

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.LocalDate
import java.time.LocalTime
import helper.*
import java.time.Period
import kotlin.streams.toList

@JsonClass(generateAdapter = true)
data class Response<Any>(
    val success: Boolean,
    val data_count: Int,
    val data: List<Any>
)

@JsonClass(generateAdapter = true)
data class FeedLog(
    val timestamp: Long,
    val amount: Int,
    val trigger: Int,
    val pond_id: Int = 1234567
)

fun generateFeedLog(
    startHour: LocalTime,
    endHour: LocalTime,
    stepHours: Long,
    startDate: LocalDate,
    endDate: LocalDate,
    stepDays: Int,
    amount: Int
): Response<FeedLog> {
    val hours = startHour..endHour step stepHours
    val dates = startDate.datesUntil(endDate, Period.ofDays(stepDays)).toList()

    val feedLogs = mutableListOf<FeedLog>()
    for (date in dates) {
        for (hour in hours) {
            val dateTimeIso = generateIsoFromDateTime(date, hour)
            feedLogs.add((FeedLog(getTimestampFromISO8601(dateTimeIso), amount, 3)))
        }
    }
    feedLogs.removeLast()
    feedLogs.removeLast()

    return Response(true, feedLogs.size, feedLogs)
}

fun defaultGenerator(): Response<FeedLog> {
    val startHour = LocalTime.of(0, 0, 0)
    val endHour = LocalTime.of(15, 0, 0)
    val startDate = LocalDate.of(2020, 1, 5)
    val endDate = LocalDate.of(2020, 2, 8)

    return generateFeedLog(startHour, endHour, 6, startDate, endDate, 1, 1250000)
}

fun getFromFile(): Response<FeedLog> {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val dataAdapter = ResponseJsonAdapter<FeedLog>(moshi, arrayOf(FeedLog::class.java))

    return try {
        val feedLogsStringFromFile = loadResource("feedlogs.json")
        dataAdapter.fromJson(feedLogsStringFromFile)!!
    } catch (e: Exception) {
        throw e
    }
}