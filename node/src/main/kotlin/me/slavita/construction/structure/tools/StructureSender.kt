package me.slavita.construction.structure.tools

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.world.BlockProperties
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class StructureSender(val client: Player) {
    fun sendBlock(block: BlockProperties, offset: Location) {
        val position = block.withOffset(offset).position
        ModTransfer()
            .double(position.x.toDouble())
            .double(position.y.toDouble())
            .double(position.z.toDouble())
            .item(ItemStack(block.type, 1, 1, block.data))
            .boolean(block.colorable)
            .send("structure:currentBlock", client)
    }

    fun sendCompleted() {
        ModTransfer().send("structure:completed", client)
    }

    fun sendCooldownExpired() {
        ModTransfer().send("structure:cooldown", client)
    }
}