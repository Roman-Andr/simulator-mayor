package me.slavita.construction.npc

import me.func.atlas.Atlas
import me.func.mod.world.Npc
import me.func.mod.world.Npc.location
import me.func.mod.world.Npc.onClick
import me.func.mod.world.Npc.skin
import me.func.protocol.world.npc.NpcBehaviour
import me.slavita.construction.action.command.menu.*
import me.slavita.construction.action.command.menu.bank.BankMainMenu
import me.slavita.construction.action.command.menu.city.LocationsMenu
import me.slavita.construction.action.command.menu.project.ActiveProjectsMenu
import me.slavita.construction.action.command.menu.worker.WorkerMenu
import me.slavita.construction.banner.BannerUtil.loadBanner
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.labels
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.inventory.EquipmentSlot
import java.util.*
import kotlin.reflect.full.primaryConstructor

object NpcManager {
    private val labels = labels("npc")
    val storageUrl = "https://storage.c7x.ru/${System.getProperty("storage.user")}/construction/skin/";

    init {
        Atlas.find("npc").getMapList("npc").forEach { values ->
            val title = values["title"] as String
            val labelTag = values["labelTag"] as String
            val skinType = values["skinType"] as String
            val skin = values["skin"] as String
            val itemKey = values["itemKey"] as String
            val itemValue = values["itemValue"] as String
            val action = values["action"] as String

            labels.filter { it.tag == labelTag }.forEach { label ->
                loadBanner(values["banner"] as Map<*, *>, label)

                Npc.npc {
                    location(label.toCenterLocation().apply {
                        y = label.blockY.toDouble()
                    })
                    name = title
                    when (skinType) {
                        "uuid" -> skin(UUID.fromString(skin))
                        "url"  -> skin(storageUrl + skin)
                    }
                    behaviour = NpcBehaviour.STARE_AND_LOOK_AROUND
                    onClick {
                        when (action) {
                            "WorkerMenu"         -> WorkerMenu::class
                            "ControlPanelMenu"   -> ControlPanelMenu::class
                            "BankMainMenu"       -> BankMainMenu::class
                            "ActiveProjectsMenu" -> ActiveProjectsMenu::class
                            "DailyMenu"          -> DailyMenu::class
                            "GuideDialog"        -> GuideDialog::class
                            "StorageMenu"        -> StorageMenu::class
                            "LocationsMenu"      -> LocationsMenu::class
                            else                 -> ControlPanelMenu::class
                        }.primaryConstructor!!.call(it.player).tryExecute()
                    }
                }.slot(EquipmentSlot.HAND, CraftItemStack.asNMSCopy(ItemIcons.get(itemKey, itemValue)))
            }
        }
    }
}