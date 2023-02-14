package me.slavita.construction.mod.uimod.showcase

import me.slavita.construction.mod.uimod.templates.BoxData
import ru.cristalix.uiengine.utility.V3

data class ShowcaseData(
    val id: Int,
    val title1: String,
    val min1: V3,
    val max1: V3,
) : BoxData(title1, min1, max1)
