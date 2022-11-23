package me.slavita.construction.dontate

import me.slavita.construction.booster.BoosterType
import me.slavita.construction.dontate.ability.FlyAbility
import me.slavita.construction.dontate.ability.NoLimitTeleportAbility
import me.slavita.construction.dontate.ability.ShopTipsAbility
import java.util.concurrent.TimeUnit

object FlyDonate : AbilityDonate(
    "Полёт",
    "Вы сможете летать без ограничений",
    49, FlyAbility
)

object NoLimitTeleportDonate : AbilityDonate(
    "Телепорт без ограничений",
    "Вы сможете перемещаться по локация без ограничей",
    49,
    NoLimitTeleportAbility
)

object CheapestShopsDonate : AbilityDonate(
    "Подсветка самого\nвыгодного магазина",
    "Вам будет сообщаться номер магазина с самыми низкими ценами",
    49,
    ShopTipsAbility
)

object BankLoyaltyDonate : AbilityDonate(
    "Лояльность банка",
    "Вам будет даваться больше времени для погашения кредита",
    49,
    ShopTipsAbility
)

object SpeedBooster : BoosterDonate(
    "Глобальный бустер\n+1.25x скорости",
    "30 минут",
    49,
    30,
    TimeUnit.MINUTES,
    BoosterType.SPEED_BOOSTER
)

object ReputationBooster : BoosterDonate(
    "Глобальный бустер\n+1.25x репутации",
    "30 минут",
    49,
    30,
    TimeUnit.MINUTES,
    BoosterType.REPUTATION_BOOSTER
)

object LuckBooster : BoosterDonate(
    "Глобальный бустер\n+1.25x удачи",
    "Удача влияет на выпадение рабочих\n30 минут",
    49,
    30,
    TimeUnit.MINUTES,
    BoosterType.LUCK_BOOSTER
)

object MoneyBooster : BoosterDonate(
    "Глобальный бустер скорости\n+1.25x монет",
    "30 минут",
    49,
    30,
    TimeUnit.MINUTES,
    BoosterType.MONEY_BOOSTER
)

object IncomeBooster : BoosterDonate(
    "Глобальный бустер скорости\n+1.25x прибыли",
    "30 минут",
    49,
    30,
    TimeUnit.MINUTES,
    BoosterType.INCOME_BOOSTER
)

object ExpBooster : BoosterDonate(
    "Глобальный бустер скорости\n+1.25x опыта",
    "30 минут",
    49,
    30,
    TimeUnit.MINUTES,
    BoosterType.EXP_BOOSTER
)