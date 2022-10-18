package me.slavita.construction.player.events

import me.func.mod.util.after
import me.slavita.construction.action.command.menu.project.ChoiceStructure
import me.slavita.construction.app
import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.prepare.*
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.Material
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

object PlayerEvents : Listener {
    private val inZone = hashMapOf<Player, Boolean>()

    @EventHandler
    fun PlayerJoinEvent.handle() {
        after (2) {
            app.addUser(player).run {
                listOf(
                    PlayerWorldIPrepare,
                    ConnectionIPrepare,
                    PermissionsIPrepare,
                    UIIPrepare,
                    ItemCallbacksIPrepare,
                    ShowcaseIPrepare,
                    BankAccountRegister
                ).forEach { it.prepare(this) }
                player.setResourcePack("")
            }

            ConnectionUtil.registerWriter(player.uniqueId) { packet ->
                if (packet !is PacketPlayOutBlockChange) return@registerWriter
                if (packet.block.material != Material.AIR) return@registerWriter

                packet.a = BlockPosition(0, 0, 0)
            }
        }
    }

    @EventHandler
    fun PlayerMoveEvent.handle() {
        app.getUser(player).run {
            if (watchableProject != null && !watchableProject!!.structure.box.contains(player.location)) {
                watchableProject!!.onLeave()
                watchableProject = null
            }

            if (watchableProject == null) {
                city.projects.forEach {
                    if (it.structure.box.contains(player.location)) {
                        watchableProject = it
                        it.onEnter()
                        return
                    }
                }

                city.cells.forEach {
                    if (it.busy || !it.box.contains(player.location)) return@forEach

                    if (inZone[player] == false) ChoiceStructure(player, it).tryExecute()
                    inZone[player] = true
                    return
                }

                inZone[player] = false
            }
        }
    }
}