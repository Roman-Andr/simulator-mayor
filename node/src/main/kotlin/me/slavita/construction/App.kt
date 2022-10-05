package me.slavita.construction

import dev.implario.bukkit.platform.Platforms
import dev.implario.platform.impl.darkpaper.PlatformDarkPaper
import me.func.mod.Anime
import me.func.mod.Kit
import me.func.mod.conversation.ModLoader
import me.func.mod.util.after
import me.func.mod.util.listener
import me.func.world.MapLoader
import me.func.world.WorldMeta
import me.slavita.construction.action.chat.AdminCommands
import me.slavita.construction.action.chat.UserCommands
import me.slavita.construction.multichat.MultiChatUtil
import me.slavita.construction.npc.NpcManager
import me.slavita.construction.player.Statistics
import me.slavita.construction.player.User
import me.slavita.construction.player.events.PhysicsDisabler
import me.slavita.construction.player.events.PlayerJoinEvents
import me.slavita.construction.world.GameWorld
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
    val users = mutableMapOf<UUID, User>()

    val localStaff  = hashSetOf(
        "e2543a0a-5799-11e9-8374-1cb72caa35fd",
        "ba821208-6b64-11e9-8374-1cb72caa35fd"
    ).map { UUID.fromString(it) }

    var pass = 0L
        private set

    override fun onEnable() {
        app = this

        EntityDataParameters.register()
        Platforms.set(PlatformDarkPaper())

        Anime.include(Kit.STANDARD, Kit.EXPERIMENTAL, Kit.DIALOG, Kit.MULTI_CHAT, Kit.LOOTBOX, Kit.NPC)
        MultiChatUtil.createChats()

        CoreApi.get().run {
            registerService(ITransferService::class.java, TransferService(ISocketClient.get()))
            registerService(IPartyService::class.java, PartyService(ISocketClient.get()))
            registerService(IScoreboardService::class.java, ScoreboardService())
        }

        IRealmService.get().currentRealmInfo.apply {
            IScoreboardService.get().serverStatusBoard.displayName = "${WHITE}Тест #${AQUA}" + realmId.id
            after(5) {
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

        listener(PlayerJoinEvents(), PhysicsDisabler(), mainWorld)

        NpcManager
        UserCommands
        AdminCommands

        server.scheduler.scheduleSyncRepeatingTask(this, { pass++ }, 0, 1)
    }

    fun addUser(player: Player) {
        users[player.uniqueId] = User(player, Statistics())
    }

    fun getUser(uuid: UUID) : User {
        return users[uuid]!!
    }

    fun getUser(player: Player) : User {
        return getUser(player.uniqueId)
    }
}