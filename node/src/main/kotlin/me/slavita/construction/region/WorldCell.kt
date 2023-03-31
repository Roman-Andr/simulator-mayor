package me.slavita.construction.region

import me.func.mod.world.Npc
import me.func.mod.world.Npc.onClick
import me.func.unit.Building
import me.func.world.Label
import me.slavita.construction.action.command.Command
import me.slavita.construction.app
import me.slavita.construction.utils.*
import me.slavita.construction.world.Box
import org.bukkit.block.BlockFace
import java.util.*

class WorldCell(val label: Label) {

    val box = Box(label.clone().add(1.0, -1.0, 1.0), label.clone().add(24.0, 47.0, 24.0))
    val face = label.tag.toBlockFace()
    val faceCenter = getFaceCenter(this)

    val stubBox = box.toFuncBox(app.mainWorld.map, yOffset = -60.0)
    val stub = Building(UUID.randomUUID(), "", "", 0.0, 0.0, 0.0, stubBox)

    val npcAction = hashMapOf<UUID, Command>()
    val npc = Npc.npc {
        yaw = face.toYaw()
        faceCenter.clone().addByFace(face, 1.0).run {
            this@npc.x = x
            this@npc.z = z
        }

        onClick {
            npcAction[it.player.uniqueId]?.tryExecute()
        }
    }

    fun allocateStub() {
        stub.allocate(box.min.clone().add(12.0, 0.0, 12.0))
    }
}
