package me.slavita.construction.npc

enum class NpcType(
    val title: String,
    val labelTag: String,
    val command: String,
    val skin: String,
    val itemKey: String,
    val itemValue: String,
) {
    PROJECTS(
        "Проекты",
        "projects",
        "lootbox",
        "e2543a0a-5799-11e9-8374-1cb72caa35fd",
        "other",
        "stats"),
    WORKERS(
        "Работники",
        "workers",
        "team",
        "ba821208-6b64-11e9-8374-1cb72caa35fd",
        "other",
        "guild_members",
    ),
    BANK(
        "Банк",
        "bank",
        "bank",
        "307264a1-2c69-11e8-b5ea-1cb72caa35fd",
        "other",
        "bank",
    ),
}