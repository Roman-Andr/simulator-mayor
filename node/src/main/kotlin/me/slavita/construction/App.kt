package me.slavita.construction

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
import me.slavita.construction.city.City
import me.slavita.construction.city.CitySerializer
import me.slavita.construction.player.User
import me.slavita.construction.project.Project
import me.slavita.construction.project.ProjectSerializer
import me.slavita.construction.protocol.GetUserPackage
import me.slavita.construction.protocol.SaveUserPackage
import me.slavita.construction.city.storage.BlocksStorage
import me.slavita.construction.city.storage.BlocksStorageSerializer
import me.slavita.construction.listener.*
import me.slavita.construction.structure.*
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.ItemsManager
import me.slavita.construction.ui.Leaderboards
import me.slavita.construction.utils.*
import me.slavita.construction.utils.language.EnumLang
import me.slavita.construction.utils.language.LanguageHelper
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
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

lateinit var app: App

class App : JavaPlugin() {

    lateinit var structureMap: WorldMeta
    lateinit var mainWorld: GameWorld
    val chatId = -1001654696542L
    val users = hashMapOf<UUID, User>()
    val allBlocks = hashSetOf<ItemProperties>()

    val localStaff = hashSetOf(
        "e2543a0a-5799-11e9-8374-1cb72caa35fd",
        "f83a7e5d-9361-11e9-80c4-1cb72caa35fd",
        "ba821208-6b64-11e9-8374-1cb72caa35fd",
    ).map { it.toUUID() }

    var pass = 0L
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

        EntityDataParameters.register()
        Platforms.set(PlatformDarkPaper())

        Anime.include(Kit.STANDARD, Kit.EXPERIMENTAL, Kit.DIALOG, Kit.LOOTBOX, Kit.NPC)

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
            GameWorld(map.apply {
                world.setGameRuleValue("randomTickSpeed", "0")
                world.setGameRuleValue("gameLoopFunction", "false")
                world.setGameRuleValue("disableElytraMovementCheck", "true")
            })
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
        )

        scheduler.run {
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

            coroutineForAll(5 * 60 * 20) {
                showcases.forEach { showcase ->
                    showcase.updatePrices()
                }
                showcaseMenu?.updateButtons()
                player.accept("Цены обновлены!")
            }
        }

        runTimer(0, 1) { pass++ }
    }

    override fun onDisable() {
        EnumLang.clean()
    }

    fun getUserOrNull(uuid: UUID) = users[uuid]

    fun getUser(uuid: UUID) = users[uuid]!!

    fun getUser(player: Player) = getUser(player.uniqueId)

    fun unloadUser(uuid: UUID) = users.remove(uuid)

    fun trySaveUser(player: Player) = runAsync {
        trySaveUser(player.user.run {
            data.inventory.clear()

            val inventory = if (data.hasFreelance) currentFreelance!!.playerInventory else player.inventory.storageContents

            inventory.forEachIndexed { index, item ->
                if (item != null) data.inventory.add(SlotItem(item, index, item.getAmount()))
            }
            player.inventory.clear()

            SaveUserPackage(
                uuid.toString(),
                gsonSerializer.toJson(data),
                data.experience,
                data.totalProjects.toLong(),
                data.totalBoosters,
                data.lastIncome,
                data.money,
                data.reputation,
            )
        })
    }

    private fun trySaveUser(pckg: SaveUserPackage) = runAsync {
        try {
            log("try save user")
            socket.writeAndAwaitResponse<SaveUserPackage>(pckg)[5, TimeUnit.SECONDS]
            log("user saved")
            failedSave.remove(pckg)
            unloadUser(pckg.uuid.toUUID())
        } catch (e: TimeoutException) {
            log("user save timeout")
            failedSave.add(pckg)
        }
    }

    fun tryLoadUser(player: Player, silent: Boolean) = runAsync {
        if (!player.isOnline) return@runAsync
        val uuid = player.uniqueId
        try {
            log("try load user")
            LoadUserEvent(cacheUser(uuid)).callEvent()
            failedLoad.remove(player)
            if (!silent) player.accept("Данные успешно загружены")
        } catch (e: TimeoutException) {
            log("user load timeout")
            player.deny("Не удалось загрузить ваши данные\nПовторная загрузка данных...")
            if (!failedLoad.contains(player)) failedLoad.add(player)
        }
    }

    private fun cacheUser(uuid: UUID): User {
        val raw = getRawUser(uuid)
        log("got raw data")
        val user = User(uuid).apply { initialize(raw) }
        log("user initialized")
        users[uuid] = user
        return user
    }

    private fun getRawUser(uuid: UUID) =
        socket.writeAndAwaitResponse<GetUserPackage>(GetUserPackage(uuid.toString()))[5, TimeUnit.SECONDS].data
}
