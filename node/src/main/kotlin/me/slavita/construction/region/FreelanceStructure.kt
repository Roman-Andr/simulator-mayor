package me.slavita.construction.region

import me.slavita.construction.ui.ItemsManager
import me.slavita.construction.utils.AnimeTimer
import me.slavita.construction.utils.deny
import me.slavita.construction.utils.notify

class FreelanceStructure(options: StructureOptions, override val cell: FreelanceCell) : ClientStructureBase(options, cell, false) {

    val playerInventory = user.player.inventory.storageContents.clone()

    val timer = AnimeTimer(user.player) {
        cell.changeChild(null)

        user.apply {
            data.reputation -= 100
            player.deny("Вы не успели выполнить фриланс заказ. Штраф: 100 репутации")
        }
    }

    override fun allocate() {
        super.allocate()

        user.player.inventory.run {
            clear()
            options.blocks.forEach { (item, count) ->
                addItem(item.createItemStack(count))
            }
            setItem(ItemsManager.freelanceCancel.inventoryId, ItemsManager.freelanceCancel.item)
        }
    }

    override fun finish() {
        super.finish()

        user.apply {
            player.notify("Постройка завершена!")
            data.addFreelanceProjects(1)
        }
    }

    override fun remove() {
        super.remove()

        timer.stop()
        user.apply {
            player.inventory.apply {
                clear()
                storageContents = playerInventory
            }
        }
    }
}
