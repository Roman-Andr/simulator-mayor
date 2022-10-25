package me.slavita.construction.world

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.craftbukkit.v1_12_R1.block.CraftBlock
import org.bukkit.inventory.ItemStack

open class ItemProperties(val type: Material, val data: Byte) {
	companion object {
		fun fromBlock(block: Block) =
			ItemProperties(block.type, (block as CraftBlock).nmsBlock.getDropData(block.data0).toByte())
	}

	fun equalsItem(item: ItemStack?): Boolean {
		return item != null && item.getType() == type && item.getData().data == data
	}

	fun createItemStack(amount: Int): ItemStack {
		return ItemStack(type, amount, 0, data)
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as ItemProperties

		if (type != other.type) return false
		if (data != other.data) return false

		return true
	}

	override fun hashCode(): Int {
		var result = type.hashCode()
		result = 31 * result + data
		return result
	}
}