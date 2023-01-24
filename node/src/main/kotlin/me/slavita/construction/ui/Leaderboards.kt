package me.slavita.construction.ui

import me.slavita.construction.booster.format.ExperienceFormatter
import me.slavita.construction.booster.format.ReputationFormatter
import me.slavita.construction.prepare.IRegistrable
import me.slavita.construction.protocol.GetLeaderboardPackage
import me.slavita.construction.utils.log
import me.slavita.construction.utils.runAsync
import me.slavita.construction.utils.socket
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object Leaderboards : IRegistrable {
    val experienceLeaderboard = Leaderboard("1", "Топ\n§fпо §bОпыту", "Опыт", 70.0, "§b", ExperienceFormatter)
    val projectsLeaderboard = Leaderboard("3", "Топ\n§fпо §eПроектам", "Проектов", 60.0, "§e", ReputationFormatter)

    override fun register() {
        load()
    }

    fun load() = runAsync {
        try {
            socket.writeAndAwaitResponse<GetLeaderboardPackage>(GetLeaderboardPackage())[5, TimeUnit.SECONDS].run {
                experienceLeaderboard.update(experience)
                projectsLeaderboard.update(projects)
                log("leaderboard loaded")
            }
        } catch (e: TimeoutException) {
            log("leaderboard load timeout")
        }
    }
}
