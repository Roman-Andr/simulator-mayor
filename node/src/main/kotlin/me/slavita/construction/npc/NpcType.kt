package me.slavita.construction.npc

import me.slavita.construction.action.command.menu.ControlPanelMenu
import me.slavita.construction.action.command.menu.bank.BankMainMenu
import me.slavita.construction.action.command.menu.worker.WorkerBuyMenu
import me.slavita.construction.action.command.menu.worker.WorkerTeamMenu
import org.bukkit.entity.Player

enum class NpcType(
    val title: String,
    val labelTag: String,
    val skinType: SkinType,
    val skin: String,
    val itemKey: String,
    val itemValue: String,
    val action: (player: Player) -> Unit,
) {
    WORKERS(
        "Покупка работников",
        "workers",
        SkinType.UUID,
        "e2543a0a-5799-11e9-8374-1cb72caa35fd",
        "other",
        "stats",
        { WorkerBuyMenu(it).tryExecute() }
    ),
    TEAM(
        "Работники",
        "market",
        SkinType.UUID,
        "307264a1-2c69-11e8-b5ea-1cb72caa35fd",
        "other",
        "stats",
        { WorkerTeamMenu(it).tryExecute() }
    ),
    MENU(
        "Меню",
        "menu",
        SkinType.UUID,
        "303c31eb-2c69-11e8-b5ea-1cb72caa35fd",
        "other",
        "stats",
        { ControlPanelMenu(it).tryExecute() }
    ),
    BANK(
        "Банк",
        "bank",
        SkinType.UUID,
        "ba821208-6b64-11e9-8374-1cb72caa35fd",
        "other",
        "stats",
        { BankMainMenu(it).tryExecute() }
    ),
}