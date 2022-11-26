package me.slavita.construction.dontate

import me.func.protocol.data.color.GlowColor
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.dontate.ability.FlyAbility
import me.slavita.construction.dontate.ability.NoLimitTeleportAbility
import me.slavita.construction.dontate.ability.ShopTipsAbility
import me.slavita.construction.ui.menu.ItemIcons
import org.bukkit.inventory.ItemStack
import java.util.concurrent.TimeUnit

enum class Donates(val donate: Donate, val displayItem: ItemStack, val backgroudColor: GlowColor) {
    FlyDonate(
        AbilityDonate(
            "Полёт",
            "Вы сможете летать без ограничений",
            49, FlyAbility
        ),
        ItemIcons.get("other", ""),
        GlowColor.GREEN
    ),
    NoLimitTeleportDonate(
        AbilityDonate(
            "Телепорт без ограничений",
            "Вы сможете перемещаться по локация без ограничей",
            49,
            NoLimitTeleportAbility
        ),
        ItemIcons.get("other", ""),
        GlowColor.GREEN
    ),
    CheapestShopsDonate(
        AbilityDonate(
            "Подсветка самого\nвыгодного магазина",
            "Вам будет показываться магазин с самыми низкими ценами",
            49,
            ShopTipsAbility
        ),
        ItemIcons.get("other", ""),
        GlowColor.GREEN
    ),
    BankLoyaltyDonate(
        AbilityDonate(
            "Лояльность банка",
            "Вам будет даваться больше времени для погашения кредита",
            49,
            ShopTipsAbility
        ),
        ItemIcons.get("other", ""),
        GlowColor.GREEN
    ),
    SpeedBooster(
        BoosterDonate(
            "Глобальный бустер\n+1.25x скорости",
            "30 минут",
            49,
            30,
            TimeUnit.MINUTES,
            BoosterType.SPEED_BOOSTER
        ),
        ItemIcons.get("other", ""),
        GlowColor.GREEN
    ),
    ReputationBooster(
        BoosterDonate(
            "Глобальный бустер\n+1.25x репутации",
            "30 минут",
            49,
            30,
            TimeUnit.MINUTES,
            BoosterType.REPUTATION_BOOSTER
        ),
        ItemIcons.get("other", ""),
        GlowColor.GREEN
    ),
    LuckBooster(
        BoosterDonate(
            "Глобальный бустер\n+1.25x удачи",
            "Удача влияет на выпадение рабочих\n30 минут",
            49,
            30,
            TimeUnit.MINUTES,
            BoosterType.LUCK_BOOSTER
        ),
        ItemIcons.get("other", ""),
        GlowColor.GREEN
    ),
    MoneyBooster(
        BoosterDonate(
            "Глобальный бустер скорости\n+1.25x монет",
            "30 минут",
            49,
            30,
            TimeUnit.MINUTES,
            BoosterType.MONEY_BOOSTER
        ),
        ItemIcons.get("other", ""),
        GlowColor.GREEN
    ),
    IncomeBooster(
        BoosterDonate(
            "Глобальный бустер скорости\n+1.25x прибыли",
            "30 минут",
            49,
            30,
            TimeUnit.MINUTES,
            BoosterType.INCOME_BOOSTER
        ),
        ItemIcons.get("other", ""),
        GlowColor.GREEN
    ),
    ExpBooster(
        BoosterDonate(
            "Глобальный бустер скорости\n+1.25x опыта",
            "30 минут",
            49,
            30,
            TimeUnit.MINUTES,
            BoosterType.EXP_BOOSTER
        ),
        ItemIcons.get("other", ""),
        GlowColor.GREEN
    )
}