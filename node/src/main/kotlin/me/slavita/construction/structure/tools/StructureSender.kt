package me.slavita.construction.structure.tools

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.world.StructureBlock
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class StructureSender(val client: Player) {
    fun sendBlock(block: StructureBlock, offset: Location) {
        val position = block.withOffset(offset).position
        ModTransfer()
            .double(position.x.toDouble())
            .double(position.y.toDouble())
            .double(position.z.toDouble())
            .item(ItemStack(block.type, 1, 1, block.data))
            .send("structure:currentBlock", client)
    }

    fun sendHide() {
        ModTransfer().send("structure:hide", client)
    }

    fun sendCooldownExpired() {
        ModTransfer().send("structure:cooldown", client)
    }
}