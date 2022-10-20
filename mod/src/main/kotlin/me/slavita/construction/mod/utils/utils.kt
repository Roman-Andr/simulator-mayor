package me.slavita.construction.mod.utils

import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.utility.V3

fun getWidth(string: String): Int = clientApi.fontRenderer().getStringWidth(string)

fun Double.doubleVec(): V3 {
    return V3(this, this, this)
}