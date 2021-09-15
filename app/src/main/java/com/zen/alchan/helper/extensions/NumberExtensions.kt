package com.zen.alchan.helper.extensions

import android.content.Context
import androidx.annotation.PluralsRes
import com.zen.alchan.helper.utils.TimeUtil
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Int.getNumberFormatting(): String {
    return NumberFormat.getIntegerInstance().format(this)
}

fun Long.moreThanADay(): Boolean {
    return TimeUtil.getCurrentTimeInMillis() > this + 24 * 60 * 60 * 1000
}

fun Double.formatTwoDecimal(): String {
    return String.format("%.2f", this)
}

fun Int.showUnit(context: Context, @PluralsRes pluralResId: Int): String {
    return "$this ${context.resources.getQuantityString(pluralResId, this)}"
}

fun Int.displaySecondsInDayDateTimeFormat(): String {
    val dateFormat = SimpleDateFormat("E, dd MMM yyyy, hh:mm a", Locale.getDefault())
    val date = Date(this * 1000L)
    return dateFormat.format(date)
}

fun Int.convertSecondsToDays(): Int {
    return this / 3600 / 24 + 1
}

fun Int.convertSecondsToHours(): Int {
    return this / 3600
}

fun Int.convertSecondsToMinutes(): Int {
    return this / 60
}

fun Int.toHex(): String {
    return String.format("#%06X", 0xFFFFFF and this)
}

fun Int.toAlphaHex(): String {
    return String.format("#%08X", 0xFFFFFFFF and this.toLong())
}