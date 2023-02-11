package me.slavita.construction.structure

import me.func.atlas.Atlas
import me.func.mod.reactive.ReactivePlace
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.app
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.addByFace
import me.slavita.construction.utils.getFaceCenter
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.utils.loadBannerFromConfig
import me.slavita.construction.utils.notify
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.WHITE
import org.bukkit.entity.Player

class WorkerStructure(
    structure: Structure,
    cell: CityCell,
    val workers: HashSet<Worker> = hashSetOf(),
) : BuildingStructure(structure, cell) {

    val blocksStorage = hashMapOf<ItemProperties, Int>()
    val remainingBlocks = HashMap(structure.blocks)

    private val center = getFaceCenter(cell).addByFace(cell.face)

    private var claimGlow = ReactivePlace.builder()
        .rgb(GlowColor.GREEN_LIGHT)
        .radius(2.0)
        .location(center.clone().apply { y -= 2.5 })
        .onEntire { player ->
            if (blocksDepositBuild(player, remainingBlocks, blocksStorage)) build()
        }
        .build().apply {
            isConstant = true
        }

    private var claimBanner = loadBannerFromConfig(
        Atlas.find("city").getMapList("claim-banner").first(),
        center,
        opacity = 0.0
    )

    private var lastModified = app.pass
    private val delayTime: Long
        get() {
            if (workers.isEmpty()) return 1
            return (60 / workers.sumOf { it.blocksSpeed }).toLong()
        }

    override fun enterBuilding() {
        Banners.show(owner.player, claimBanner)
        claimGlow.send(owner.player)
        continueBuilding()
    }

    override fun continueBuilding() {
        repeat(blocksPlaced) {
            val item = ItemProperties(currentBlock!!.type, currentBlock!!.data)
            remainingBlocks[item] = remainingBlocks[item]!! - 1
            app.mainWorld.placeFakeBlock(owner.player, currentBlock!!.withOffset(allocation))
            currentBlock = structure.getNextBlock(currentBlock!!.position)
        }
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
        hideClaim()
        owner.player.notify(
            """
                ${WHITE}Постройка завершена!
                ${GRAY}Номер: ${GRAY}${cell.id}
                ${AQUA}Название: ${GOLD}${structure.name}
                ${GOLD}Локация: ${GREEN}${cell.city.title}
            """.trimIndent()
        )
    }

    private fun hideClaim() {
        Banners.hide(owner.player, claimBanner)
        claimGlow.delete(setOf(owner.player))
    }

    fun build() {
        if (state != StructureState.BUILDING || app.pass - lastModified < delayTime) return
        lastModified = app.pass

        val item = ItemProperties(currentBlock!!.type, currentBlock!!.data)
        if (blocksStorage.getOrDefault(item, 0) <= 0) return

        if (workers.isNotEmpty()) {
            remainingBlocks[item] = remainingBlocks[item]!! - 1
            blocksStorage[item] = blocksStorage[item]!! - 1
            placeCurrentBlock()
        }
    }

    private fun blocksDepositBuild(
        player: Player,
        target: HashMap<ItemProperties, Int>,
        storage: HashMap<ItemProperties, Int>,
    ): Boolean {
        var deposit = false
        player.inventory.storageContents.forEachIndexed { index, item ->
            if (item == null) return@forEachIndexed
            val props = ItemProperties(item)
            val value = target.getOrDefault(props, 0) - storage.getOrDefault(props, 0)
            if (value <= 0) return@forEachIndexed
            if (target.filter { it.value - storage.getOrDefault(it.key, 0) > 0 }.containsKey(props)) {
                if (item.getAmount() > value) {
                    storage[props] = storage.getOrDefault(props, 0) + item.getAmount() - value
                    player.inventory.storageContents[index].setAmount(item.getAmount() - value)
                } else {
                    storage[props] = storage.getOrDefault(props, 0) + item.getAmount()
                    player.inventory.remove(item)
                }
                deposit = true
                player.accept("Вы положили ${LanguageHelper.getItemDisplayName(item, player)}")
            }
        }
        return deposit
    }
}
