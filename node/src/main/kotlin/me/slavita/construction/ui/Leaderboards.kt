package me.slavita.construction.ui

import me.slavita.construction.protocol.GetLeaderboardPackage
import me.slavita.construction.ui.format.ExpFormatter
import me.slavita.construction.ui.format.ReputationFormatter
import me.slavita.construction.utils.runAsync
import me.slavita.construction.utils.socket
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object Leaderboards {
    val experienceLeaderboard = Leaderboard("1", "Топ\n§fпо §bОпыту", "Опыт", 70.0, "§b", ExpFormatter())
    val projectsLeaderboard = Leaderboard("3", "Топ\n§fпо §eПроектам", "Проектов", 60.0, "§e", ReputationFormatter())

    init {
        load()
    }

    fun load() = runAsync {
        try {
            socket.writeAndAwaitResponse<GetLeaderboardPackage>(GetLeaderboardPackage())[5, TimeUnit.SECONDS].run {
                experienceLeaderboard.update(experience)
                projectsLeaderboard.update(projects)
                println("leaderboard loaded")
            }
        } catch (e: TimeoutException) {
            println("leaderboard load timeout")
        }
    }
}
