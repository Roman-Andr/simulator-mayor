package me.slavita.construction

import dev.implario.bukkit.platform.Platforms
import dev.implario.platform.impl.darkpaper.PlatformDarkPaper
import me.func.mod.Anime
import me.func.mod.Kit
import me.func.mod.conversation.ModLoader
import me.func.mod.util.after
import me.func.mod.util.listener
import me.func.sound.Category
import me.func.sound.Music
import me.func.world.MapLoader
import me.func.world.WorldMeta
import me.slavita.construction.action.chat.AdminCommands
import me.slavita.construction.action.chat.UserCommands
import me.slavita.construction.market.MarketsManager
import me.slavita.construction.multichat.MultiChats
import me.slavita.construction.npc.NpcManager
import me.slavita.construction.player.Statistics
import me.slavita.construction.player.User
import me.slavita.construction.player.events.PhysicsDisabler
import me.slavita.construction.player.events.PlayerEvents
import me.slavita.construction.structure.instance.Structures
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
    val users = hashMapOf<UUID, User>()
    val allBlocks = hashSetOf<ItemProperties>()

    val localStaff = hashSetOf(
        "e2543a0a-5799-11e9-8374-1cb72caa35fd",
        "ba821208-6b64-11e9-8374-1cb72caa35fd"
    ).map { UUID.fromString(it) }

    var pass = 0L
        private set

    override fun onEnable() {
        app = this

        EntityDataParameters.register()
        Platforms.set(PlatformDarkPaper())

        Anime.include(Kit.STANDARD, Kit.EXPERIMENTAL, Kit.DIALOG, Kit.MULTI_CHAT, Kit.LOOTBOX, Kit.NPC, Kit.DEBUG)

        CoreApi.get().run {
            registerService(ITransferService::class.java, TransferService(ISocketClient.get()))
            registerService(IPartyService::class.java, PartyService(ISocketClient.get()))
            registerService(IScoreboardService::class.java, ScoreboardService())
        }

        IRealmService.get().currentRealmInfo.apply {
            IScoreboardService.get().serverStatusBoard.displayName = "${WHITE}Тест #${AQUA}" + realmId.id
            after(60) {
                ITransferService.get().transfer(UUID.fromString(System.getProperty("construction.user")), realmId)
            }
        }.run {
            readableName = "Тест"
            groupName = "CRN"
            status = RealmStatus.WAITING_FOR_PLAYERS
            isLobbyServer = true
        }

        ModLoader.loadAll("mods")
        ModLoader.onJoining("construction-mod.jar")

        structureMap = MapLoader.load("construction", "structures")
        mainWorld = GameWorld(MapLoader.load("construction", "test"))

        Music.block(Category.MUSIC)

        Config
        after(50) {
            NpcManager
        }
        MultiChats
        UserCommands
        AdminCommands
        Structures
        MarketsManager
        ModCallbacks

        listener(PlayerEvents, PhysicsDisabler, ItemsManager)

        server.scheduler.scheduleSyncRepeatingTask(this, { pass++ }, 0, 1)
    }

    fun addUser(player: Player): User {
        users[player.uniqueId] = User(player, Statistics())
        return getUser(player)
    }

    fun getUserOrNull(uuid: UUID) : User? {
        return users[uuid]
    }

    fun hasUser(player: Player) : Boolean {
        return getUserOrNull(player.uniqueId) == null
    }

    fun getUser(uuid: UUID) : User {
        return users[uuid]!!
    }

    fun getUser(player: Player) : User {
        return getUser(player.uniqueId)
    }
}