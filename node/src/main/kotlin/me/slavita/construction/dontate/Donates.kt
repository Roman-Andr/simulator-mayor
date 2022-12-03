package me.slavita.construction.dontate

import me.func.protocol.data.color.GlowColor
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.dontate.ability.FlyAbility
import me.slavita.construction.dontate.ability.NoLimitTeleportAbility
import me.slavita.construction.dontate.ability.ShopTipsAbility
import me.slavita.construction.ui.Formatter.toCriMoney
import me.slavita.construction.ui.menu.ItemIcons
import org.bukkit.ChatColor.*
import org.bukkit.inventory.ItemStack
import java.util.concurrent.TimeUnit

enum class Donates(val donate: Donate, val displayItem: ItemStack, val backgroudColor: GlowColor) {
    FLY_DONATE(
        AbilityDonate(
            "${AQUA}${BOLD}Полёт",
            """
                ${GOLD}Возможность:
                  ${AQUA}Полёт
                
                Нажмите чтобы купить за ${89.toCriMoney()}
            """.trimIndent(),
            89, FlyAbility
        ),
        ItemIcons.get("other", "friend_game"),
        GlowColor.CIAN_LIGHT
    ),
    NO_LIMIT_TELEPORT_DONATE(
        AbilityDonate(
            "${GOLD}${BOLD}Телепорт\n${GOLD}${BOLD}без ограничений",
            """
                ${GOLD}Возможность:
                  Перемещение по локациям
                  ${AQUA}без ограничений
                
                Нажмите чтобы купить за ${49.toCriMoney()}
            """.trimIndent(),
            49,
            NoLimitTeleportAbility
        ),
        ItemIcons.get("alpha", "islands"),
        GlowColor.ORANGE_LIGHT
    ),
    BANK_LOYALTY_DONATE(
        AbilityDonate(
            "${GOLD}${BOLD}Лояльность\n${GOLD}${BOLD}банка",
            """
                ${AQUA}Описание:
                  Вам будет даваться больше ${GOLD}времени
                  для погашения ${LIGHT_PURPLE}кредита
                
                Нажмите чтобы купить за ${59.toCriMoney()}
            """.trimIndent(),
            59,
            ShopTipsAbility
        ),
        ItemIcons.get("other", "guild_bank"),
        GlowColor.YELLOW
    ),
    CHEAPEST_SHOPS_DONATE(
        AbilityDonate(
            "${GREEN}${BOLD}Подсветка\n${GREEN}${BOLD}Выгодного\n${GREEN}${BOLD}Магазина",
            """
                ${AQUA}Описание:
                  Вам будет показываться ${AQUA}магазин
                  с ${GOLD}самыми низкими ценами
                
                Нажмите чтобы купить за ${69.toCriMoney()}
            """.trimIndent(),
            69,
            ShopTipsAbility
        ),
        ItemIcons.get("alpha", "home1"),
        GlowColor.GREEN
    ),
    LUCK_BOOSTER(
        BoosterDonate(
            "${GOLD}${BOLD}Глобальный бустер удачи\n+1.25x\n${GRAY}30 минут",
            """
                ${AQUA}Описание:
                  Активирует ${LIGHT_PURPLE}глобальный бустер
                  ${GOLD}+1.25x ${WHITE}к множителю ${GOLD}удачи
                  на ${GOLD}30 минут
                
                Нажмите чтобы купить за ${69.toCriMoney()}
            """.trimIndent(),
            69,
            30,
            TimeUnit.MINUTES,
            BoosterType.LUCK_BOOSTER
        ),
        ItemIcons.get("other", "new_booster_2"),
        GlowColor.ORANGE_LIGHT
    ),
    REPUTATION_BOOSTER(
        BoosterDonate(
            "${LIGHT_PURPLE}${BOLD}Глобальный бустер репутации\n+1.25x\n${GRAY}30 минут",
            """
                ${AQUA}Описание:
                  Активирует ${LIGHT_PURPLE}глобальный бустер
                  ${RED}+1.25x ${WHITE}к множителю ${RED}репутации
                  на ${GOLD}30 минут
                
                Нажмите чтобы купить за ${69.toCriMoney()}
            """.trimIndent(),
            69,
            30,
            TimeUnit.MINUTES,
            BoosterType.REPUTATION_BOOSTER
        ),
        ItemIcons.get("other", "new_booster_2", true),
        GlowColor.PURPLE_LIGHT
    ),
    INCOME_BOOSTER(
        BoosterDonate(
            "${GREEN}${BOLD}Глобальный бустер прибыли\n+1.25x\n${GRAY}30 минут",
            """
                ${AQUA}Описание:
                  Активирует ${LIGHT_PURPLE}глобальный бустер
                  ${GREEN}+1.25x ${WHITE}к множителю ${GREEN}прибыли
                  на ${GOLD}30 минут
                
                Нажмите чтобы купить за ${79.toCriMoney()}
            """.trimIndent(),
            79,
            30,
            TimeUnit.MINUTES,
            BoosterType.INCOME_BOOSTER
        ),
        ItemIcons.get("other", "new_booster_1"),
        GlowColor.GREEN_LIGHT
    ),
    MONEY_BOOSTER(
        BoosterDonate(
            "${GOLD}${BOLD}Глобальный бустер монет\n+1.25x\n${GRAY}30 минут",
            """
                ${AQUA}Описание:
                  Активирует ${LIGHT_PURPLE}глобальный бустер
                  ${GOLD}+1.25x ${WHITE}к множителю ${GOLD}монет
                  на ${GOLD}30 минут
                
                Нажмите чтобы купить за ${79.toCriMoney()}
            """.trimIndent(),
            79,
            30,
            TimeUnit.MINUTES,
            BoosterType.MONEY_BOOSTER
        ),
        ItemIcons.get("other", "new_booster_1", true),
        GlowColor.YELLOW
    ),
    SPEED_BOOSTER(
        BoosterDonate(
            "${AQUA}${BOLD}Глобальный бустер скорости\n+1.25x\n${GRAY}30 минут",
            """
                ${AQUA}Описание:
                  Активирует ${LIGHT_PURPLE}глобальный бустер
                  ${AQUA}+1.25x ${WHITE}к множителю ${AQUA}скорости
                  на ${GOLD}30 минут
                
                Нажмите чтобы купить за ${69.toCriMoney()}
            """.trimIndent(),
            69,
            30,
            TimeUnit.MINUTES,
            BoosterType.SPEED_BOOSTER
        ),
        ItemIcons.get("other", "achievements_many_rare"),
        GlowColor.CIAN
    ),
    EXP_BOOSTER(
        BoosterDonate(
            "${AQUA}${BOLD}Глобальный бустер опыта\n+1.25x\n${GRAY}30 минут",
            """
                ${AQUA}Описание:
                  Активирует ${LIGHT_PURPLE}глобальный бустер
                  ${AQUA}+1.25x ${WHITE}к множителю ${AQUA}опыта
                  на ${GOLD}30 минут
                
                Нажмите чтобы купить за ${69.toCriMoney()}
            """.trimIndent(),
            69,
            30,
            TimeUnit.MINUTES,
            BoosterType.EXP_BOOSTER
        ),
        ItemIcons.get("other", "achievements_many_rare", true),
        GlowColor.CIAN_LIGHT
    ),
    BEGINNER_PACK(
        BoosterPackDonate(
            "${GREEN}${BOLD}Пак Новичёк",
            """
                ${GREEN}При покупке:
                  Активирует ${LIGHT_PURPLE}все глобальные бустеры
                  ${GOLD}на 1 час
                 
                Нажмите чтобы купить за ${RED}${STRIKETHROUGH}868${WHITE} ${799.toCriMoney()}
            """.trimIndent(),
            799,
            1,
            TimeUnit.HOURS,
            *BoosterType.values()
        ),
        ItemIcons.get("other", "achievements"),
        GlowColor.GREEN_LIGHT
    ),
    EXPERT_PACK(
        BoosterPackDonate(
            "${GOLD}${BOLD}Пак Эксперт",
            """
                ${GREEN}При покупке:
                  Активирует ${LIGHT_PURPLE}все глобальные бустеры
                  ${GOLD}на 3 часа
                 
                Нажмите чтобы купить за ${RED}${STRIKETHROUGH}2604${WHITE} ${2099.toCriMoney()}
            """.trimIndent(),
            2099,
            6,
            TimeUnit.HOURS,
            *BoosterType.values()
        ),
        ItemIcons.get("other", "achievements_rare", true),
        GlowColor.ORANGE_LIGHT
    )
}