package me.slavita.construction

import clepto.bukkit.B.plugin
import dev.implario.bukkit.platform.Platforms
import dev.implario.games5e.sdk.cristalix.WorldMeta
import dev.implario.platform.impl.darkpaper.PlatformDarkPaper
import me.func.mod.Anime
import me.func.mod.Kit
import me.func.mod.conversation.ModLoader
import me.func.mod.conversation.data.LootDrop
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.Glow
import me.func.mod.ui.menu.selection.Selection
import me.func.mod.util.command
import me.func.mod.util.listener
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.command.UserCommands
import me.slavita.construction.multichat.MultiChatUtil
import me.slavita.construction.npc.NpcManager
import me.slavita.construction.player.Statistics
import me.slavita.construction.player.User
import me.slavita.construction.player.events.PhysicsDisabler
import me.slavita.construction.player.events.PlayerJoinEvents
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.util.MapLoader
import me.slavita.construction.worker.WorkerGenerator
import me.slavita.construction.worker.WorkerRarity
import me.slavita.construction.world.GameWorld
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

        Platforms.set(PlatformDarkPaper())

        CoreApi.get().run {
            registerService(ITransferService::class.java, TransferService(socketClient))
            registerService(IScoreboardService::class.java, ScoreboardService())
        }

        IRealmService.get().currentRealmInfo.apply {
            IScoreboardService.get().serverStatusBoard.displayName = "§fТест #§b" + this.realmId.id
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

        NpcManager
        UserCommands
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