package me.slavita.construction.action.command.menu.lootbbox

import implario.humanize.Humanize
import me.func.mod.Anime
import me.func.mod.conversation.data.LootDrop
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer.Choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import me.slavita.construction.utils.music.MusicExtension.playSound
import me.slavita.construction.utils.music.MusicSound
import me.slavita.construction.utils.user
import me.slavita.construction.worker.WorkerGenerator
import me.slavita.construction.worker.WorkerRarity
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.entity.Player
import ru.cristalix.core.formatting.Formatting.error

class ChoiceLootboxAmount(player: Player, val rarity: WorkerRarity) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return Choicer(
                title = "${AQUA}${BOLD}Купить лутбоксы",
                description = "Выберите необходимое количество лутбоксов",
                storage = listOf(
                    Pair(1, "common_key"),
                    Pair(1, "rare_key"),
                    Pair(1, "mific_key")
                ).map {
                    button {
                        item = ItemIcons.get("other", it.second)
                        title = "${it.first} ${Humanize.plurals("лутбокс", "лутбокса", "лутбоксов", it.first)}"
                        description = (rarity.price * it.first).toMoneyIcon()
                        hint = "Купить"
                        onClick { _, _, _ ->
                            if (!player.user.canPurchase(rarity.price * it.first)) {
                                Anime.close(player)
                                player.playSound(MusicSound.DENY)
                                player.killboard(error("Недостаточно средств!"))
                                return@onClick
                            }
                            val workers = WorkerGenerator.generate(rarity, it.first)
                            val lootDrop = mutableListOf<LootDrop>()
                            workers.forEach {
                                lootDrop.add(LootDrop(rarity.getIcon(), it.name, rarity.dropRare))
                            }
                            OpenWorker(this@user, *workers.toTypedArray()).tryExecute()
                        }
                    }
                }.toMutableList()
            )
        }
    }
}