package me.slavita.construction.structure

import me.func.mod.reactive.ReactivePlace
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.player.User
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.PlayerExtensions.accept
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.utils.runAsync
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.GameWorld
import me.slavita.construction.world.ItemProperties

class WorkerStructure(
    structure: Structure,
    playerCell: PlayerCell,
    val workers: HashSet<Worker> = hashSetOf(),
) : BuildingStructure(structure, playerCell) {

    private var remainingBlocks = hashMapOf<ItemProperties, Int>()
    private val blocksStorage = hashMapOf<ItemProperties, Int>()
    private var claimGlow: ReactivePlace? = null

    private val delayTime: Long
        get() {
            if (workers.isEmpty()) return 1
            return (60 / workers.sumOf { it.blocksSpeed }).toLong()
        }

    override fun enterBuilding() {
        remainingBlocks = structure.blocks.clone() as HashMap<ItemProperties, Int>
        claimGlow = ReactivePlace.builder()
            .rgb(GlowColor.GREEN_LIGHT)
            .radius(2.0)
            .x(allocation.x)
            .y(allocation.y - 2.50)
            .z(allocation.z)
            .onEntire { player ->
                player.inventory.storageContents.forEach { item ->
                    if (item == null) return@forEach
                    val props = ItemProperties(item)
                    val value = blocksStorage.getOrDefault(props, 0)
                    if (remainingBlocks.filter { it.value > 0 }.containsKey(props)) {
                        if (item.amount > value) {
                            blocksStorage[props] = blocksStorage.getOrDefault(props, 0) + item.getAmount() - value
                        } else {
                            blocksStorage[props] = blocksStorage.getOrDefault(props, 0) + item.getAmount()
                            player.inventory.remove(item)
                            build()
                        }
                        player.accept("Вы положили ${LanguageHelper.getItemDisplayName(item, player)}")
                    }
                }
            }
            .build().apply {
                isConstant = true
            }
        claimGlow!!.send(owner.player)
        build()
    }

    override fun getBannerInfo(): List<Pair<String, Double>> {
        return listOf(
            Pair("Информация об постройке", 0.7),
            Pair("Название: ${structure.name}", 0.7),
            Pair("Рабочих: ${workers.size}", 0.7),
            Pair("", 0.5),
            Pair("Скорость: ${workers.sumOf { it.blocksSpeed }} блоков в секунду", 0.5),
            Pair("Прогресс: $blocksPlaced из ${structure.blocksCount} блоков", 0.5)
        )
    }

    override fun onShow() {}

    override fun onHide() {}

    override fun blockPlaced() {}

    override fun onFinish() {
        claimGlow!!.delete(setOf(owner.player))
    }

    private fun build() {
        if (state != StructureState.BUILDING) return
        val item = ItemProperties(currentBlock!!.type, currentBlock!!.data)
        if (blocksStorage.getOrDefault(item, 0) <= 0) return
        runAsync(delayTime) {
            if (workers.isNotEmpty()) {
                remainingBlocks[item] = remainingBlocks[item]!! - 1
                blocksStorage[item] = blocksStorage[item]!! - 1
                placeCurrentBlock()
            }
            build()
        }
    }
}
