package me.slavita.construction.structure.tools

import me.func.atlas.Atlas
import me.func.mod.Anime
import me.func.mod.reactive.ReactivePlace
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.element.Banner
import me.func.protocol.world.marker.Marker
import me.func.protocol.world.marker.MarkerSign
import me.slavita.construction.structure.CityStructure
import me.slavita.construction.ui.HumanizableValues.BLOCK
import me.slavita.construction.utils.*
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor.GOLD
import org.bukkit.entity.Player

class CityStructureVisual(val structure: CityStructure) {
    private var repairGlow: ReactivePlace? = null
    private var repairBanner: Banner? = null
    private var marker: Marker? = null

    init {
        val center = structure.cell.box.center
        val sideCenter = getFaceCenter(structure.cell).addByFace(structure.cell.face)
        marker = Marker(center.x, center.y, center.z, 80.0, MarkerSign.ARROW_DOWN)
        runAsync(1) {
            repairGlow = ReactivePlace.builder()
                .rgb(GlowColor.GREEN_LIGHT)
                .radius(2.0)
                .location(sideCenter.clone().apply { y -= 2.5 })
                .onEntire { player ->
                    if (blocksDepositRepair(player, structure.targetBlocks, structure.repairBlocks)) {
                        structure.repairBlocks.forEach {
                            if (structure.targetBlocks[it.key]!! <= 0) structure.targetBlocks.remove(it.key)
                        }

                        if (structure.targetBlocks.isEmpty()) {
                            structure.repair()
                        }
                    }
                }
                .build()
            repairBanner = loadBannerFromConfig(
                Atlas.find("city").getMapList("claim-banner").first(),
                sideCenter,
                opacity = 0.0
            )
            update()
        }
    }

    private fun blocksDepositRepair(
        player: Player,
        target: HashMap<ItemProperties, Int>,
        storage: HashMap<ItemProperties, Int>,
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
        if (deposit) player.accept("Вы положили ${GOLD}${BLOCK.get(toDeposit)}")
        return deposit
    }

    fun update() {
        structure.apply {
            when (state) {
                CityStructureState.NOT_READY -> {
                    cell.border.delete(owner)
                    Anime.removeMarker(owner, marker!!)
                }

                CityStructureState.FUNCTIONING -> {
                    cell.border.delete(owner)
                    Anime.removeMarker(owner, marker!!)
                    repairGlow!!.delete(setOf(owner))
                    Banners.hide(owner, repairBanner!!)
                }

                CityStructureState.BROKEN -> {
                    cell.border.color = GlowColor.RED_LIGHT
                    cell.border.send(owner)
                    Anime.marker(owner, marker!!)
                    repairGlow!!.send(owner)
                    Banners.show(owner, repairBanner!!)
                    targetBlocks = HashMap(structure.blocks)
                }
            }
        }
    }

    fun delete() {
        structure.owner.run {
            val player = setOf(this)
            structure.cell.border.delete(structure.owner)
            repairGlow!!.delete(player)
            Banners.hide(this, repairBanner!!)
            Banners.remove(repairBanner!!.uuid)
        }
    }
}
