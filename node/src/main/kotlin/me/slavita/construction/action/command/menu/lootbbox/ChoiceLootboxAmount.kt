package me.slavita.construction.action.command.menu.lootbbox

import implario.humanize.Humanize
import me.func.mod.Anime
import me.func.mod.conversation.data.LootDrop
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer.Choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import me.slavita.construction.utils.music.MusicExtension.playSound
import me.slavita.construction.utils.music.MusicSound
import me.slavita.construction.worker.WorkerGenerator
import me.slavita.construction.worker.WorkerRarity
import org.bukkit.ChatColor.RED
import org.bukkit.entity.Player

class ChoiceLootboxAmount(player: Player, val rarity: WorkerRarity) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@{
            return Choicer(
                title = "Купить лутбоксы",
                description = "Выберите необходимое количество лутбоксов",
                storage = mutableListOf<ReactiveButton>().apply {
                    listOf(1, 5, 10).forEach {
                        add(button {
                            item = ItemIcons.get("other", "new_lvl_rare_close")
                            title = "$it ${Humanize.plurals("лутбокс", "лутбокса", "лутбоксов", it)}"
                            description = (rarity.price * it).toMoneyIcon()
                            hint = "Купить"
                            onClick { _, _, _ ->
                                if (!app.getUser(player).canPurchase(rarity.price * it)) {
                                    Anime.close(player)
                                    player.killboard("${RED}Недостаточно средств!")
                                    player.playSound(MusicSound.DENY)
                                    return@onClick
                                }
                                val workers = WorkerGenerator.generate(rarity, it)
                                val lootDrop = mutableListOf<LootDrop>()
                                workers.forEach {
                                    lootDrop.add(LootDrop(rarity.getIcon(), it.name, rarity.dropRare))
                                }
                                OpenWorker(this@user, *workers.toTypedArray()).tryExecute()
                            }
                        })
                    }
                })
        }
    }
}