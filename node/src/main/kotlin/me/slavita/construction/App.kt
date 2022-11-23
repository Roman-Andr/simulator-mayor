package me.slavita.construction

import Nightingale
import dev.implario.bukkit.platform.Platforms
import dev.implario.kensuke.Kensuke
import dev.implario.kensuke.Scope
import dev.implario.kensuke.UserManager
import dev.implario.kensuke.impl.bukkit.BukkitKensuke
import dev.implario.kensuke.impl.bukkit.BukkitUserManager
import dev.implario.platform.impl.darkpaper.PlatformDarkPaper
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
import me.slavita.construction.booster.Boosters
import me.slavita.construction.market.MarketsManager
import me.slavita.construction.multichat.MultiChats
import me.slavita.construction.npc.NpcManager
import me.slavita.construction.player.Data
import me.slavita.construction.player.KensukeUser
import me.slavita.construction.player.User
import me.slavita.construction.player.events.PhysicsDisabler
import me.slavita.construction.player.events.PlayerEvents
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.ui.BoardsManager
import me.slavita.construction.ui.SpeedPlaces
import me.slavita.construction.ui.items.ItemsManager
import me.slavita.construction.utils.Config
import me.slavita.construction.utils.ModCallbacks
import me.slavita.construction.world.GameWorld
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import ru.cristalix.core.CoreApi
import ru.cristalix.core.datasync.EntityDataParameters
import ru.cristalix.core.invoice.IInvoiceService
import ru.cristalix.core.invoice.InvoiceService
import ru.cristalix.core.locate.ILocateService
import ru.cristalix.core.locate.LocateService
import ru.cristalix.core.network.ISocketClient
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
    private val users = hashMapOf<UUID, User>()
    val allBlocks = hashSetOf<ItemProperties>()

    val statScope = Scope("construction-test", Data::class.java)
    lateinit var kensuke: Kensuke
    lateinit var userManager: UserManager<KensukeUser>

    val localStaff = hashSetOf(
        "e2543a0a-5799-11e9-8374-1cb72caa35fd",
        "ba821208-6b64-11e9-8374-1cb72caa35fd"
    ).map { UUID.fromString(it) }

    var pass = 0L
        private set
    var started = 0L
        private set

    override fun onEnable() {
        app = this

        started = System.currentTimeMillis()

        EntityDataParameters.register()
        Platforms.set(PlatformDarkPaper())

        Anime.include(Kit.STANDARD, Kit.EXPERIMENTAL, Kit.DIALOG, Kit.MULTI_CHAT, Kit.LOOTBOX, Kit.NPC, Kit.DEBUG)

        CoreApi.get().run {
            registerService(ITransferService::class.java, TransferService(ISocketClient.get()))
            registerService(ILocateService::class.java, LocateService(ISocketClient.get()))
            registerService(IPartyService::class.java, PartyService(ISocketClient.get()))
            registerService(IScoreboardService::class.java, ScoreboardService())
            registerService(IInvoiceService::class.java, InvoiceService(ISocketClient.get()))
        }

        IRealmService.get().currentRealmInfo.apply {
            readableName = "Тест"
            groupName = "Тест"
            status = RealmStatus.WAITING_FOR_PLAYERS
            maxPlayers = 250
            extraSlots = 15

            IScoreboardService.get().serverStatusBoard.displayName = "${WHITE}Тест #${AQUA}" + realmId.id
            after(20 * 10) {
                ITransferService.get().transfer(UUID.fromString(System.getProperty("construction.user")), realmId)
            }
        }

        userManager = BukkitUserManager(
            listOf(statScope),
            { session, context -> KensukeUser(context.uuid, context.getData(statScope), session) },
            { user, context -> context.store(statScope, user.user.data) }
        )

        kensuke = BukkitKensuke.setup(this)
        kensuke.addGlobalUserManager(userManager)
        kensuke.globalRealm = "SLVT-0"
        userManager.isOptional = true

        ModLoader.loadAll("mods")
        ModLoader.onJoining("construction-mod.jar")

        structureMap = MapLoader.load("construction", "structures")
            .apply {
                world.setGameRuleValue("disableElytraMovementCheck", "true")
            }
        mainWorld = GameWorld(MapLoader.load("construction", "main"))

        Music.block(Category.MUSIC)

        Nightingale
            .subscribe("construction")
            .useP2p()
            .start()

        Stronghold.namespace("construction")

        Config.load {
            NpcManager
            BoardsManager
            MarketsManager
        }
        Boosters
        MultiChats
        UserCommands
        AdminCommands
        Structures
        ModCallbacks
        SpeedPlaces
        PhysicsDisabler
        ItemsManager
        PlayerEvents

        server.scheduler.scheduleSyncRepeatingTask(this, { pass++ }, 0, 1)
    }

    fun getUserOrAdd(uuid: UUID) = getUserOrNull(uuid) ?: addUser(uuid)

    fun addUser(uuid: UUID): User {
        users[uuid] = User(uuid)
        return getUser(uuid)
    }

    fun addUser(player: Player) = addUser(player.uniqueId)

    fun getUserOrNull(uuid: UUID) = users[uuid]

    fun hasUser(player: Player) = getUserOrNull(player.uniqueId) != null

    fun getUser(uuid: UUID) = users[uuid]!!

    fun getUser(player: Player) = getUser(player.uniqueId)
}