package me.slavita.construction.listener

import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.prepare.AbilityPrepare
import me.slavita.construction.prepare.BankAccountPrepare
import me.slavita.construction.prepare.CitiesPrepare
import me.slavita.construction.prepare.ConnectionPrepare
import me.slavita.construction.prepare.GuidePrepare
import me.slavita.construction.prepare.ItemCallbacksPrepare
import me.slavita.construction.prepare.PermissionsPrepare
import me.slavita.construction.prepare.PlayerWorldPrepare
import me.slavita.construction.prepare.ShowcasePrepare
import me.slavita.construction.prepare.StoragePrepare
import me.slavita.construction.prepare.TabPrepare
import me.slavita.construction.prepare.TagsPrepare
import me.slavita.construction.prepare.UIPrepare
import me.slavita.construction.prepare.WarningPrepare
import me.slavita.construction.utils.listener

object OnUserLoad : IRegistrable {
    override fun register() {
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
                ShowcasePrepare,
                WarningPrepare,
            ).forEach { it.prepare(user) }
        }
    }
}
