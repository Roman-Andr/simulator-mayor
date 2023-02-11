package me.slavita.construction.ui.achievements

enum class AchievementType(
    val title: String,
    val placeholder: String,
    val itemKey: String,
    val itemValue: String,
    val formula: (level: Int) -> Long,
) {
    PROJECTS("Проекты", "Закончить %value% проектов", "other", "quests", { level ->
        if (level != 0) level * (((level / 20) + 1) * 5).toLong() else 1L
    }),
    WORKERS("Работники", "Нанять %value% работников", "other", "myfriends", { level ->
        if (level != 0) level * (((level / 20) + 1) * 5).toLong() else 1L
    }),
    FREELANCE("Фриланс", "Закончить %value% фриланс заказов", "other", "human", { level ->
        if (level != 0) level * (((level / 20) + 1) * 5).toLong() else 1L
    }),
    MONEY("Монеты", "Заработать %value% монет", "other", "coin3", { level ->
        if (level != 0) level * (((level / 10) + 1) * 5000).toLong() else 1L
    }),
    CITY_HALL("Мэрия", "Улучшить мэрию до %value% уровня", "other", "guild_bank", { level ->
        if (level != 0) level * (((level / 20) + 1) * 5).toLong() else 1L
    }),
    STORAGE("Склад", "Улучшить склад до %value% уровня", "other", "stock", { level ->
        if (level != 0) level * (((level / 20) + 1) * 5).toLong() else 1L
    }),
    BOUGHT_BLOCKS("Купленные блоки", "Купить в магазине %value% блоков", "skyblock", "settings", { level ->
        if (level != 0) level * (((level / 10) + 1) * 5).toLong() else 1L
    }),
}
