package me.slavita.construction

import clepto.bukkit.B.plugin
import dev.implario.bukkit.platform.Platforms
import dev.implario.bukkit.world.Label
import dev.implario.games5e.sdk.cristalix.WorldMeta
import dev.implario.platform.impl.darkpaper.PlatformDarkPaper
import me.func.mod.Anime
import me.func.mod.Kit
import me.func.mod.MultiChat
import me.func.mod.conversation.ModLoader
import me.func.mod.util.command
import me.func.mod.util.listener
import me.func.protocol.ModChat
import me.func.protocol.dialog.Action.Companion.command
import me.slavita.construction.multichat.MultiChatUtil
import me.slavita.construction.player.events.PhysicsDisabler
import me.slavita.construction.player.events.PlayerJoinEvents
import me.slavita.construction.util.MapLoader
import me.slavita.construction.world.GameWorld
import me.slavita.construction.world.Structure
import me.slavita.construction.world.StructureType
import org.bukkit.plugin.java.JavaPlugin
import ru.cristalix.core.CoreApi
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

        listener(PlayerJoinEvents())
        listener(PhysicsDisabler())

        Anime.include(Kit.STANDARD, Kit.EXPERIMENTAL, Kit.DIALOG, Kit.MULTI_CHAT, Kit.LOOTBOX)

        MultiChatUtil.createChats()

        ModLoader.loadAll("mods")

        structureMap = MapLoader.load("construction", "structures")!!
        mainWorld = GameWorld(MapLoader.load("construction", "test")!!)

        mainWorld.addStructure(Structure(slavita, StructureType.SMALL_HOUSE, mainWorld.map.getLabels("default", "1")[0]))

        IRealmService.get().currentRealmInfo.run {
            readableName = "Тест"
            groupName = "CRN"
            status = RealmStatus.WAITING_FOR_PLAYERS
            isLobbyServer = true
        }

        command("spawn") { player, args ->
            mainWorld.addStructure(Structure(player.uniqueId, StructureType.BIG_HOUSE, mainWorld.map.getLabels("default", "2")[0]))
            mainWorld.showAll(player)
        }
    }
}