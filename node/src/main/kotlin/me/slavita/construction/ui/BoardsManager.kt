package me.slavita.construction.ui

import me.func.atlas.Atlas
import me.slavita.construction.app
import me.slavita.construction.booster.format.*
import me.slavita.construction.utils.BlocksExtensions.toYaw
import me.slavita.construction.utils.cristalixName
import me.slavita.construction.utils.runTimer
import me.slavita.construction.utils.toUUID
import org.bukkit.Location
import org.bukkit.block.BlockFace
import ru.cristalix.boards.bukkitapi.Boards
import java.util.*


object BoardsManager {
    var taskId = 0

    init {
        Atlas.find("boards").run {
            app.mainWorld.map.getLabels("board").forEach {
                createTop(
                    it.toCenterLocation(),
                    getString("boards.${it.tag}.field"),
                    getString("boards.${it.tag}.title"),
                    getString("boards.${it.tag}.value"),
                    getString("boards.${it.tag}.fieldSize").toDouble(),
                    getString("boards.${it.tag}.color"),
                    when (getString("boards.${it.tag}.formatter")) {
                        "MoneyFormatter"      -> MoneyFormatter
                        "ExperienceFormatter" -> ExperienceFormatter
                        "ProjectsFormatter"   -> ProjectsFormatter
                        "ReputationFormatter" -> ReputationFormatter
                        "BoosterFormatter"    -> BoosterFormatter
                        "IncomeFormatter"     -> IncomeFormatter
                        else                  -> EmptyFormatter
                    }
                )
            }
        }
    }

    private fun createTop(
        location: Location,
        field: String,
        title: String,
        value: String,
        fieldSize: Double,
        color: String,
        formatter: IFormatter,
    ) {
        val board = Boards.newBoard()
        board.addColumn("#", 15.0)
        board.addColumn("Игрок", 140.0)
        board.addColumn(value, fieldSize)
        board.title = title
        board.location = location.apply {
            yaw = BlockFace.WEST.toYaw()
            y += 4
        }
        Boards.addBoard(board)

        taskId = runTimer(0L, 10 * 20) {
            board.clearContent()
            app.kensuke.getLeaderboard(app.userManager, app.statScope, field, 10).thenAccept {
                it.forEach { entry ->
                    board.addContent(
                        UUID.fromString(entry.data.session.userId),
                        entry.position.toString(),
                        entry.data.session.userId.toUUID().cristalixName,
                        "${color}${formatter.format(entry.data.user.data)}"
                    )
                }
                board.updateContent()
            }
        }
    }
}