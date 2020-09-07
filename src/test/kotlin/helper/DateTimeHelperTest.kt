package helper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

internal class DateTimeHelperTest {

    @Test
    fun `convert iso8601 to timestamp`() {
        val isoDate = "2018-05-13T03:01:01+00:00"
        val isoDate2 = "2018-05-13T03:01:01Z"
        val expectedTimestamp: Long = 1526180461

        assertEquals(expectedTimestamp, getTimestampFromISO8601(isoDate))
        assertEquals(expectedTimestamp, getTimestampFromISO8601(isoDate2))
    }

    @Test
    fun `build date with time iso`() {
        val localDate = LocalDate.of(2020, 1, 1)
        val localTime = LocalTime.of(12, 0, 0)
        val expected = "2020-01-01T12:00:00+00:00"

        assertEquals(expected, generateIsoFromDateTime(localDate, localTime))
    }
}