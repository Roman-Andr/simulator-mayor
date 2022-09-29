package me.slavita.construction.commands

import me.func.mod.Anime
import me.func.mod.conversation.data.LootDrop
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.Glow
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.app
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.worker.WorkerGenerator
import me.slavita.construction.worker.WorkerRarity
import org.bukkit.entity.Player

enum class Command(
    val action: (player: Player) -> Unit
) {
    LOOTBOX({ player ->
        val storage = mutableListOf<ReactiveButton>()
        app.getUser(player).run {
            WorkerRarity.values().forEach {
                val canBuy = stats.money >= it.price
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
                                workers.add(worker)
                            } else {
                                Anime.close(player)
                                Glow.animate(player, 1.0, GlowColor.RED)
                            }
                        }
                )
            }
            Anime.close(player)
            Selection(
                title = "Покупка строителей",
                money = "Ваш баланс ${stats.money}",
                rows = 3,
                columns = 3,
                storage = storage
            ).open(player)
        }
    }),
    TEAM({ player ->
        val user = app.getUser(player)
        val storage = mutableListOf<ReactiveButton>()
        user.workers.filter { it.active }.sortedBy { it.rarity.price }.reversed()
            .plus(user.workers.filter { !it.active }.sortedBy { it.rarity.price }.reversed().asIterable()).forEach {
                storage.add(
                    ReactiveButton()
                        .item(ItemIcons.get(it.rarity.iconKey, it.rarity.iconValue, it.rarity.iconMaterial))
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
                            TEAM.delaydedExecute(player)
                        }
                )
            }
        Anime.close(player)
        Selection(
            title = "Ваши строители",
            money = "Ваш баланс ${user.stats.money}",
            rows = 4,
            columns = 5,
            storage = storage
        ).open(player)
    }),
    PROJECTS({ player ->
        val user = app.getUser(player)
        val storage = mutableListOf(
            ReactiveButton()
                .title("Получить проект")
                .description("Вы сами будете его строить")
                .item(ItemIcons.get("skyblock", "crafts"))
                .onClick { _, _, _ ->
                    Anime.close(player)
                    app.mainWorld.playerBuild(player)
                },
            ReactiveButton()
                .title("Получить проект")
                .description("За вас построят все ваши строители")
                .item(ItemIcons.get("skyblock", "crafts"))
                .onClick { _, _, _ ->
                    Anime.close(player)
                    app.mainWorld.playerWorkerBuild(player)
                }
        )
        Anime.close(player)
        Selection(
            title = "Ваши строители",
            money = "Ваш баланс ${user.stats.money}",
            rows = 4,
            columns = 5,
            storage = storage
        ).open(player)
    });

    fun execute(player: Player) = action(player)

    fun delaydedExecute(player: Player) {
        CommandsManager.timeData[player]!![this]!!.execute(player)
    }
}