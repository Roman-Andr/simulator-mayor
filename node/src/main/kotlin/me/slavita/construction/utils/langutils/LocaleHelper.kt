package me.slavita.construction.utils.langutils

import org.bukkit.entity.Player

object LocaleHelper {
    fun getPlayerLanguage(player: Player?): String {
        return player!!.locale
    }
}