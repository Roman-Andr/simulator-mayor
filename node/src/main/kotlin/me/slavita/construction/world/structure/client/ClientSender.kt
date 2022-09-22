package me.slavita.construction.world.structure.client

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.utils.SpecialColor
import me.slavita.construction.world.BlockProperties
import org.bukkit.Location
import org.bukkit.entity.Player

class ClientSender(val client: Player) {
    fun sendBlock(block: BlockProperties, allocation: Location, hasNext: Boolean) {
        val position = block.withOffset(allocation).position
        ModTransfer()
            .double(position.x.toDouble())
            .double(position.y.toDouble())
            .double(position.z.toDouble())
            .integer(block.type.id)
            .byte(block.data)
            .boolean(hasNext)
            .send("structure:update", client)
    }

    fun sendFinish() {
        ModTransfer()
            .send("structure:completed", client)
    }

    fun sendColor(color: SpecialColor) {
        ModTransfer()
            .integer(color.red)
            .integer(color.green)
            .integer(color.blue)
            .double(color.alpha)
            .send("structure:changeColor", client)
    }
}