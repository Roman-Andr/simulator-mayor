package me.slavita.construction.region

import me.slavita.construction.app
import me.slavita.construction.player.sound.MusicSound
import me.slavita.construction.utils.createWorldProgress
import me.slavita.construction.utils.playSound
import me.slavita.construction.utils.unaryMinus
import org.bukkit.ChatColor
import org.bukkit.Material

abstract class BuildingStructure(options: StructureOptions, cell: Cell, val saveFakeBlocks: Boolean) : Structure(options, cell) {

    var blocksPlaced = 0
    var currentBlock = options.getFirstBlock()

    val progressBar = StructureProgressBar(user.player, blocksCount)
    val progressWorld = createWorldProgress(cell)

    override fun allocate() {
        repeat(blocksPlaced) {
            app.mainWorld.placeFakeBlock(user.player, currentBlock.withOffset(allocation), saveFakeBlocks)
            currentBlock = options.getNextBlock(currentBlock.position)!!
        }
    }

    override fun enter() {
        super.enter()
        progressBar.show()
        progressWorld.send(user.player)
    }

    override fun leave() {
        super.leave()
        progressBar.hide()
        progressWorld.delete(setOf(user.player))
    }

    fun placeCurrentBlock() {
        user.player.playSound(MusicSound.HINT)

        app.mainWorld.placeFakeBlock(
            user.player,
            currentBlock.withOffset(allocation),
            saveFakeBlocks
        )

        options.getNextBlock(currentBlock.position).run {
            if (this == null) finish()
            else {
                currentBlock = this
                blocksPlaced++
                blockPlaced()
            }
        }
    }

    override fun remove() {
        super.remove()

        box.forEachBukkit {
            if (it.type == Material.AIR) return@forEachBukkit

            val emptyBlock = app.mainWorld.emptyBlock
                .withOffset(allocation)
                .withOffset(it.location)
                .withOffset(-box.min)

            app.mainWorld.placeFakeBlock(user.player, emptyBlock, false)
        }

        if (saveFakeBlocks) app.mainWorld.clearCellBlocks(user.uuid, cell.worldCell)
    }

    open fun finish() {
        cell.changeChild(StaticStructure(options, cell))
    }

    open fun blockPlaced() {
        progressBar.update(blocksPlaced)
        progressWorld.apply {
            progress = blocksPlaced.toDouble() / blocksCount.toDouble()
            text = "${ChatColor.WHITE}Поставлено блоков: ${ChatColor.WHITE}${blocksPlaced} ${ChatColor.WHITE}из ${ChatColor.AQUA}${blocksCount}"
        }
        //TODO: add content
        infoBanner.setContent("aboba")
    }
}
