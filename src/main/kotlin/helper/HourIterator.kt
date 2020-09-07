package helper

import java.time.LocalTime

internal class HourIterator(
    startHour: LocalTime,
    private val endHourInclusive: LocalTime,
    private val stepHours: Long
) : Iterator<LocalTime> {
    private var currentHour = startHour

    override fun hasNext() = currentHour <= endHourInclusive

    override fun next(): LocalTime {
        val next = currentHour
        currentHour = currentHour.plusHours(stepHours)
        return next
    }
}

class HourProgression(
    override val start: LocalTime,
    override val endInclusive: LocalTime,
    private val stepHours: Long = 1
) : Iterable<LocalTime>, ClosedRange<LocalTime> {

    override fun iterator(): Iterator<LocalTime> = HourIterator(start, endInclusive, stepHours)

    infix fun step(hours: Long) = HourProgression(start, endInclusive, stepHours)
}

operator fun LocalTime.rangeTo(other: LocalTime) = HourProgression(this, other)