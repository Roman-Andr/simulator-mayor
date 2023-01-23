package me.slavita.construction.ui

import me.slavita.construction.app
import me.slavita.construction.protocol.LeaderboardItem
import me.slavita.construction.ui.format.IFormatter
import me.slavita.construction.utils.BlocksExtensions.toYaw
import me.slavita.construction.utils.CristalixUtil
import me.slavita.construction.utils.label
import org.bukkit.Location
import org.bukkit.block.BlockFace
import ru.cristalix.boards.bukkitapi.Boards
import java.util.*

class Leaderboard(
    labelTag: String,
    title: String,
    value: String,
    fieldSize: Double,
    val color: String,
    val formatter: IFormatter
) {
    val board = Boards.newBoard()

    init {
        board.apply {
            addColumn("#", 15.0)
            addColumn("Игрок", 140.0)
            addColumn(value, fieldSize)
            this.title = title
            location = label("board", labelTag)!!.apply {
                yaw = BlockFace.WEST.toYaw()
                y += 4
            }
            Boards.addBoard(this)
        }
    }

    fun update(values: ArrayList<LeaderboardItem>) {
        board.clearContent()
        values.forEachIndexed { index, value ->
            val uuid = UUID.fromString(value.uuid)
            board.addContent(
                uuid,
                (index + 1).toString(),
                CristalixUtil.getDisplayName(uuid),
                "${color}${formatter.format(value.value)}"
            )
        }
        board.updateContent()
    }
}
