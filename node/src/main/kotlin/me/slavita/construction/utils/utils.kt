package me.slavita.construction.utils

import dev.implario.bukkit.event.EventContext
import dev.implario.bukkit.item.ItemBuilder
import dev.implario.bukkit.platform.Platforms
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import me.func.mod.ui.menu.button
import me.func.world.WorldMeta
import me.slavita.construction.app
import me.slavita.construction.dontate.Donates
import me.slavita.construction.ui.Formatter.toCriMoney
import net.minecraft.server.v1_12_R1.EntityPlayer
import net.minecraft.server.v1_12_R1.Packet
import org.apache.logging.log4j.util.BiConsumer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import java.util.*
import kotlin.reflect.KClass

val Player.user
    get() = app.getUser(this)

val UUID.user
    get() = app.getUser(this)

val Player.userOrNull
    get() = app.getUserOrNull(this.uniqueId)

val Player.handle: EntityPlayer
    get() = (this as CraftPlayer).handle

fun labels(key: String, map: WorldMeta = app.mainWorld.map) = map.getLabels(key)

fun label(key: String, map: WorldMeta = app.mainWorld.map) = map.getLabel(key)

fun Float.revert() = when {
    this >= 0 -> this - 180F
    else      -> this + 180F
}

object EmptyListener : Listener

inline fun <reified T : Event> listener(
    priority: EventPriority = EventPriority.NORMAL,
    noinline handler: T.() -> Unit,
) {
    Bukkit.getPluginManager().registerEvent(
        T::class.java, EmptyListener, priority,
        { _, event ->
            if (T::class.java.isInstance(event)) {
                handler.invoke(event as T)
            }
        }, Platforms.getPlugin()
    )
}

@Suppress("UNCHECKED_CAST")
fun <T : Event> listener(
    targetClass: KClass<T>,
    priority: EventPriority = EventPriority.NORMAL,
    handler: T.() -> Unit,
) {
    Bukkit.getPluginManager().registerEvent(
        targetClass.java, EmptyListener, priority,
        { _, event ->
            if (targetClass.java.isInstance(event)) {
                handler.invoke(event as T)
            }
        }, Platforms.getPlugin()
    )
}

inline fun <reified T : Packet<*>> packetListener(player: Player, noinline handler: T.() -> Unit) {
    (player as CraftPlayer).handle.playerConnection.networkManager.channel.pipeline()
        .addBefore("packet_handler", UUID.randomUUID().toString(), object : ChannelDuplexHandler() {
            override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                if (msg is T) {
                    handler.invoke(msg)
                }
                super.write(ctx, msg, promise)
            }
        })
}

fun Location.yaw(yaw: Float) = apply { setYaw(yaw) }

fun String.colored(colors: List<String>): String {
    val result = this
    val chars = result.chunked(1).toMutableList()
    if (colors.size != result.toCharArray().size) return "ERROR"
    colors.forEachIndexed { index, color ->
        chars[index] = "¨" + color.replace("#", "") + chars[index]
    }

    return chars.joinToString("")
}

fun opCommand(name: String, biConsumer: BiConsumer<Player, Array<out String>>) {
    Bukkit.getCommandMap().register("anime", object : Command(name) {
        override fun execute(sender: CommandSender, var2: String, agrs: Array<out String>): Boolean {
            if (sender is Player && sender.isOp) biConsumer.accept(sender, agrs)
            return true
        }
    })
}

fun log(message: String) {
    println("[CONSTRUCTION] $message")
}

val routine = EventContext { true }.fork()

val scheduler: BukkitScheduler = Bukkit.getScheduler()

fun runTimerAsync(start: Long, every: Long, runnable: Runnable) =
    scheduler.runTaskTimerAsynchronously(app, runnable, start, every)

fun runAsync(after: Long, runnable: Runnable): BukkitTask =
    scheduler.runTaskLaterAsynchronously(app, runnable, after)

fun Player.sendPacket(packet: Packet<*>) {
    (this as CraftPlayer).handle.playerConnection.networkManager.sendPacket(packet)
}

fun donateButton(donate: Donates, player: Player) = button {
    item = donate.displayItem
    title = donate.donate.title
    hover = donate.donate.description
    hint = "Купить"
    description = "Цена: ${donate.donate.price.toCriMoney()}"
    backgroundColor = donate.backgroudColor
    onClick { _, _, _ ->
        donate.donate.purchase(player.user)
    }
}

fun getEmptyButton() = button {
    material(Material.AIR)
    hint = ""
    enabled = false
}

fun Material.validate(): ItemStack = if (isItem) ItemBuilder(this).build() else ItemBuilder(Material.BARRIER).build()

fun ItemStack.validate(): ItemStack =
    if (getType().isItem) ItemBuilder(getType()).build() else ItemBuilder(Material.BARRIER).build()

inline fun <T, R> Array<out T>.mapM(transform: (T) -> R): MutableList<R> {
    return map(transform).toMutableList()
}

inline fun <T, R> Iterable<T>.mapM(transform: (T) -> R): MutableList<R> {
    return map(transform).toMutableList()
}
