package me.slavita.construction.ui.npc

import me.slavita.construction.ui.BannerSamples
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GREEN

enum class NpcSamples(
    val title: String,
    val label: String,
    val skin: String,
    val skinType: SkinType,
    val itemKey: String,
    val itemValue: String,
    val action: String,
    val banner: BannerSamples
) {
    WORKER(
        "${GREEN}Работники",
        "1",
        "workers.png",
        SkinType.URL,
        "other",
        "myfriends",
        "WorkerMenu",
        BannerSamples.WORKER
    ),
    DAILY(
        "${GREEN}Ежедневные награды",
        "2",
        "daily.png",
        SkinType.URL,
        "other",
        "quests",
        "DailyMenu",
        BannerSamples.DAILY
    ),
    LOCATIONS(
        "${GREEN}Проводник",
        "3",
        "locations.png",
        SkinType.URL,
        "alpha",
        "islands",
        "LocationsMenu",
        BannerSamples.LOCATIONS
    ),
    PROJECTS(
        "${AQUA}Активные проекты",
        "4",
        "projects.png",
        SkinType.URL,
        "other",
        "book",
        "ActiveProjectsMenu",
        BannerSamples.PROJECTS
    ),
    BANK(
        "${GOLD}Банкир",
        "5",
        "banker.png",
        SkinType.URL,
        "other",
        "coin3",
        "BankMainMenu",
        BannerSamples.BANK
    ),
    GUIDE(
        "${GREEN}Старина Джо",
        "guide",
        "guide.png",
        SkinType.URL,
        "other",
        "search",
        "GuideDialog",
        BannerSamples.GUIDE
    ),
}
