package me.slavita.construction.npc

import me.slavita.construction.action.command.LootboxCommand
import me.slavita.construction.action.command.ProjectsCommand
import me.slavita.construction.action.command.TeamCommand
import org.bukkit.entity.Player

enum class NpcType(
    val title: String,
    val labelTag: String,
    val skinType: SkinType,
    val skin: String,
    val itemKey: String,
    val itemValue: String,
    val action: (player: Player) -> Unit,
) {
    WORKERS(
        "Покупка работников",
        "workers",
        SkinType.UUID,
        "e2543a0a-5799-11e9-8374-1cb72caa35fd",
        "other",
        "stats",
        { LootboxCommand(it).tryExecute() }
    ),
    PROJECTS(
        "Проекты",
        "projects",
        SkinType.UUID,
        "ba821208-6b64-11e9-8374-1cb72caa35fd",
        "other",
        "stats",
        { ProjectsCommand(it).tryExecute() }
    ),
    TEAM(
        "Работники",
        "market",
        SkinType.UUID,
        "307264a1-2c69-11e8-b5ea-1cb72caa35fd",
        "other",
        "guild_members",
        { TeamCommand(it).tryExecute() }
    ),
}