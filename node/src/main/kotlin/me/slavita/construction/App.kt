package me.slavita.construction

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.google.gson.GsonBuilder
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
import me.slavita.construction.listener.LoadUserEvent
import me.slavita.construction.listener.PlayerEvents
import me.slavita.construction.multichat.MultiChats
import me.slavita.construction.npc.NpcManager
import me.slavita.construction.player.User
import me.slavita.construction.player.*
import me.slavita.construction.project.Project
import me.slavita.construction.project.ProjectSerializer
import me.slavita.construction.protocol.*
import me.slavita.construction.storage.BlocksStorage
import me.slavita.construction.storage.BlocksStorageSerializer
import me.slavita.construction.structure.*
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.ui.*
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.items.ItemsManager
import me.slavita.construction.utils.*
import me.slavita.construction.utils.PlayerExtensions.accept
import me.slavita.construction.utils.PlayerExtensions.deny
import me.slavita.construction.utils.language.EnumLang
import me.slavita.construction.world.GameWorld
import me.slavita.construction.world.ItemProperties
import me.slavita.construction.world.SlotItem
import org.bukkit.Bukkit
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import ru.cristalix.core.CoreApi
import ru.cristalix.core.datasync.EntityDataParameters
import ru.cristalix.core.invoice.IInvoiceService
import ru.cristalix.core.invoice.InvoiceService
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
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

lateinit var app: App

class App : JavaPlugin() {

    lateinit var structureMap: WorldMeta
    lateinit var mainWorld: GameWorld
    val bot = bot {
        token = System.getProperty("tg.token")
        dispatch {}
    }
    val chatId = -1001654696542L
    val users = hashMapOf<UUID, User>()
    val allBlocks = hashSetOf<ItemProperties>()

    val localStaff = hashSetOf(
        "e2543a0a-5799-11e9-8374-1cb72caa35fd",
        "ba821208-6b64-11e9-8374-1cb72caa35fd",
        "f83a7e5d-9361-11e9-80c4-1cb72caa35fd",
    ).map { UUID.fromString(it) }

    var pass = 0L
        private set
    var started = 0L
        private set

    private val failedLoad = hashSetOf<Player>()
    private val failedSave = hashSetOf<SaveUserPackage>()

    private val gsonSerializer = GsonBuilder()
        .registerTypeAdapter(PlayerCell::class.java, PlayerCellSerializer())
        .registerTypeAdapter(WorkerStructure::class.java, BuildingStructureSerializer())
        .registerTypeAdapter(ClientStructure::class.java, BuildingStructureSerializer())
        .registerTypeAdapter(Project::class.java, ProjectSerializer())
        .registerTypeAdapter(BlocksStorage::class.java, BlocksStorageSerializer())
        .registerTypeAdapter(City::class.java, CitySerializer())
        .create()

    override fun onEnable() {
        app = this

        started = System.currentTimeMillis()

        EntityDataParameters.register()
        Platforms.set(PlatformDarkPaper())

        Anime.include(Kit.STANDARD, Kit.EXPERIMENTAL, Kit.DIALOG, Kit.MULTI_CHAT, Kit.LOOTBOX, Kit.NPC)

        CoreApi.get().run {
            registerService(ITransferService::class.java, TransferService(socket))
            registerService(IPartyService::class.java, PartyService(socket))
            registerService(IScoreboardService::class.java, ScoreboardService())
            registerService(IInvoiceService::class.java, InvoiceService(socket))
            registerService(IMultiChatService::class.java, MultiChatService(socket))
        }

        IMultiChatService.get().apply {
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
                ITransferService.get().transfer(UUID.fromString(System.getProperty("construction.user")), realmId)
            }
        }

        ModLoader.loadAll("mods")
        ModLoader.onJoining("construction-mod.jar")

        structureMap = MapLoader.load("construction", "structures")
        val map = MapLoader.load("construction", "main")
        nextTick {
            GameWorld(map.apply {
                world.setGameRuleValue("randomTickSpeed", "0")
                world.setGameRuleValue("gameLoopFunction", "false")
                world.setGameRuleValue("disableElytraMovementCheck", "true")
            })
        }

        Music.block(Category.MUSIC)

        Lock.realms("SLVT")

        Stronghold.namespace("construction")

        Config.load {
            NpcManager
            CityGlows
        }

        Boosters
        MultiChats
        UserCommands
        AdminCommands
        Structures
        ModCallbacks
        ItemsManager
        PlayerEvents

        EnumLang.init()

        bot.startPolling()
        logTg("Realm Initialized")

        scheduler.run {
            scheduleSyncRepeatingTask(app, { pass++ }, 0, 1)
            runTimerAsync(0, 120) {
                failedLoad.forEach {
                    tryLoadUser(it, false)
                }
                failedSave.forEach {
                    trySaveUser(it)
                }
            }

            runTimerAsync(10 * 20, 2 * 60 * 20) {
                Leaderboards.load()
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
        }
    }

    override fun onDisable() {
        EnumLang.clean()
    }

    fun getUserOrNull(uuid: UUID) = users[uuid]

    fun getUser(uuid: UUID) = users[uuid]!!

    fun getUser(player: Player) = getUser(player.uniqueId)

    fun unloadUser(player: Player) = users.remove(player.uniqueId)

    fun unloadUser(uuid: UUID) = users.remove(uuid)

    fun trySaveUser(player: Player) = runAsync {
        trySaveUser(player.user.run {
            data.inventory.clear()
            player.inventory.storageContents.forEachIndexed { index, item ->
                if (item != null) data.inventory.add(SlotItem(item, index, item.getAmount()))
            }
            player.inventory.clear()

            SaveUserPackage(
                uuid.toString(),
                gsonSerializer.toJson(data),
                data.experience,
                data.totalProjects.toLong()
            )
        })
    }

    private fun trySaveUser(pckg: SaveUserPackage) = runAsync {
        try {
            socket.writeAndAwaitResponse<SaveUserPackage>(pckg)[5, TimeUnit.SECONDS]
            println("user saved")
            failedSave.remove(pckg)
            unloadUser(UUID.fromString(pckg.uuid))
        } catch(e: TimeoutException) {
            println("user save timeout")
            failedSave.add(pckg)
        }
    }

    fun tryLoadUser(player: Player, silent: Boolean) = runAsync {
        if (!player.isOnline) return@runAsync
        val uuid = player.uniqueId
        try {
            println("try load user")
            LoadUserEvent(cacheUser(uuid)).callEvent()
            failedLoad.remove(player)
            if (!silent) player.accept("Данные успешно загружены")
        } catch (e: TimeoutException) {
            println("user load timeout")
            player.deny("Не удалось загрузить ваши данные\nПовторная загрузка данных...")
            if (!failedLoad.contains(player)) failedLoad.add(player)
        }
    }

    private fun cacheUser(uuid: UUID): User {
        val raw = getRawUser(uuid)
        println("got raw data")
        val user = User(uuid).apply { initialize(raw) }
        println("user initialized")
        users[uuid] = user
        return user
    }

    private fun getRawUser(uuid: UUID) =
        socket.writeAndAwaitResponse<GetUserPackage>(GetUserPackage(uuid.toString()))[5, TimeUnit.SECONDS].data
}
