package com.example.newsapp.shared.extensions

import java.util.*
import java.util.concurrent.*

fun Date.difference(): String {
    val now = Calendar.getInstance().time
    val diff: Long = now.time - this.time
    TimeUnit.MILLISECONDS.toDays(diff).let { days ->
        if (days > 0) {
            return "${days}D"
        }
    }
    TimeUnit.MILLISECONDS.toHours(diff).let { hours ->
        if (hours > 0) {
            return "${hours}H"
        }
    }
    TimeUnit.MILLISECONDS.toMinutes(diff).let { minutes ->
        if (minutes > 0) {
            return "${minutes}M"
        }
    }
    TimeUnit.MILLISECONDS.toSeconds(diff).let { seconds ->
        if (seconds > 0) {
            return "${seconds}S"
        }
    }
    return ""
}