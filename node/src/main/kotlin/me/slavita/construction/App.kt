package me.slavita.construction

import dev.implario.bukkit.platform.Platforms
import dev.implario.kensuke.Kensuke
import dev.implario.kensuke.Scope
import dev.implario.kensuke.UserManager
import dev.implario.kensuke.impl.bukkit.BukkitKensuke
import dev.implario.kensuke.impl.bukkit.BukkitUserManager
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
import me.slavita.construction.booster.Boosters
import me.slavita.construction.listener.PlayerEvents
import me.slavita.construction.multichat.MultiChats
import me.slavita.construction.npc.NpcManager
import me.slavita.construction.player.Data
import me.slavita.construction.player.KensukeUser
import me.slavita.construction.player.User
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.ui.BoardsManager
import me.slavita.construction.ui.CityGlows
import me.slavita.construction.ui.SpeedPlaces
import me.slavita.construction.ui.items.ItemsManager
import me.slavita.construction.utils.Config
import me.slavita.construction.utils.ModCallbacks
import me.slavita.construction.utils.language.EnumLang
import me.slavita.construction.utils.user
import me.slavita.construction.world.GameWorld
import me.slavita.construction.world.ItemProperties
import net.minecraft.server.v1_12_R1.Chunk
import net.minecraft.server.v1_12_R1.EntityPlayer
import net.minecraft.server.v1_12_R1.PacketPlayOutMapChunk
import org.bukkit.Bukkit
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import ru.cristalix.ChunkInterceptor
import ru.cristalix.core.CoreApi
import ru.cristalix.core.datasync.EntityDataParameters
import ru.cristalix.core.invoice.IInvoiceService
import ru.cristalix.core.invoice.InvoiceService
import ru.cristalix.core.multichat.ChatMessage
import ru.cristalix.core.multichat.IMultiChatService
import ru.cristalix.core.multichat.MultiChatService
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

        Anime.include(Kit.STANDARD, Kit.EXPERIMENTAL, Kit.DIALOG, Kit.MULTI_CHAT, Kit.LOOTBOX, Kit.NPC)

        CoreApi.get().run {
            registerService(ITransferService::class.java, TransferService(ISocketClient.get()))
            registerService(IPartyService::class.java, PartyService(ISocketClient.get()))
            registerService(IScoreboardService::class.java, ScoreboardService())
            registerService(IInvoiceService::class.java, InvoiceService(ISocketClient.get()))
            registerService(IMultiChatService::class.java, MultiChatService(ISocketClient.get()))
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
        mainWorld = GameWorld(MapLoader.load("construction", "main").apply {
            world.setGameRuleValue("randomTickSpeed", "0")
            world.setGameRuleValue("gameLoopFunction", "false")
            world.setGameRuleValue("disableElytraMovementCheck", "true")
        })

        Music.block(Category.MUSIC)

        Lock.realms("SLVT")

        Stronghold.namespace("construction")

        Config.load {
            NpcManager
            BoardsManager
            CityGlows
        }
        Boosters
        MultiChats
        UserCommands
        AdminCommands
        Structures
        ModCallbacks
        SpeedPlaces
        ItemsManager
        PlayerEvents

        server.scheduler.scheduleSyncRepeatingTask(this, { pass++ }, 0, 1)

        EnumLang.init()
    }

    override fun onDisable() {
        EnumLang.clean()
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
