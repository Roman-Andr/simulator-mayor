package me.slavita.construction.game.player.events

import clepto.bukkit.B
import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent
import dev.xdark.feder.EmptyChunkBiome
import dev.xdark.feder.FixedChunkLight
import me.slavita.construction.App
import me.slavita.construction.app
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
    fun disable(event: ChunkLoadEvent) {
        val chunk = event.chunk
        chunk.biome = EmptyChunkBiome.INSTANCE
        chunk.emittedLight = FixedChunkLight((-1).toByte())
    }

    @EventHandler
    fun disable(event: CraftItemEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: PlayerInteractEntityEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: PlayerInteractEvent) {
        if (event.action == Action.RIGHT_CLICK_BLOCK) event.isCancelled = true
    }

    @EventHandler
    fun disable(event: EntityDismountEvent) {
        if (event.dismounted.type === EntityType.BAT) B.postpone(1) {
            event.dismounted.remove()
        }
    }

    @EventHandler
    fun disable(event: EntityChangeBlockEvent) {
        if (event.entity is FallingBlock) event.isCancelled = true
    }

    @EventHandler
    fun disable(event: EntityDamageEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: FoodLevelChangeEvent) {
        event.foodLevel = 20
    }

    @EventHandler
    fun disable(event: BlockFadeEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: BlockPhysicsEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: BlockSpreadEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: BlockBreakEvent) {
        if (event.getPlayer().world == app.map.world) event.isCancelled = true
    }

    @EventHandler
    fun disable(event: BlockGrowEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: BlockFromToEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: PlayerDropItemEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: HangingBreakByEntityEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: BlockBurnEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: EntityExplodeEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: PlayerArmorStandManipulateEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: CreatureSpawnEvent) {
        event.isCancelled = event.spawnReason == CreatureSpawnEvent.SpawnReason.NATURAL
    }

    @EventHandler
    fun disable(event: PlayerAdvancementCriterionGrantEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun disable(event: PlayerSwapHandItemsEvent) {
        event.isCancelled = true
    }
}