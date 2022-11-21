package me.slavita.construction.ui

import me.func.atlas.Atlas
import me.slavita.construction.app
import me.slavita.construction.utils.extensions.BlocksExtensions.toYaw
import me.slavita.construction.utils.labels
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.BlockFace
import ru.cristalix.boards.bukkitapi.Boards
import java.util.*


object BoardsManager {
    init {
        Atlas.find("boards").run {
            app.mainWorld.map.getLabels("board").forEach {
                createTop(it.toCenterLocation(), getString("boards.${it.tag}.title"), getString("boards.${it.tag}.value"))
            }
        }
    }

    private fun createTop(
        location: Location,
        title: String,
        value: String,
    ) {
        val blocks = Boards.newBoard()
        blocks.addColumn("#", 10.0)
        blocks.addColumn("Игрок", 150.0)
        blocks.addColumn(value, 55.0)
        blocks.title = title
        blocks.location = location.apply {
            yaw = BlockFace.WEST.toYaw()
            y += 8
        }
        Boards.addBoard(blocks)

        Bukkit.server.scheduler.scheduleSyncRepeatingTask(app, {
            blocks.clearContent()
            blocks.addContent(
                UUID.randomUUID(),
                "1",
                "2",
                "3",
            )
        }, 0L, 10 * 20L)
    }
}