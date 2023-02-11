package me.slavita.construction.common.utils

object TimeFormatter {
    fun toTimeFormat(duration: Long): String {
//        val millis: Long = duration % 1000
        val second: Long = duration / 1000 % 60
        val minute: Long = duration / (1000 * 60) % 60
        val hour: Long = duration / (1000 * 60 * 60) % 24
        var answer = ""
        if (hour != 0L) answer += "${hour}ч "
        if (minute != 0L) answer += "${minute}м "
        if (second != 0L || (hour == 0L && minute == 0L)) answer += "${second}с"
        return answer
    }
}
