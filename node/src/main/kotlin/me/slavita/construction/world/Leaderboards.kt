package me.slavita.construction.world

import me.slavita.construction.booster.format.BoosterFormatter
import me.slavita.construction.booster.format.ExperienceFormatter
import me.slavita.construction.booster.format.IncomeFormatter
import me.slavita.construction.booster.format.ReputationFormatter
import me.slavita.construction.protocol.GetLeaderboardPackage
import me.slavita.construction.ui.format.MoneyFormatter
import me.slavita.construction.ui.format.ProjectsFormatter
import me.slavita.construction.utils.log
import me.slavita.construction.utils.runAsync
import me.slavita.construction.utils.socket
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.DARK_GREEN
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.LIGHT_PURPLE
import org.bukkit.ChatColor.WHITE
import org.bukkit.ChatColor.YELLOW
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object Leaderboards {
    private val experience = Leaderboard("1", "Топ\n${WHITE}по ${AQUA}Опыту", "Опыт", 70.0, "$AQUA", ExperienceFormatter)
    private val totalBoosters = Leaderboard("2", "Топ\n${DARK_GREEN}Глобальных бустеров", "Бустеров", 60.0, "$DARK_GREEN", BoosterFormatter)
    private val projects = Leaderboard("3", "Топ\n${WHITE}по ${YELLOW}Проектам", "Проектов", 60.0, "$YELLOW", ProjectsFormatter)
    private val lastIncome = Leaderboard("4", "Топ\n${WHITE}по ${GREEN}Прибыли", "Доход", 80.0, "$GREEN", IncomeFormatter)
    private val money = Leaderboard("5", "Топ\n${WHITE}по ${GOLD}Монетам", "Монет", 80.0, "$GOLD", MoneyFormatter)
    private val reputation = Leaderboard("6", "Топ\n${WHITE}по ${LIGHT_PURPLE}Репутации", "Репутации", 70.0, "$LIGHT_PURPLE", ReputationFormatter)

    fun load() = runAsync {
        load("experience", experience)
        load("projects", projects)
        load("totalBoosters", totalBoosters)
        load("lastIncome", lastIncome)
        load("money", money)
        load("reputation", reputation)
    }

    private fun load(field: String, leaderboard: Leaderboard) = runAsync {
        try {
            socket.writeAndAwaitResponse<GetLeaderboardPackage>(GetLeaderboardPackage(field))[5, TimeUnit.SECONDS].run {
                leaderboard.update(top)
            }
        } catch (e: TimeoutException) {
            log("$field leaderboard load timeout")
        }
    }
}
