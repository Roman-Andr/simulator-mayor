package me.slavita.construction.player.events

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent
import dev.xdark.feder.EmptyChunkBiome
import dev.xdark.feder.FixedChunkLight
import me.func.mod.util.after
import org.bukkit.entity.EntityType
import org.bukkit.entity.FallingBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.*
import org.bukkit.event.entity.*
import org.bukkit.event.hanging.HangingBreakByEntityEvent
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.player.*
import org.bukkit.event.world.ChunkLoadEvent
import org.spigotmc.event.entity.EntityDismountEvent

class PhysicsDisabler : Listener {
    @EventHandler
    fun ChunkLoadEvent.handle() {
        chunk.biome = EmptyChunkBiome.INSTANCE
        chunk.emittedLight = FixedChunkLight((-1).toByte())
    }

    @EventHandler
    fun CraftItemEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun PlayerInteractEntityEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun PlayerInteractEvent.handle() {
        if (action == Action.RIGHT_CLICK_BLOCK) isCancelled = true
    }

    @EventHandler
    fun EntityDismountEvent.handle() {
        if (dismounted.type === EntityType.BAT) after(1) {
            dismounted.remove()
        }
    }

    @EventHandler
    fun EntityChangeBlockEvent.handle() {
        if (entity is FallingBlock) isCancelled = true
    }

    @EventHandler
    fun EntityDamageEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun FoodLevelChangeEvent.handle() {
        foodLevel = 20
    }

    @EventHandler
    fun BlockFadeEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun BlockPhysicsEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun BlockSpreadEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun BlockBreakEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun BlockGrowEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun BlockFromToEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun PlayerDropItemEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun HangingBreakByEntityEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun BlockBurnEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun EntityExplodeEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun PlayerArmorStandManipulateEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun CreatureSpawnEvent.handle() {
        isCancelled = spawnReason == CreatureSpawnEvent.SpawnReason.NATURAL
    }

    @EventHandler
    fun PlayerAdvancementCriterionGrantEvent.handle() {
        isCancelled = true
    }

    @EventHandler
    fun PlayerSwapHandItemsEvent.handle() {
        isCancelled = true
    }
}