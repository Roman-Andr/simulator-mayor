package me.slavita.construction.utils

import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.DARK_GRAY
import org.bukkit.ChatColor.DARK_GREEN
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.RED
import org.bukkit.ChatColor.WHITE
import org.bukkit.ChatColor.YELLOW

val WORKER_INFO = """
        ${GOLD}${BOLD}Характеристики:
          ${YELLOW}Имя $GRAY»
            ${WHITE}Наименование рабочего
            ${WHITE}в вашей команде
          
          ${DARK_GREEN}Редкость $GRAY»
            ${WHITE}Показывает на сколько
            ${WHITE}характеристики рабочего хороши
          
          ${GOLD}Уровень $GRAY»
            ${WHITE}Показывает уровень прокачки
            ${WHITE}рабочего и влияет
            ${WHITE}на все его характеристики
          
          ${AQUA}Скорость $GRAY»
            ${WHITE}Количество блоков,
            ${WHITE}которые ставит рабочий
            ${WHITE}за секунду
          
          ${GREEN}Надёжность $GRAY»
            ${WHITE}Влияет на то, как часто
            ${WHITE}будет ломаться здания,
            ${WHITE}построенные этим рабочим
          
          ${RED}Жадность $GRAY»
            ${WHITE}Влияет на награду
            ${WHITE}за окончания постройки
            ${WHITE}здания этим рабочим
""".trimIndent()
val SHOWCASE_INFO = """
    ${GOLD}${BOLD}Магазин:
        Обновление цен происходит
        во всех магазинах раз в определённое время
        В разных магазинах цены отличаются
""".trimIndent()
val PROJECTS_INFO = """
    ${GOLD}${BOLD}Проекты:
        Каждый проект соответствует определённой стройке
""".trimIndent()
val STORAGE_INFO = """
    ${GOLD}${BOLD}Склад:
        Здесь хранятся ваши блоки, купленные в магазине
        Вы также можете класть сюда другие ваши блоки
""".trimIndent()
val LOCATIONS_INFO = """
    ${GOLD}${BOLD}Локации:
        Вы можете перемещаться между открытыми локациями
""".trimIndent()
val SETTINGS_INFO = """
    ${GOLD}${BOLD}Настройки:
        Вы можете включить или выключить необходимые опции игры
""".trimIndent()
val TAGS_INFO = """
    ${GOLD}${BOLD}Теги:
        Тег это надпись после вашего ника, которая показывается в чате и табе игры
""".trimIndent()
val DONATE_INFO = """
    ${GOLD}${BOLD}Платные возможности:
        Здесь вы можете купить необходимые улучшения за кристаллики
""".trimIndent()
val MENU_INFO = """
    ${GOLD}${BOLD}Главное меню:
        Здесь вы можете выбрать необходимый раздел
""".trimIndent()
val ACHIEVEMENTS_INFO = """
    ${GOLD}${BOLD}Достижения:
        Здесь вы можете посмотреть все достижения,
        а также те, которые вы получили
""".trimIndent()
val BANK_INFO = """
    ${GOLD}${BOLD}Банк:
        Здесь вы можете брать кредиты под процент,
        таким образом быстрее развиваясь
""".trimIndent()
val REWARDS_INFO = """
    ${GOLD}${BOLD}Ежедневные награды:
""".trimIndent()
val CITY_HALL_INFO = """
    ${GOLD}${BOLD}Мэрия:
        Главное здание в городе. 
        Оно приносит пассивный доход.
        Ты также можешь улучшать мэрию
""".trimIndent()
val STRUCTURES_INFO = """
    ${GOLD}${BOLD}Здания:
""".trimIndent()
val PROJECTS_MENU = """
    ${DARK_GRAY}Быстрый доступ: /projects (K)
""".trimIndent()
val LOCATIONS_MENU = """
    ${DARK_GRAY}Быстрый доступ: /locations (L)
""".trimIndent()
val WORKERS_MENU = """
    ${DARK_GRAY}Быстрый доступ: /workers (M)
""".trimIndent()
val TAGS_MENU = """
    ${DARK_GRAY}Быстрый доступ: /tags
""".trimIndent()
val ACHIEVEMENTS_MENU = """
    ${DARK_GRAY}Быстрый доступ: /achievements
""".trimIndent()
val STORAGE_MENU = """
    ${DARK_GRAY}Быстрый доступ: /storage
""".trimIndent()
val CITY_HALL_MENU = """
    ${DARK_GRAY}Быстрый доступ: /cityhall
""".trimIndent()
val FREELANCE_MENU = """
    ${DARK_GRAY}Быстрый доступ: /freelance
""".trimIndent()
val REWARDS_MENU = """
    ${DARK_GRAY}Быстрый доступ: /rewards
""".trimIndent()
val SETTINGS_MENU = """
    ${DARK_GRAY}Быстрый доступ: /settings
""".trimIndent()
