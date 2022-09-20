package me.slavita.construction.utils

import clepto.cristalix.Cristalix
import dev.implario.games5e.sdk.cristalix.WorldMeta
import ru.cristalix.core.map.BukkitWorldLoader
import java.util.concurrent.ExecutionException

object MapLoader {

    fun load(type: String?, id: String?): WorldMeta? {
        // Загрузка карты с сервера BUIL-3
        val mapInfo = Cristalix.mapService().getLatestMapByGameTypeAndMapName(type, id)
            .orElseThrow { RuntimeException("Map construction: ${type}/${id}") }
        return try {
            val meta = WorldMeta(Cristalix.mapService().loadMap(mapInfo.latest, BukkitWorldLoader.INSTANCE).get())
            meta.world.setGameRuleValue("mobGriefing", "false")
            meta.world.setGameRuleValue("doTileDrops", "false")
            meta
        } catch (exception: Exception) {
            when (exception) {
                is InterruptedException,
                is ExecutionException -> {
                    exception.printStackTrace()
                    Thread.currentThread().interrupt()
                }
            }
            throw exception
        }
    }
}