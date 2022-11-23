package me.slavita.construction.ui

import me.func.atlas.Atlas
import me.slavita.construction.app
import me.slavita.construction.utils.CristalixUtil
import me.slavita.construction.utils.extensions.BlocksExtensions.toYaw
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.BlockFace
import ru.cristalix.boards.bukkitapi.Boards
import java.util.*


object BoardsManager {
    init {
        Atlas.find("boards").run {
            app.mainWorld.map.getLabels("board").forEach {
                createTop(
                    it.toCenterLocation(),
                    getString("boards.${it.tag}.title"),
                    getString("boards.${it.tag}.value"),
                    getString("boards.${it.tag}.color")
                )
            }
        }
    }

    private fun createTop(
        location: Location,
        title: String,
        value: String,
        color: String,
    ) {
        val blocks = Boards.newBoard()
        blocks.addColumn("#", 10.0)
        blocks.addColumn("Игрок", 150.0)
        blocks.addColumn(value, 55.0)
        blocks.title = title
        blocks.location = location.apply {
            yaw = BlockFace.WEST.toYaw()
            y += 4
        }
        Boards.addBoard(blocks)

        Bukkit.server.scheduler.scheduleSyncRepeatingTask(app, {
            blocks.clearContent()
            listOf(
                "303c31eb-2c69-11e8-b5ea-1cb72caa35fd",
                "ae7abc6b-d142-11e8-8374-1cb72caa35fd",
                "602036a1-3255-11ed-98d1-1cb72caa35fd",
                "e2543a0a-5799-11e9-8374-1cb72caa35fd",
                "307264a1-2c69-11e8-b5ea-1cb72caa35fd",
                "ba821208-6b64-11e9-8374-1cb72caa35fd",
                "306f45f5-2c69-11e8-b5ea-1cb72caa35fd",
                "6326e7a1-8dd1-11e9-80c4-1cb72caa35fd",
                "ee476051-dc55-11e8-8374-1cb72caa35fd",
                "303dc644-2c69-11e8-b5ea-1cb72caa35fd",
            ).forEachIndexed { index, s ->
                blocks.addContent(
                    UUID.randomUUID(),
                    index.toString(),
                    CristalixUtil.getDisplayName(UUID.fromString(s)),
                    "${color}3",
                )
            }
        }, 0L, 10 * 20L)
    }
}