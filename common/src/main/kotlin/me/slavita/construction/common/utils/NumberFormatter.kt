package me.slavita.construction.common.utils

import java.math.BigInteger
import java.util.*

object NumberFormatter {
    private val NAMES = arrayOf(
        "тыс",
        "млн",
        "млрд",
        "трлн",
        "квдрлн",
        "квнтлн",
        "скcтлн",
        "сптлн",
    )
    private val THOUSAND = BigInteger.valueOf(1000)
    private var map: NavigableMap<BigInteger, String> = TreeMap()

    init {
        for (i in NAMES.indices) {
            map[THOUSAND.pow(i + 1)] = NAMES[i]
        }
    }

    fun toMoneyFormat(arg: Long): String {
        BigInteger.valueOf(arg).apply {
            val (key, value) = map.floorEntry(this) ?: return this.toString()
            val rounded = this.divide(key.divide(THOUSAND)).toInt() / 1000.0

            return if (rounded % 1 == 0.0) "${rounded.toInt()} $value" else "$rounded $value"
        }
    }
}