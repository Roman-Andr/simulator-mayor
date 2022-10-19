package me.slavita.construction.npc

import me.func.atlas.Atlas
import me.func.mod.world.Npc
import me.func.mod.world.Npc.location
import me.func.mod.world.Npc.onClick
import me.func.mod.world.Npc.skin
import me.func.protocol.world.npc.NpcBehaviour
import me.slavita.construction.action.Command
import me.slavita.construction.action.command.menu.ControlPanelMenu
import me.slavita.construction.action.command.menu.DailyMenu
import me.slavita.construction.action.command.menu.bank.BankMainMenu
import me.slavita.construction.action.command.menu.lootbbox.BuyLootboxMenu
import me.slavita.construction.action.command.menu.lootbbox.UserLootboxesMenu
import me.slavita.construction.action.command.menu.project.ActiveProjectsMenu
import me.slavita.construction.action.command.menu.worker.WorkerTeamMenu
import me.slavita.construction.app
import me.slavita.construction.ui.menu.ItemIcons
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.inventory.EquipmentSlot
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

object NpcManager {
    private val labels = app.mainWorld.getNpcLabels()

    init {
        load()
    }

    private fun load() {
        Atlas.find("npc").getMapList("npc").forEach { values ->
            val title = values.entries.elementAt(0).value as String
            val labelTag = values.entries.elementAt(1).value as String
            val skinType = values.entries.elementAt(2).value as String
            val skin = values.entries.elementAt(3).value as String
            val itemKey = values.entries.elementAt(4).value as String
            val itemValue = values.entries.elementAt(5).value as String
            val action = values.entries.elementAt(6).value as String

            val commands = mapOf<String, KClass<out Command>>(
                "BuyLootboxMenu" to BuyLootboxMenu::class,
                "WorkerTeamMenu" to WorkerTeamMenu::class,
                "ControlPanelMenu" to ControlPanelMenu::class,
                "BankMainMenu" to BankMainMenu::class,
                "UserLootboxesMenu" to UserLootboxesMenu::class,
                "ActiveProjectsMenu" to ActiveProjectsMenu::class,
                "DailyMenu" to DailyMenu::class,
            )
            Npc.npc {
                labels.find { it.tag == labelTag }?.let { location(it) }
                name = title
                when(skinType) {
                    "uuid" -> skin(UUID.fromString(skin))
                    "url" -> skin(skin)
                }
                behaviour = NpcBehaviour.STARE_AND_LOOK_AROUND
                onClick {
                    commands[action]!!.primaryConstructor!!.call(it.player).tryExecute()
                }
            }.slot(EquipmentSlot.HAND, CraftItemStack.asNMSCopy(ItemIcons.get(itemKey, itemValue)))
        }
    }
}