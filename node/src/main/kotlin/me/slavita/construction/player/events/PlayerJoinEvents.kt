package me.slavita.construction.player.events

import me.func.mod.Anime
import me.func.mod.ui.scoreboard.ScoreBoard
import me.func.mod.util.after
import me.func.protocol.data.emoji.Emoji
import me.func.protocol.ui.indicator.Indicators
import me.slavita.construction.app
import me.slavita.construction.connection.ConnectionUtil.createChannel
import me.slavita.construction.multichat.MultiChatUtil
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinEvents : Listener {
    @EventHandler
    fun PlayerJoinEvent.handle() {
        MultiChatUtil.sendPlayerChats(player)
        app.addUser(player)
        player.teleport(app.mainWorld.getSpawn())
        createChannel(player)
        after (1) {
            Anime.hideIndicator(player, Indicators.HEALTH, Indicators.EXP, Indicators.HUNGER)
            ScoreBoard.builder()
                .key("scoreboard")
                .header("Стройка")
                .dynamic("Монеты") {
                    return@dynamic "${app.getUser(it).stats.money}§f ${Emoji.DOLLAR}"
                }
                .dynamic("Уровень") {
                    return@dynamic "${app.getUser(it).stats.level}§f ${Emoji.UP}"
                }
                .empty()
                .dynamic("Строителей") {
                    return@dynamic "${app.getUser(it).workers.size}"
                }
                .empty()
                .dynamic("Проектов") {
                    return@dynamic "${app.getUser(it).stats.totalProjects}"
                }
                .dynamic("Репутация") {
                    return@dynamic "${app.getUser(it).stats.reputation}"
                }
                .build().apply {
                    ScoreBoard.subscribe("scoreboard", player)
                    show(player)
                }
        }
    }
}