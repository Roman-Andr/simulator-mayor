package me.slavita.construction.mod.showcase

import ru.cristalix.uiengine.utility.V3
import java.util.*

data class ShowcaseData(
	val title: String,
	val min: V3,
	val max: V3,
	val uuid: UUID,
)