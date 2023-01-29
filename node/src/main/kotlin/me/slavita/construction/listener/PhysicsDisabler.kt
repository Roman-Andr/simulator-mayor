package me.slavita.construction.listener

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent
import dev.xdark.feder.EmptyChunkBiome
import dev.xdark.feder.FixedChunkLight
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.utils.listener
import me.slavita.construction.utils.safe
import org.bukkit.ChatColor.GRAY
import org.bukkit.entity.EntityType
import org.bukkit.entity.FallingBlock
import org.bukkit.event.block.*
import org.bukkit.event.entity.*
import org.bukkit.event.hanging.HangingBreakByEntityEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.player.*
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.StructureGrowEvent
import org.spigotmc.event.entity.EntityDismountEvent

object PhysicsDisabler : IRegistrable {
    override fun register() {
        listOf(
            BlockPlaceEvent::class,
            CraftItemEvent::class,
            PlayerInteractEntityEvent::class,
            PlayerSwapHandItemsEvent::class,
            PlayerAdvancementCriterionGrantEvent::class,
            PlayerArmorStandManipulateEvent::class,
            EntityExplodeEvent::class,
            BlockBurnEvent::class,
            HangingBreakByEntityEvent::class,
            BlockFromToEvent::class,
            BlockGrowEvent::class,
            EntityDamageEvent::class,
            BlockBreakEvent::class,
            BlockSpreadEvent::class,
            BlockPhysicsEvent::class,
            BlockFadeEvent::class,
            EntityCombustEvent::class,
            LeavesDecayEvent::class,
            StructureGrowEvent::class,
        ).forEach {
            listener(it) { isCancelled = true }
        }

        listener<ChunkLoadEvent> {
            chunk.biome = EmptyChunkBiome.INSTANCE
            chunk.emittedLight = FixedChunkLight((-1).toByte())
        }
        listener<EntityDismountEvent> { if (dismounted.type === EntityType.BAT) safe { dismounted.remove() } }
        listener<EntityChangeBlockEvent> { if (entity is FallingBlock) isCancelled = true }
        listener<FoodLevelChangeEvent> { foodLevel = 20 }
        listener<CreatureSpawnEvent> { isCancelled = spawnReason == CreatureSpawnEvent.SpawnReason.NATURAL }
        listener<AsyncPlayerChatEvent> { format = "%1\$s ${GRAY}» §7%2\$s" }
    }
}
