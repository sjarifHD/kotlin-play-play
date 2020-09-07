package helper

import java.time.*
import java.time.format.DateTimeFormatter

const val DATE_FORMAT_ISO8601UTC = "yyyy-MM-dd'T'HH:mm:ssxxx"

internal fun parseIsoOffset(text: CharSequence) = DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(text, Instant::from)

fun getTimestampFromISO8601(dateConvert: String) = parseIsoOffset(dateConvert).epochSecond

fun generateIsoFromDateTime(localDate: LocalDate, localTime: LocalTime): String {
    val isoTime = OffsetDateTime.of(localDate, localTime, ZoneOffset.UTC)
    val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_ISO8601UTC)
    return isoTime.format(formatter)
}