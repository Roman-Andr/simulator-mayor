package me.slavita.construction.ui.npc

import me.slavita.construction.ui.BannerSamples

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
        "§aРаботники",
        "1",
        "workers.png",
        SkinType.URL,
        "other",
        "myfriends",
        "WorkerMenu",
        BannerSamples.WORKER
    ),
    DAILY(
        "§aЕжедневные награды",
        "2",
        "daily.png",
        SkinType.URL,
        "other",
        "quests",
        "DailyMenu",
        BannerSamples.DAILY
    ),
    LOCATIONS(
        "§aПроводник",
        "3",
        "locations.png",
        SkinType.URL,
        "alpha",
        "islands",
        "LocationsMenu",
        BannerSamples.LOCATIONS
    ),
    PROJECTS(
        "§bАктивные проекты",
        "4",
        "projects.png",
        SkinType.URL,
        "other",
        "book",
        "ActiveProjectsMenu",
        BannerSamples.PROJECTS
    ),
    BANK(
        "§6Банкир",
        "5",
        "banker.png",
        SkinType.URL,
        "other",
        "coin3",
        "BankMainMenu",
        BannerSamples.BANK
    ),
    GUIDE(
        "§aСтарина Джо",
        "guide",
        "guide.png",
        SkinType.URL,
        "other",
        "search",
        "GuideDialog",
        BannerSamples.GUIDE
    ),
}