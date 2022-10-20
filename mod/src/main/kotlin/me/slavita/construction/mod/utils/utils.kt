package me.slavita.construction.mod.utils

import ru.cristalix.uiengine.UIEngine.clientApi

fun getWidth(string: String): Int = clientApi.fontRenderer().getStringWidth(string)