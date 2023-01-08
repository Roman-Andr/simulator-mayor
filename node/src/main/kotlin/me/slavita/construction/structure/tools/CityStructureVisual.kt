package me.slavita.construction.structure.tools

import me.func.mod.Anime
import me.func.mod.reactive.ReactivePlace
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.element.Banner
import me.func.protocol.world.marker.Marker
import me.func.protocol.world.marker.MarkerSign
import me.slavita.construction.banner.BannerUtil
import me.slavita.construction.structure.CityStructure
import me.slavita.construction.ui.HumanizableValues
import me.slavita.construction.utils.accept
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class CityStructureVisual(val structure: CityStructure) {
    private var redBanner: Banner? = null
    private var repairGlow: ReactivePlace? = null
    private var marker: Marker? = null

    init {
        val center = structure.playerCell.box.center
        redBanner = BannerUtil.createFloorBanner(
            center.clone().apply {
                y = structure.box.min.y - 22.49
                z = structure.box.min.z
            }, GlowColor.RED
        )
        marker = Marker(center.x, center.y, center.z, 80.0, MarkerSign.ARROW_DOWN)
        repairGlow = ReactivePlace.builder()
            .rgb(GlowColor.GREEN_LIGHT)
            .radius(2.0)
            .location(structure.playerCell.box.min.clone().apply { y -= 2.5 })
            .onEntire { player ->
                debug()

                if (blocksDepositRepair(player, structure.targetBlocks, structure.repairBlocks)) {
                    println("depositing...")

                    structure.repairBlocks.forEach {
                        if (structure.targetBlocks[it.key]!! <= 0) structure.targetBlocks.remove(it.key)
                    }

                    debug()
                    if (structure.targetBlocks.isEmpty()) {
                        println("repairing...")
                        structure.repair()
                    }
                }
            }
            .build()
    }

    fun debug() {
        println("repair")
        structure.repairBlocks.values.forEach {
            println("$it")
        }
        println("target")
        structure.targetBlocks.values.forEach {
            println("$it")
        }
        println("")
    }

    private fun blocksDepositRepair(
        player: Player,
        target: java.util.HashMap<ItemProperties, Int>,
        storage: java.util.HashMap<ItemProperties, Int>,
    ): Boolean {
        var deposit = false
        var toDeposit = 0
        player.inventory.storageContents.forEachIndexed { index, item ->
            if (item == null) return@forEachIndexed
            val props = ItemProperties(item)
            val blocks = storage.getOrDefault(props, 0)
            val value = target.getOrDefault(props, 0)
            if (value == 0) return@forEachIndexed
            if (target.containsKey(props)) {
                if (item.getAmount() > value) {
                    storage[props] = blocks + value
                    target[props] = 0
                    player.inventory.storageContents[index].setAmount(item.getAmount() - value)
                    toDeposit += value
                } else {
                    storage[props] = blocks + item.getAmount()
                    target[props] = value - item.getAmount()
                    player.inventory.remove(item)
                    toDeposit += item.getAmount()
                }
                deposit = true
            }
        }
        if (deposit) player.accept("Вы положили ${ChatColor.GOLD}$toDeposit ${HumanizableValues.BLOCK.get(toDeposit)}")
        return deposit
    }

    fun update() {
        when (structure.state) {
            CityStructureState.NOT_READY   -> {
                Banners.hide(structure.owner, redBanner!!)
                Anime.removeMarker(structure.owner, marker!!)
            }

            CityStructureState.FUNCTIONING -> {
                Banners.hide(structure.owner, redBanner!!)
                Anime.removeMarker(structure.owner, marker!!)
                repairGlow!!.delete(setOf(structure.owner))
            }

            CityStructureState.BROKEN      -> {
                Banners.show(structure.owner, redBanner!!)
                Anime.marker(structure.owner, marker!!)
                repairGlow!!.send(structure.owner)
                structure.targetBlocks = HashMap(structure.structure.blocks)
            }
        }
    }
}
