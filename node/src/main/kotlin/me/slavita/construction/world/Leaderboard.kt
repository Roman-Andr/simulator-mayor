package me.slavita.construction.world

import me.slavita.construction.booster.format.IFormatter
import me.slavita.construction.protocol.LeaderboardItem
import me.slavita.construction.utils.cristalixName
import me.slavita.construction.utils.label
import me.slavita.construction.utils.labels
import me.slavita.construction.utils.toBlockFace
import me.slavita.construction.utils.toUUID
import me.slavita.construction.utils.toYaw
import org.bukkit.block.BlockFace
import ru.cristalix.boards.bukkitapi.Boards

class Leaderboard(
    labelTag: String,
    title: String,
    value: String,
    fieldSize: Double,
    val color: String,
    val formatter: IFormatter,
) {
    val board = Boards.newBoard()

    init {
        board.apply {
            addColumn("#", 15.0)
            addColumn("Игрок", 140.0)
            addColumn(value, fieldSize)
            this.title = title
            location = labels("board").find { it.tag.contains(labelTag) }!!.apply {
                yaw = tag.split("-")[1].toBlockFace().toYaw()
                y += 4
            }
            Boards.addBoard(this)
        }
    }

    fun update(values: ArrayList<LeaderboardItem>) {
        board.clearContent()
        values.forEachIndexed { index, value ->
            val uuid = value.uuid.toUUID()
            board.addContent(
                uuid,
                (index + 1).toString(),
                uuid.cristalixName,
                "${color}${formatter.format(value.value)}"
            )
        }
        board.updateContent()
    }
}
