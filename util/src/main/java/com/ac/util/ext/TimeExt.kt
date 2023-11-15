package com.ac.util.ext

import java.util.Calendar

fun isToday(millis: Long): Boolean {
    val wee: Long = getWeeOfToday()
    return millis >= wee && millis < wee + 86400000
}

private fun getWeeOfToday(): Long {
    val cal = Calendar.getInstance()
    cal[Calendar.HOUR_OF_DAY] = 0
    cal[Calendar.SECOND] = 0
    cal[Calendar.MINUTE] = 0
    cal[Calendar.MILLISECOND] = 0
    return cal.timeInMillis
}