package me.slavita.construction

import dev.implario.bukkit.platform.Platforms
import dev.implario.platform.impl.darkpaper.PlatformDarkPaper
import me.func.Lock
import me.func.mod.Anime
import me.func.mod.Kit
import me.func.mod.conversation.ModLoader
import me.func.mod.util.after
import me.func.sound.Category
import me.func.sound.Music
import me.func.stronghold.Stronghold
import me.func.world.MapLoader
import me.func.world.WorldMeta
import me.slavita.construction.action.chat.AdminCommands
import me.slavita.construction.action.chat.UserCommands
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.booster.Boosters
import me.slavita.construction.common.utils.register
import me.slavita.construction.listener.*
import me.slavita.construction.player.User
import me.slavita.construction.player.UserLoader
import me.slavita.construction.player.UserSaver
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.ItemsManager
import me.slavita.construction.ui.Leaderboards
import me.slavita.construction.utils.*
import me.slavita.construction.utils.language.EnumLang
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.world.GameWorld
import me.slavita.construction.world.ItemProperties
import org.bukkit.Bukkit
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import ru.cristalix.core.CoreApi
import ru.cristalix.core.datasync.EntityDataParameters
import ru.cristalix.core.invoice.IInvoiceService
import ru.cristalix.core.invoice.InvoiceService
import ru.cristalix.core.keyboard.IKeyService
import ru.cristalix.core.keyboard.KeyService
import ru.cristalix.core.multichat.ChatMessage
import ru.cristalix.core.multichat.IMultiChatService
import ru.cristalix.core.multichat.MultiChatService
import ru.cristalix.core.party.IPartyService
import ru.cristalix.core.party.PartyService
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
    val chatId = System.getenv("TG_CHAT_ID").toLong()
    val users = hashMapOf<UUID, User>()
    val allBlocks = hashSetOf<ItemProperties>()
    val waitResponseTime = 5L

    val localStaff = hashSetOf(
        "e2543a0a-5799-11e9-8374-1cb72caa35fd",
        "f83a7e5d-9361-11e9-80c4-1cb72caa35fd",
        "ba821208-6b64-11e9-8374-1cb72caa35fd",
    ).map { it.toUUID() }

    var pass = 0L
        private set

    override fun onEnable() {
        app = this

        EntityDataParameters.register()
        Platforms.set(PlatformDarkPaper())

        Anime.include(Kit.DEBUG, Kit.STANDARD, Kit.EXPERIMENTAL, Kit.DIALOG, Kit.LOOTBOX, Kit.NPC)

        CoreApi.get().run {
            registerService(ITransferService::class.java, TransferService(socket))
            registerService(IPartyService::class.java, PartyService(socket))
            registerService(IScoreboardService::class.java, ScoreboardService())
            registerService(IInvoiceService::class.java, InvoiceService(socket))
            registerService(IMultiChatService::class.java, MultiChatService(socket))
            registerService(IKeyService::class.java, KeyService(app))
        }

        IMultiChatService.get().run {
            setRealmTag("slvt")
            addSingleChatHandler("construction") { message: ChatMessage ->
                Bukkit.getOnlinePlayers().forEach { player ->
                    player.sendMessage(*message.components.toMutableList().toTypedArray())
                }
            }
        }

        IRealmService.get().currentRealmInfo.apply {
            readableName = "Тест"
            groupName = "Тест"
            status = RealmStatus.WAITING_FOR_PLAYERS
            maxPlayers = 250
            extraSlots = 15

            IScoreboardService.get().serverStatusBoard.displayName = "${WHITE}Тест #${AQUA}" + realmId.id
            after(20 * 4) {
                ITransferService.get().transfer(System.getenv("CONSTRUCTION_USER").toUUID(), realmId)
            }
        }

        ModLoader.loadAll("mods")
        ModLoader.onJoining("construction-mod.jar")

        structureMap = MapLoader.load("construction", "structures")
        val map = MapLoader.load("construction", "main")
        nextTick {
            GameWorld(map.apply { world.apply {
                setGameRuleValue("randomTickSpeed", "0")
                setGameRuleValue("gameLoopFunction", "false")
                setGameRuleValue("disableElytraMovementCheck", "true")
            }})
        }

        Music.block(Category.MUSIC)

        Lock.realms("SLVT")

        Stronghold.namespace("construction")

        register(
            Boosters,
            UserCommands,
            AdminCommands,
            ModCallbacks,
            ItemsManager,
            Structures,
            LanguageHelper,
            PhysicsDisabler,
            OnJoin,
            OnLeave,
            OnChat,
            OnActions,
            OnUserLoad,
            UserLoader,
            UserSaver,
            BotsManager,
        )

        runTimerAsync(10 * 20, 2 * 60 * 20) {
            Leaderboards.load()
        }

        coroutineForAll(1) {
            data.cities.forEach { city ->
                city.projects.forEach {
                    if (it.structure is WorkerStructure) (it.structure as WorkerStructure).build()
                }
            }
        }

        coroutineForAll(20) {
            data.money += income.applyBoosters(BoosterType.MONEY_BOOSTER)
        }

        coroutineForAll(10 * 20) {
            showcases.forEach {
                it.properties.updatePrices()
            }
        }

        coroutineForAll(2 * 60 * 20) {
            data.cities.forEach {
                it.breakStructure()
            }
        }

        coroutineForAll(5 * 60 * 20) {
            showcases.forEach { showcase ->
                showcase.updatePrices()
            }
            showcaseMenu?.updateButtons()
            player.accept("Цены обновлены!")
        }

        runTimer(0, 1) { pass++ }
    }

    override fun onDisable() {
        EnumLang.clean()
    }

    fun getUserOrNull(uuid: UUID) = users[uuid]

    fun getUser(uuid: UUID) = users[uuid]!!

    fun getUser(player: Player) = getUser(player.uniqueId)
}
