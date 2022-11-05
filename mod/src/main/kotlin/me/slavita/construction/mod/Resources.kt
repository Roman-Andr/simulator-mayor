package me.slavita.construction.mod

import dev.xdark.clientapi.resource.ResourceLocation

enum class Resources(
	val source: ResourceLocation,
) {
	INFO(ResourceLocation.of("minecraft", "mcpatcher/cit/others/badges/info1.png")),
	ARROW(ResourceLocation.of("minecraft", "mcpatcher/cit/others/badges/arrow_down.png")),
	CANCEL(ResourceLocation.of("minecraft", "mcpatcher/cit/others/badges/close.png")),
	PLUS(ResourceLocation.of("minecraft", "mcpatcher/cit/others/badges/add.png")),
}