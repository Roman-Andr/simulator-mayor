package me.slavita.construction.mod.uimod.storage

import me.slavita.construction.mod.uimod.templates.BoxData
import ru.cristalix.uiengine.utility.V3

data class StorageData(
    val title1: String,
    val min1: V3,
    val max1: V3,
) : BoxData(title1, min1, max1)
