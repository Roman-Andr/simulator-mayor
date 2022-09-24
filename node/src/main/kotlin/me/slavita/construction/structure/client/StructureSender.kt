package me.slavita.construction.structure.client

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.utils.SpecialColor
import me.slavita.construction.world.BlockProperties
import org.bukkit.Location
import org.bukkit.entity.Player

class StructureSender(val client: Player) {
    fun sendBlock(block: BlockProperties, offset: Location) {
        val position = block.withOffset(offset).position
        ModTransfer()
            .double(position.x.toDouble())
            .double(position.y.toDouble())
            .double(position.z.toDouble())
            .integer(block.type.id)
            .byte(block.data)
            .send("structure:currentBlock", client)
    }

    fun sendCompleted() {
        ModTransfer().send("structure:completed", client)
    }

    fun sendFrameColor(color: SpecialColor) {
        ModTransfer()
            .integer(color.red)
            .integer(color.green)
            .integer(color.blue)
            .double(color.alpha)
            .send("structure:frameColor", client)
    }
}