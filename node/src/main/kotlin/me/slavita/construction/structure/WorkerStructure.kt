package me.slavita.construction.structure

import me.func.atlas.Atlas
import me.func.mod.reactive.ReactivePlace
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.element.Banner
import me.slavita.construction.player.User
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.*
import me.slavita.construction.utils.loadBannerFromConfig
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.GameWorld
import me.slavita.construction.world.ItemProperties
import org.bukkit.entity.Player

class WorkerStructure(
    world: GameWorld,
    structure: Structure,
    owner: User,
    playerCell: PlayerCell,
    val workers: HashSet<Worker> = hashSetOf(),
) : BuildingStructure(world, structure, owner, playerCell) {

    private var remainingBlocks = hashMapOf<ItemProperties, Int>()
    private val blocksStorage = hashMapOf<ItemProperties, Int>()
    private var claimGlow: ReactivePlace? = null
    private var claimBanner: Banner? = null

    private val delayTime: Long
        get() {
            if (workers.isEmpty()) return 1
            return (60 / workers.sumOf { it.blocksSpeed }).toLong()
        }

    override fun enterBuilding() {
        remainingBlocks = HashMap(structure.blocks)
        val center = getFaceCenter(cell)
        claimGlow = ReactivePlace.builder()
            .rgb(GlowColor.GREEN_LIGHT)
            .radius(2.0)
            .location(center.clone().apply { y -= 2.5 })
            .onEntire { player ->
                if (blocksDepositBuild(player, remainingBlocks, blocksStorage)) build()
            }
            .build().apply {
                isConstant = true
            }
        claimBanner = loadBannerFromConfig(
            Atlas.find("city").getMapList("claim-banner").first(),
            center,
            opacity = 0.0
        )
        Banners.show(owner.player, claimBanner!!)
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
        hideClaim()
    }

    private fun hideClaim() {
        Banners.hide(owner.player, claimBanner!!)
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
