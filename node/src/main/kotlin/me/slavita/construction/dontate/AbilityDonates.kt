package me.slavita.construction.dontate

import me.slavita.construction.dontate.ability.FlyAbility
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.dontate.ability.NoLimitTeleportAbility
import java.util.concurrent.TimeUnit

object FlyDonate : AbilityDonate("Полёт", "Вы сможете летать без ограничений", 49, FlyAbility)

object NoLimitTeleportDonate : AbilityDonate("Телепорт без ограничений", "Вы сможете перемещаться по локация без ограничей", 49, NoLimitTeleportAbility)

object SpeedBooster : BoosterDonate("Глобальный бустер\n+1.25x скорости", "30 минут", 49, 30, TimeUnit.MINUTES, BoosterType.SPEED_BOOSTER)

object ReputationBooster : BoosterDonate("Глобальный бустер\n+1.25x репутации", "30 минут", 49, 30, TimeUnit.MINUTES, BoosterType.REPUTATION_BOOSTER)

object MoneyBooster : BoosterDonate("Глобальный бустер скорости\n+1.25x монет", "30 минут", 49, 30, TimeUnit.MINUTES, BoosterType.MONEY_BOOSTER)

object IncomeBooster : BoosterDonate("Глобальный бустер скорости\n+1.25x прибыли", "30 минут", 49, 30, TimeUnit.MINUTES, BoosterType.INCOME_BOOSTER)

object ExpBooster : BoosterDonate("Глобальный бустер скорости\n+1.25x опыта", "30 минут", 49, 30, TimeUnit.MINUTES, BoosterType.EXP_BOOSTER)