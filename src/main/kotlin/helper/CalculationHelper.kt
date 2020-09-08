package helper

import kotlin.math.roundToInt

fun calculatePercentage(obtained: Double, total: Double): Double {
    val result = obtained * 100 / total
    val result3Digits = (result * 1000.0).roundToInt() / 1000.0
    return (result3Digits * 100.0).roundToInt() / 100.0
}

fun decreasePercentage(resultObtained: Double, total: Double): Double {
    val decrease = total - resultObtained
    return calculatePercentage(decrease, total)
}