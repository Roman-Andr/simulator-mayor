package me.slavita.construction.ui

import me.slavita.construction.booster.format.*
import me.slavita.construction.prepare.IRegistrable
import me.slavita.construction.protocol.GetLeaderboardPackage
import me.slavita.construction.ui.format.MoneyFormatter
import me.slavita.construction.ui.format.ProjectsFormatter
import me.slavita.construction.utils.log
import me.slavita.construction.utils.runAsync
import me.slavita.construction.utils.socket
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object Leaderboards : IRegistrable {
    val experience = Leaderboard("1", "Топ\n§fпо §bОпыту", "Опыт", 70.0, "§b", ExperienceFormatter)
    val totalBoosters = Leaderboard("2", "Топ\n§2Глобальных бустеров", "Бустеров", 60.0, "§2", BoosterFormatter)
    val projects = Leaderboard("3", "Топ\n§fпо §eПроектам", "Проектов", 60.0, "§e", ProjectsFormatter)
    val lastIncome = Leaderboard("4", "Топ\n§fпо §aПрибыли", "Доход", 80.0, "§a", IncomeFormatter)
    val money = Leaderboard("5", "Топ\n§fпо §6Монетам", "Монет", 80.0, "§6", MoneyFormatter)
    val reputation = Leaderboard("6", "Топ\n§fпо §dРепутации", "Репутации", 70.0, "§d", ReputationFormatter)

    override fun register() {
        load()
    }

    fun load() = runAsync {
        load("experience", experience)
        load("projects", projects)
        load("totalBoosters", totalBoosters)
        load("lastIncome", lastIncome)
        load("money", money)
        load("reputation", reputation)
    }

    fun load(field: String, leaderboard: Leaderboard) = runAsync {
        try {
            socket.writeAndAwaitResponse<GetLeaderboardPackage>(GetLeaderboardPackage(field))[5, TimeUnit.SECONDS].run {
                leaderboard.update(top)
            }
        } catch (e: TimeoutException) {
            log("$field leaderboard load timeout")
        }
    }
}
