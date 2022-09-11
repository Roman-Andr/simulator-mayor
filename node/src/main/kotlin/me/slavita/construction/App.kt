package me.slavita.construction

import clepto.bukkit.B
import clepto.bukkit.B.plugin
import dev.implario.bukkit.platform.Platforms
import dev.implario.bukkit.world.Label
import dev.implario.games5e.sdk.cristalix.WorldMeta
import dev.implario.platform.impl.darkpaper.PlatformDarkPaper
import me.func.MetaWorld
import me.func.builder.MetaSubscriber
import me.func.mod.Anime
import me.func.mod.Kit
import me.func.mod.conversation.ModLoader
import me.func.unit.Building
import me.slavita.construction.player.prepare.PlayerJoinEvents
import me.slavita.construction.util.MapLoader
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
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

    lateinit var map : WorldMeta
    var buildings = mutableListOf<Building>()

    override fun onEnable() {
        app = this
        plugin = app

        Platforms.set(PlatformDarkPaper())

        CoreApi.get().run {
            registerService(ITransferService::class.java, TransferService(socketClient))
            registerService(IScoreboardService::class.java, ScoreboardService())
        }

        B.events(PlayerJoinEvents())

        Anime.include(Kit.STANDARD, Kit.EXPERIMENTAL, Kit.DIALOG, Kit.MULTI_CHAT, Kit.LOOTBOX)

        ModLoader.loadAll("mods")

        IRealmService.get().currentRealmInfo.run {
            readableName = "Тест"
            groupName = "CRN"
            status = RealmStatus.GAME_STARTED_CAN_JOIN
            isLobbyServer = false
        }

        map = MapLoader.load("construction", "test")!!

        var ok = true
        map.getBoxes("building").forEach {
            var uuid = UUID.fromString("ba821208-6b64-11e9-8374-1cb72caa35fd")
            if (ok) ok = false
            else uuid = UUID.fromString("e2543a0a-5799-11e9-8374-1cb72caa35fd")

            val building = Building(uuid, "building", it.key, map).apply {
                allocate(map.getLabels("default", "1")[0])
            }
                .onClick { player, packetPlayInUseItem -> player.sendMessage("Ахуеть спасибо папаша за мерседес рено логан чёрного цвета двадцатого века!") }
                .onBreak { player, packetPlayInBlockDig ->
                    (player as CraftPlayer).handle.playerConnection.sendPacket(PacketPlayOutBlockChange().apply {
                        a = packetPlayInBlockDig.a
                        block = MetaWorld.storage[player.uniqueId]!!.buildings[0].allocation!!.blocks?.get(a)!!
                    })
                }

            buildings.add(building)
        }

        MetaWorld.universe(
            map.world, *MetaSubscriber()
                .buildingLoader { buildings }
                .build()
        )
    }

    fun getSpawn(): Label = map.getLabel("spawn")

    override fun onDisable() {
        println("Disable plugin Construction")
    }
}