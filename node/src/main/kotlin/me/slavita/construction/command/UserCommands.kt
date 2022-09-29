package me.slavita.construction.command

import me.func.mod.Anime
import me.func.mod.conversation.data.LootDrop
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.Glow
import me.func.mod.ui.menu.selection.Selection
import me.func.mod.util.command
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.app
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.utils.extensions.LoggerUtils.fine
import me.slavita.construction.worker.WorkerGenerator
import me.slavita.construction.worker.WorkerRarity
import java.util.*

object UserCommands {
    init {
        command("lootbox") { player, _ ->
            val user = app.getUser(player)
            val storage = mutableListOf<ReactiveButton>()
            WorkerRarity.values().forEach {
                val canBuy = user.stats.money >= it.price
                val iconItem = ItemIcons.get(it.iconKey, it.iconValue, it.iconMaterial)
                storage.add(
                    ReactiveButton()
                    .item(iconItem)
                    .title(it.title)
                    .description(it.description)
                    .price(it.price)
                    .hint(if (canBuy) "Купить" else "Недостаточно\nсредств")
                    .onClick { clickedPlayer, _, _ ->
                        if (canBuy) {
                            val worker = WorkerGenerator.generate(it)
                            Anime.openLootBox(clickedPlayer, LootDrop(iconItem, worker.name, it.dropRare))
                            Glow.animate(player, 1.0, GlowColor.GREEN)
                            user.workers.add(worker)
                        }
                        else {
                            Anime.close(player)
                            Glow.animate(player, 1.0, GlowColor.RED)
                        }
                    }
                )
            }
            val menu = Selection(
                UUID.randomUUID(),
                title = "Покупка строителей",
                money = "Ваш баланс ${user.stats.money}",
                rows = 3,
                columns = 3,
                storage = storage
            )
            Anime.close(player)
            menu.open(player)
        }

        command("team") { player, _ ->
            val user = app.getUser(player)
            val storage = mutableListOf<ReactiveButton>()
            val menu = Selection(
                UUID.randomUUID(),
                title = "Ваши строители",
                money = "Ваш баланс ${user.stats.money}",
                rows = 4,
                columns = 5,
                storage = storage
            )
            user.workers.filter { it.active }.sortedBy { it.rarity.price }.reversed()
                .plus(user.workers.filter { !it.active }.sortedBy { it.rarity.price }.reversed().asIterable()).forEach {
                    val iconItem = ItemIcons.get(it.rarity.iconKey, it.rarity.iconValue, it.rarity.iconMaterial)
                    storage.add(
                        ReactiveButton()
                        .item(iconItem)
                        .title(it.name)
                        .hover("§aИмя: ${it.name}\n" +
                                "§eРедкость: ${it.rarity.title}\n" +
                                "§bУровень: ${it.skill}\n" +
                                "§3Надёжность: ${it.reliability}\n" +
                                "§cЖадность: ${it.rapacity.title}")
                        .hint(if (it.active) "Активный" else "Не выбран")
                        .special(it.active)
                        .onClick { _, _, _ ->
                            it.active = !it.active
                            Anime.close(player)
                            player.performCommand("team")
                        }
                    )
                }
            Anime.close(player)
            menu.open(player)
        }

        val command = CustomCommand(40, "Подождите %t")
        command("test") { player, _ ->

            command.execute(player) {
                player.fine("ага")
            }
        }
    }
}