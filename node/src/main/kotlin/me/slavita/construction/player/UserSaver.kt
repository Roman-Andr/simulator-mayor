package me.slavita.construction.player

import com.google.gson.GsonBuilder
import me.slavita.construction.app
import me.slavita.construction.city.City
import me.slavita.construction.city.CitySerializer
import me.slavita.construction.city.project.Project
import me.slavita.construction.city.project.ProjectSerializer
import me.slavita.construction.city.storage.BlocksStorage
import me.slavita.construction.city.storage.BlocksStorageSerializer
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.protocol.SaveUserPackage
import me.slavita.construction.structure.BuildingStructureSerializer
import me.slavita.construction.structure.CityCell
import me.slavita.construction.structure.CityCellSerializer
import me.slavita.construction.structure.CityStructure
import me.slavita.construction.structure.CityStructureSerializer
import me.slavita.construction.structure.ClientStructure
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.utils.log
import me.slavita.construction.utils.runAsync
import me.slavita.construction.utils.runTimerAsync
import me.slavita.construction.utils.socket
import me.slavita.construction.utils.toUUID
import me.slavita.construction.utils.user
import me.slavita.construction.world.SlotItem
import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object UserSaver : IRegistrable {
    private val failedSave = hashSetOf<SaveUserPackage>()

    private val gsonSerializer = GsonBuilder()
        .registerTypeAdapter(CityCell::class.java, CityCellSerializer())
        .registerTypeAdapter(WorkerStructure::class.java, BuildingStructureSerializer())
        .registerTypeAdapter(ClientStructure::class.java, BuildingStructureSerializer())
        .registerTypeAdapter(Project::class.java, ProjectSerializer())
        .registerTypeAdapter(BlocksStorage::class.java, BlocksStorageSerializer())
        .registerTypeAdapter(City::class.java, CitySerializer())
        .registerTypeAdapter(CityStructure::class.java, CityStructureSerializer())
        .create()

    override fun register() {
        runTimerAsync(0, (app.waitResponseTime + 1) * 20L) {
            failedSave.forEach {
                trySaveUser(it)
            }
        }
    }

    fun trySaveUser(player: Player) = runAsync {
        trySaveUser(
            player.user.run {
                data.inventory.clear()

                val inventory =
                    if (data.hasFreelance) currentFreelance!!.playerInventory
                    else player.inventory.storageContents

                inventory.forEachIndexed { index, item ->
                    if (item != null) data.inventory.add(SlotItem(item, index, item.getAmount()))
                }
                player.inventory.clear()

                SaveUserPackage(
                    uuid.toString(),
                    gsonSerializer.toJson(data),
                    data.experience,
                    data.totalProjects.toLong(),
                    data.totalBoosters,
                    data.lastIncome,
                    data.money,
                    data.reputation,
                )
            }
        )
    }

    private fun trySaveUser(pckg: SaveUserPackage) = runAsync {
        try {
            log("try save user")
            socket.writeAndAwaitResponse<SaveUserPackage>(pckg)[app.waitResponseTime, TimeUnit.SECONDS]
            log("user saved")
            failedSave.remove(pckg)
            unloadUser(pckg.uuid.toUUID())
        } catch (e: TimeoutException) {
            log("Save timeout")
            failedSave.add(pckg)
        }
    }

    private fun unloadUser(uuid: UUID) = app.users.remove(uuid)
}
