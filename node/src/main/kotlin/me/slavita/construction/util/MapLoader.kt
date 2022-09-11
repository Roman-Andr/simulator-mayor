package me.slavita.construction.util

import clepto.cristalix.Cristalix
import clepto.cristalix.WorldMeta
import ru.cristalix.core.map.BukkitWorldLoader
import java.util.concurrent.ExecutionException

class MapLoader {

    fun load(map: String?): WorldMeta? {
        // Загрузка карты с сервера BUIL-3
        val mapInfo = Cristalix.mapService().getLatestMapByGameTypeAndMapName("construction", map)
            .orElseThrow { RuntimeException("Map construction:${map}") }
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