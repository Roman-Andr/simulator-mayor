package me.slavita.construction.npc

import me.slavita.construction.commands.Command

enum class NpcType(
    val title: String,
    val labelTag: String,
    val command: Command,
    val skinType: SkinType,
    val skin: String,
    val itemKey: String,
    val itemValue: String,
) {
    WORKERS(
        "Покупка работников",
        "workers",
        Command.LOOTBOX,
        SkinType.UUID,
        "e2543a0a-5799-11e9-8374-1cb72caa35fd",
        "other",
        "stats"),
    PROJECTS(
        "Проекты",
        "projects",
        Command.PROJECTS,
        SkinType.UUID,
        "ba821208-6b64-11e9-8374-1cb72caa35fd",
        "other",
        "stats"),
    TEAM(
        "Работники",
        "market",
        Command.TEAM,
        SkinType.UUID,
        "307264a1-2c69-11e8-b5ea-1cb72caa35fd",
        "other",
        "guild_members",
    ),
}