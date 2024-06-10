package com.matzuu.batterymonitor.utils

import java.text.SimpleDateFormat
import java.util.Locale


private val dateformatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
private val timeformatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

fun dateFormatter(time: Long) : String {
    return dateformatter.format(time)
}

fun timeFormatter(time: Long) : String {
    return timeformatter.format(time)

}