package me.slavita.construction.listener

import me.slavita.construction.prepare.*
import me.slavita.construction.utils.listener

object OnUserLoad {
    init {
        listener<LoadUserEvent> {
            listOf(
                UIPrepare,
                TagsPrepare,
                CitiesPrepare,
                PlayerWorldPrepare,
                TabPrepare,
                ConnectionPrepare,
                PermissionsPrepare,
                ItemCallbacksPrepare,
                BankAccountPrepare,
                GuidePrepare,
                StoragePrepare,
                AbilityPrepare,
                DailyRewardsPrepare,
                ShowcasePrepare,
                WarningPrepare,
            ).forEach { it.prepare(user) }
        }
    }
}
