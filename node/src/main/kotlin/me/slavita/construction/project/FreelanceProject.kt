package me.slavita.construction.project

import me.slavita.construction.app
import me.slavita.construction.player.City
import me.slavita.construction.reward.Reward
import me.slavita.construction.structure.BuildingStructure
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.BlocksExtensions.unaryMinus
import org.bukkit.Material

class FreelanceProject(
    city: City,
    id: Int,
    structure: BuildingStructure,
    rewards: List<Reward>,
) : Project(city, id, structure, rewards) {

    val playerInventory = owner.player.inventory.storageContents.clone()

    init {
        start()
        owner.player.inventory.run {
            clear()
            structure.structure.blocks.forEach { (item, count) ->
                addItem(item.createItemStack(count))
            }
        }
    }

    override fun onEnter() {
        when (structure.state) {
            StructureState.BUILDING -> structure.showVisual()

            StructureState.FINISHED -> {
                owner.currentFreelance = null

                structure.structure.box.forEachBukkit {
                    if (it.type == Material.AIR) return@forEachBukkit

                    val emptyBlock = app.mainWorld.emptyBlock
                        .withOffset(structure.allocation)
                        .withOffset(it.location)
                        .withOffset(-structure.structure.box.min)

                    app.mainWorld.placeFakeBlock(owner.player, emptyBlock, false)
                }
                owner.watchableProject = null
                owner.player.inventory.storageContents = playerInventory

                finish()
            }

            else                    -> {}
        }
    }
}
