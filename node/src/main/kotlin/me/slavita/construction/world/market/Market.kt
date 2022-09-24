package me.slavita.construction.world.market

import dev.implario.bukkit.item.ItemBuilder
import me.slavita.construction.app
import me.slavita.construction.structure.Structures
import net.minecraft.server.v1_12_R1.*
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.entity.Player

object Market {
    private val blocksSet = mutableSetOf<BlockData>()

    init {
        Structures.structureGroups.forEach { group ->
            group.structures.forEach { structure ->
                val min = structure.box.min
                val max = structure.box.max
                (max.y.toInt() downTo min.y.toInt()).forEach { y ->
                    (max.x.toInt() downTo min.x.toInt()).forEach { x ->
                        (max.z.toInt() downTo min.z.toInt()).forEach z@ { z ->
                            val curBlock = app.structureMap.world.getBlockAt(x, y, z)
                            if (curBlock.type == Material.AIR) return@z
                            blocksSet.add(BlockData(
                                curBlock.type.toString(),
                                curBlock.type,
                                curBlock.data
                            ))
                        }
                    }
                }
            }
        }
    }

    fun sendPlayer(player: Player) {
        val chunkSize = 1
        val blocks = blocksSet.toTypedArray().toList().chunked(chunkSize)
        var counter = 0

        app.mainWorld.getShopBox().forEachBukkit { loc ->
            if (loc.block.type != Material.DIAMOND_BLOCK || blocks.size <= counter) return@forEachBukkit

            val targetLocation = loc.toCenterLocation()
            blocks[counter].forEach {
                val stand = EntityArmorStand(app.mainWorld.map.world.handle).apply {
                    setLocation(targetLocation.x, targetLocation.y, targetLocation.z, 0.0f, 0.0f)
                    customNameVisible = false
                    isNoGravity = true
                    isSmall = true
                    isInvisible = true
                    setSlot(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(ItemBuilder(it.type).data(it.data.toInt()).build()))
                }
                mutableListOf<Packet<PacketListenerPlayOut>>(
                    PacketPlayOutSpawnEntityLiving(stand),
                    PacketPlayOutEntityMetadata(stand.getId(), stand.dataWatcher, false),
                    PacketPlayOutEntityEquipment(stand.getId(), EnumItemSlot.HEAD, stand.getEquipment(EnumItemSlot.HEAD))
                ).forEach { packet ->
                    (player as CraftPlayer).handle.playerConnection.sendPacket(packet)
                }
            }

            counter++
        }
    }
}