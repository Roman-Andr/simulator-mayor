package me.slavita.construction.ui.npc

import me.func.mod.world.Npc
import me.func.mod.world.Npc.location
import me.func.mod.world.Npc.onClick
import me.func.mod.world.Npc.skin
import me.func.protocol.world.npc.NpcBehaviour
import me.slavita.construction.action.menu.city.LocationsMenu
import me.slavita.construction.action.menu.city.StorageMenu
import me.slavita.construction.action.menu.general.ControlPanelMenu
import me.slavita.construction.action.menu.general.DailyMenu
import me.slavita.construction.action.menu.general.GuideDialog
import me.slavita.construction.action.menu.project.ActiveProjectsMenu
import me.slavita.construction.action.menu.worker.WorkerMenu
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.STORAGE_URL
import me.slavita.construction.utils.labels
import me.slavita.construction.utils.newBanner
import me.slavita.construction.utils.toUUID
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.inventory.EquipmentSlot
import kotlin.reflect.full.primaryConstructor

object NpcManager : IRegistrable {
    private val labels = labels("npc")

    override fun register() {
        NpcSamples.values().forEach { npc ->
            labels.filter { it.tag == npc.label }.forEach { label ->
                newBanner(npc.banner, label)

                Npc.npc {
                    location(
                        label.toCenterLocation().apply {
                            y = label.blockY.toDouble()
                        }
                    )
                    name = ""
                    when (npc.skinType) {
                        SkinType.UUID -> skin(npc.skin.toUUID())
                        SkinType.URL  -> skin("$STORAGE_URL/skin/${npc.skin}")
                    }
                    behaviour = NpcBehaviour.STARE_AND_LOOK_AROUND
                    onClick {
                        when (npc.action) {
                            "WorkerMenu" -> WorkerMenu::class
                            "ControlPanelMenu" -> ControlPanelMenu::class
                            "BankMainMenu" -> me.slavita.construction.action.menu.bank.BankMainMenu::class
                            "ActiveProjectsMenu" -> ActiveProjectsMenu::class
                            "DailyMenu" -> DailyMenu::class
                            "GuideDialog" -> GuideDialog::class
                            "StorageMenu" -> StorageMenu::class
                            "LocationsMenu" -> LocationsMenu::class
                            else -> ControlPanelMenu::class
                        }.primaryConstructor!!.call(it.player).tryExecute()
                    }
                }.slot(EquipmentSlot.HAND, CraftItemStack.asNMSCopy(Icons.get(npc.itemKey, npc.itemValue)))
            }
        }
    }
}
