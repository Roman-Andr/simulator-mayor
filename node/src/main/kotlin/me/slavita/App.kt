package me.slavita

import clepto.cristalix.WorldMeta
import dev.implario.bukkit.platform.Platforms
import dev.implario.platform.impl.darkpaper.PlatformDarkPaper
import me.func.mod.Anime
import me.func.mod.Kit
import me.func.mod.conversation.ModLoader
import me.slavita.util.MapLoader
import org.bukkit.plugin.java.JavaPlugin
import ru.cristalix.core.CoreApi
import ru.cristalix.core.realm.IRealmService
import ru.cristalix.core.realm.RealmStatus
import ru.cristalix.core.scoreboard.IScoreboardService
import ru.cristalix.core.scoreboard.ScoreboardService
import ru.cristalix.core.transfer.ITransferService
import ru.cristalix.core.transfer.TransferService

lateinit var app: App

class App : JavaPlugin() {

    private lateinit var worldMeta: WorldMeta

    override fun onEnable() {
        app = this

        Platforms.set(PlatformDarkPaper())

        CoreApi.get().run {
            registerService(ITransferService::class.java, TransferService(socketClient))
            registerService(IScoreboardService::class.java, ScoreboardService())
        }

        Anime.include(Kit.STANDARD, Kit.EXPERIMENTAL, Kit.DIALOG, Kit.MULTI_CHAT, Kit.LOOTBOX);

        ModLoader.loadAll("mods")

        IRealmService.get().currentRealmInfo.run {
            readableName = "Что"
            groupName = "Не"
            status = RealmStatus.WAITING_FOR_PLAYERS
            isLobbyServer = true
        }

        worldMeta = MapLoader().load("test")!!
    }

    override fun onDisable() {
        println("Disable plugin Construction")
    }
}