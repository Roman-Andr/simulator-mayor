package me.slavita.construction.commands

import org.bukkit.entity.Player

object CommandsManager {
    val timeData = mutableMapOf<Player, MutableMap<Command, CustomCommand>>()

    fun register(player: Player) {
        timeData[player] = mutableMapOf()
        Command.values().forEach {
            timeData[player]?.set(it, CustomCommand(5, it))
        }
    }
}