package me.slavita.construction

import clepto.bukkit.B.plugin
import dev.implario.bukkit.platform.Platforms
import dev.implario.games5e.sdk.cristalix.WorldMeta
import dev.implario.platform.impl.darkpaper.PlatformDarkPaper
import me.func.mod.Anime
import me.func.mod.Glow
import me.func.mod.Kit
import me.func.mod.conversation.ModLoader
import me.func.mod.data.LootDrop
import me.func.mod.menu.ReactiveButton
import me.func.mod.menu.selection.Selection
import me.func.mod.util.after
import me.func.mod.util.command
import me.func.mod.util.listener
import me.func.protocol.GlowColor
import me.slavita.construction.multichat.MultiChatUtil
import me.slavita.construction.player.Statistics
import me.slavita.construction.player.User
import me.slavita.construction.player.events.PhysicsDisabler
import me.slavita.construction.player.events.PlayerJoinEvents
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.util.MapLoader
import me.slavita.construction.worker.WorkerGenerator
import me.slavita.construction.worker.WorkerRarity
import me.slavita.construction.world.GameWorld
import me.slavita.construction.world.Structure
import me.slavita.construction.world.Structures
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import ru.cristalix.core.CoreApi
import ru.cristalix.core.realm.IRealmService
import ru.cristalix.core.realm.RealmStatus
import ru.cristalix.core.scoreboard.IScoreboardService
import ru.cristalix.core.scoreboard.ScoreboardService
import ru.cristalix.core.transfer.ITransferService
import ru.cristalix.core.transfer.TransferService
import java.util.*

lateinit var app: App

class App : JavaPlugin() {
    lateinit var structureMap: WorldMeta
    lateinit var mainWorld: GameWorld
    val users = mutableMapOf<UUID, User>()

    override fun onEnable() {
        app = this
        plugin = app

        val slavita = UUID.fromString("ba821208-6b64-11e9-8374-1cb72caa35fd")
        val romanAndr = UUID.fromString("e2543a0a-5799-11e9-8374-1cb72caa35fd")

        Platforms.set(PlatformDarkPaper())

        CoreApi.get().run {
            registerService(ITransferService::class.java, TransferService(socketClient))
            registerService(IScoreboardService::class.java, ScoreboardService())
        }

        IRealmService.get().currentRealmInfo.apply {
            IScoreboardService.get().serverStatusBoard.displayName = "§fТест #§b" + this.realmId.id;
        }.run {
            readableName = "Тест"
            groupName = "CRN"
            status = RealmStatus.WAITING_FOR_PLAYERS
            isLobbyServer = true
        }

        listener(PlayerJoinEvents(), PhysicsDisabler())

        Anime.include(Kit.STANDARD, Kit.EXPERIMENTAL, Kit.DIALOG, Kit.MULTI_CHAT, Kit.LOOTBOX, Kit.NPC)
        MultiChatUtil.createChats()

        ModLoader.loadAll("mods")
        ModLoader.onJoining("mod-bundle-1.0.jar")

        structureMap = MapLoader.load("construction", "structures")!!
        mainWorld = GameWorld(MapLoader.load("construction", "test")!!)

        command("lootbox") { player, _ ->
            val user = app.getUser(player)
            val storage = mutableListOf<ReactiveButton>()
            WorkerRarity.values().forEach {
                val canBuy = user.stats.money >= it.price
                val iconItem = ItemIcons.get(it.iconKey, it.iconValue, it.iconMaterial)
                storage.add(ReactiveButton()
                    .item(iconItem)
                    .title(it.title)
                    .description(it.description)
                    .price(it.price)
                    .hint(if (canBuy) "Купить" else "Недостаточно\nсредств")
                    .onClick { clickedPlayer, _, _ ->
                        if (canBuy) {
                            val worker = WorkerGenerator.generate(it)
                            Anime.openLootBox(clickedPlayer, LootDrop(iconItem, worker.name, it.dropRare))
                            Glow.animate(player, 2.0, GlowColor.GREEN)
                            user.workers.add(worker)
                        }
                        else {
                            Glow.animate(player, 2.0, GlowColor.RED)
                        }
                    }
                )
            }
            val menu = Selection(
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
                title = "Ваши строители",
                money = "Ваш баланс ${user.stats.money}",
                rows = 4,
                columns = 5,
                storage = storage
            )
            user.workers.filter { it.active }.sortedBy { it.rarity.price }.reversed()
                .plus(user.workers.filter { !it.active }.sortedBy { it.rarity.price }.reversed().asIterable()).forEach {
                val iconItem = ItemIcons.get(it.rarity.iconKey, it.rarity.iconValue, it.rarity.iconMaterial)
                storage.add(ReactiveButton()
                    .item(iconItem)
                    .title(it.name)
                    //.description(it)
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
    }

    fun addUser(player: Player) {
        users[player.uniqueId] = User(player, Statistics(1000000000.0, 0, 0.0, .0, 0, 0))
    }

    fun getUser(uuid: UUID) : User {
        return users[uuid]!!
    }

    fun getUser(player: Player) : User {
        return getUser(player.uniqueId)
    }
}