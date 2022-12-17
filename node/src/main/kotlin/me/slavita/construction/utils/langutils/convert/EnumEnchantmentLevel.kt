package me.slavita.construction.utils.langutils.convert

import java.util.*

enum class EnumEnchantmentLevel(
    val level: Int,
    val unlocalizedName: String
) {
    LEVEL1(1, "enchantment.level.1"), LEVEL2(2, "enchantment.level.2"), LEVEL3(3, "enchantment.level.3"), LEVEL4(
        4,
        "enchantment.level.4"
    ),
    LEVEL5(5, "enchantment.level.5"), LEVEL6(6, "enchantment.level.6"), LEVEL7(7, "enchantment.level.7"), LEVEL8(
        8,
        "enchantment.level.8"
    ),
    LEVEL9(9, "enchantment.level.9"), LEVEL10(10, "enchantment.level.10");

    companion object {
        private val lookup: MutableMap<Int, EnumEnchantmentLevel> = HashMap()

        init {
            for (level in EnumSet.allOf(EnumEnchantmentLevel::class.java))
                lookup[level.level] = level
        }

        operator fun get(level: Int): EnumEnchantmentLevel? {
            return lookup[level]
        }
    }
}