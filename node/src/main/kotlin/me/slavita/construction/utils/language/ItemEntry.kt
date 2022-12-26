package me.slavita.construction.utils.language

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

data class ItemEntry(var material: Material?, var metadata: Int = 0) {

    private constructor() : this(null, 0)

    constructor(itemStack: ItemStack) : this(itemStack.getType(), itemStack.getDurability().toInt())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ItemEntry) return false
        return metadata == other.metadata && material === other.material
    }

    override fun hashCode(): Int {
        var result = material.hashCode()
        result = 31 * result + metadata
        return result
    }

    override fun toString(): String {
        return material.toString() + " " + metadata
    }

    companion object {
        private val inst = ItemEntry()
        fun from(itemStack: ItemStack): ItemEntry {
            return try {
                val result = inst.copy()
                result.material = itemStack.getType()
                result.metadata = itemStack.getDurability().toInt()
                result
            } catch (e: CloneNotSupportedException) {
                ItemEntry(itemStack)
            }
        }

        fun from(material: Material?, meta: Int): ItemEntry {
            return try {
                val result = inst.copy()
                result.material = material
                result.metadata = meta
                result
            } catch (e: CloneNotSupportedException) {
                ItemEntry(material, meta)
            }
        }

        fun from(material: Material?): ItemEntry {
            return from(material, 0)
        }
    }
}