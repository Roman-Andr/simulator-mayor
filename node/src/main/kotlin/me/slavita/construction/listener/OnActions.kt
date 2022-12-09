package me.slavita.construction.listener

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.action.command.menu.project.ChoiceStructure
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.ui.HumanizableValues
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import me.slavita.construction.utils.listener
import me.slavita.construction.utils.music.MusicExtension.playSound
import me.slavita.construction.utils.music.MusicSound
import me.slavita.construction.utils.user
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerMoveEvent
import ru.cristalix.core.formatting.Formatting.fine

object OnActions {
    private val inZone = hashMapOf<Player, Boolean>()
    private var storageEntered = false

    init {
        listener<PlayerDropItemEvent> {
            val user = player.user
            if (!user.blocksStorage.inBox() || itemDrop.itemStack.getType() == Material.CLAY_BALL) {
                isCancelled = true
                return@listener
            }

            player.playSound(MusicSound.LEVEL_UP)
            player.killboard(
                fine(
                    "Вы положили ${drop.itemStack.getAmount()} ${
                        HumanizableValues.BLOCK.get(
                            drop.itemStack.getAmount()
                        )
                    }"
                )
            )
            user.blocksStorage.addItem(drop.itemStack)
            drop.remove()
        }

        listener<PlayerMoveEvent> {
            player.user.run {
                if (watchableProject != null && !watchableProject!!.structure.box.contains(player.location)) {
                    watchableProject!!.onLeave()
                    watchableProject = null
                }

                currentCity.cityStructures.forEach {
                    if (it.cell.box.contains(player.location) && it.state == CityStructureState.BROKEN) {
                        it.repair()
                    }
                }

                if (player.user.blocksStorage.inBox() && !storageEntered) {
                    ModTransfer()
                        .send("storage:show", player)
                    println("Enter Storage")
                    storageEntered = true
                }

                if (!player.user.blocksStorage.inBox() && storageEntered) {
                    ModTransfer()
                        .send("storage:hide", player)
                    println("Leave Storage")
                    storageEntered = false
                }

                if (watchableProject == null) {
                    currentCity.projects.forEach { project ->
                        if (project.structure.box.contains(player.location)) {
                            watchableProject = project
                            project.onEnter()
                            return@listener
                        }
                    }

                    currentCity.cells.forEach { cell ->
                        if (cell.busy || !cell.box.contains(player.location)) return@forEach

                        if (inZone[player] == false) ChoiceStructure(player, cell).tryExecute()
                        inZone[player] = true
                        return@listener
                    }

                    inZone[player] = false
                }
            }
        }
    }
}