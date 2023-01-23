package me.slavita.construction.utils

import com.github.kotlintelegrambot.entities.ChatId
import dev.implario.bukkit.event.EventContext
import dev.implario.bukkit.item.ItemBuilder
import dev.implario.bukkit.platform.Platforms
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.func.mod.Anime
import me.func.mod.reactive.ReactiveBanner
import me.func.mod.ui.Glow
import me.func.mod.reactive.ButtonClickHandler
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.mod.ui.menu.selection.Selection
import me.func.mod.util.after
import me.func.mod.world.Banners
import me.func.mod.world.Banners.location
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.RGB
import me.func.protocol.data.color.Tricolor
import me.func.protocol.data.element.Banner
import me.func.protocol.data.element.MotionType
import me.func.world.WorldMeta
import me.slavita.construction.action.command.menu.ButtonCommand
import me.slavita.construction.app
import me.slavita.construction.bank.Bank
import me.slavita.construction.dontate.Donates
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.toCriMoney
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import net.minecraft.server.v1_12_R1.*
import org.apache.logging.log4j.util.BiConsumer
import org.bukkit.*
import org.bukkit.ChatColor.*
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.BlockFace
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import org.bukkit.util.Vector
import ru.cristalix.core.account.IAccountService
import ru.cristalix.core.formatting.Formatting
import ru.cristalix.core.math.V3
import ru.cristalix.core.permissions.IPermissionContext
import ru.cristalix.core.permissions.IPermissionService
import ru.cristalix.core.network.ISocketClient
import ru.cristalix.core.realm.IRealmService
import java.util.*
import kotlin.reflect.KClass

val socket = ISocketClient.get()

val STORAGE_URL = "https://storage.c7x.dev/${System.getenv("STORAGE_USER")}/construction"

val Player.user
    get() = app.getUser(this)

val Player.cristalixName
    get() = getDisplayName(this.uniqueId)

val Player.userOrNull
    get() = app.getUserOrNull(uniqueId)

val Player.handle: EntityPlayer
    get() = (this as CraftPlayer).handle

val scheduler: BukkitScheduler = Bukkit.getScheduler()

fun ReactiveButton.click(click: ButtonClickHandler) = apply {
    onClick = ButtonClickHandler { player, index, button ->
        ButtonCommand(player) {
            click.handle(player, index, button)
        }.tryExecute()
    }
}

fun nextTick(runnable: Runnable) {
    Bukkit.getScheduler().runTask(Platforms.getPlugin(), runnable)
}

fun coroutine(block: suspend CoroutineScope.() -> Unit) = CoroutineScope(Dispatchers.IO).launch { block() }

fun coroutineForAll(every: Long, task: User.() -> Unit) {
    scheduler.scheduleSyncRepeatingTask(app, {
        app.users.forEach { (_, user) ->
            task.invoke(user)
        }
    }, 0L, every)
}

fun Player.deny(text: String) {
    killboard(Formatting.error(text))
    playSound(MusicSound.DENY)
    Glow.animate(this, 0.4, GlowColor.RED)
}

fun Player.accept(text: String) {
    killboard(Formatting.fine(text))
    playSound(MusicSound.LEVEL_UP)
    Glow.animate(this, 0.4, GlowColor.GREEN)
}

fun Player.sendPacket(packet: Packet<*>) {
    (this as CraftPlayer).handle.playerConnection.networkManager.sendPacket(packet)
}

fun Player.killboard(text: String) {
    Anime.killboardMessage(this, text)
}

fun Player.cursor(text: String) {
    Anime.cursorMessage(this, text)
}

val UUID.user
    get() = app.getUser(this)

val UUID.cristalixName
    get() = getDisplayName(this)

private fun getDisplayName(uuid: UUID): String {
    val name = IAccountService.get().getNameByUuid(uuid).get()
    return getDisplayNameFromContext(IPermissionService.get().getPermissionContextDirect(uuid), name)
}

fun labels(key: String, map: WorldMeta = app.mainWorld.map) = map.getLabels(key)

fun label(key: String, map: WorldMeta = app.mainWorld.map) = map.getLabel(key)

fun label(key: String, tag: String, map: WorldMeta = app.mainWorld.map) = map.getLabel(key, tag)

fun Float.revert() = when {
    this >= 0 -> this - 180F
    else      -> this + 180F
}

object EmptyListener : Listener

@Suppress("UNCHECKED_CAST")
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

fun command(name: String, biConsumer: BiConsumer<Player, Array<out String>>) {
    Bukkit.getCommandMap().register("construction", object : Command(name) {
        override fun execute(sender: CommandSender, var2: String, agrs: Array<out String>): Boolean {
            if (sender is Player) biConsumer.accept(sender, agrs)
            return true
        }
    })
}

fun safe(action: () -> Unit) = after(1, action)

fun logFormat(message: String) = "[${IRealmService.get().currentRealmInfo.realmId.realmName}] $message"

fun log(message: String) = println(logFormat(message))

fun logTg(message: String) = app.bot.sendMessage(ChatId.fromId(app.chatId), logFormat(message))

val routine: EventContext = EventContext { true }.fork()

fun runTimerAsync(start: Long, every: Long, runnable: Runnable): BukkitTask =
    scheduler.runTaskTimerAsynchronously(app, runnable, start, every)

fun runTimer(start: Long, every: Long, runnable: Runnable): Int =
    scheduler.scheduleSyncRepeatingTask(app, runnable, start, every)

fun runAsync(after: Long, runnable: Runnable): BukkitTask =
    scheduler.runTaskLaterAsynchronously(app, runnable, after)

fun donateButton(donate: Donates, player: Player) = button {
    item = donate.displayItem
    title = donate.donate.title
    hover = donate.donate.description
    hint = "Купить"
    description = "Цена: ${donate.donate.price.toCriMoney()}"
    backgroundColor = donate.backgroudColor
    click { _, _, _ ->
        donate.donate.purchase(player.user)
    }
}

fun getEmptyButton() = button {
    material(Material.AIR)
    hint = ""
    enabled = false
}

fun ItemStack.validate(): ItemStack =
    if (getType().isItem) this else ItemBuilder(Material.BARRIER).build()

inline fun <T, R> Array<out T>.mapM(transform: (T) -> R): MutableList<R> {
    return map(transform).toMutableList()
}

inline fun <T, R> Iterable<T>.mapM(transform: (T) -> R): MutableList<R> {
    return map(transform).toMutableList()
}

inline fun <K, V, R> Map<out K, V>.mapM(transform: (Map.Entry<K, V>) -> R): MutableList<R> {
    return map(transform).toMutableList()
}

inline fun <T, R> Iterable<T>.mapIndexedM(transform: (index: Int, T) -> R): MutableList<R> {
    return mapIndexed(transform).toMutableList()
}

fun PlayerInventory.swapItems(firstIndex: Int, secondIndex: Int) {
    val firstItem = getItem(firstIndex)
    setItem(firstIndex, getItem(secondIndex))
    setItem(secondIndex, firstItem)
}

fun Banners.show(player: Player, pair: Pair<Banner, Banner>) {
    show(player, pair.first)
    show(player, pair.second)
}

fun Banners.hide(player: Player, pair: Pair<Banner, Banner>) {
    hide(player, pair.first)
    hide(player, pair.second)
}

fun String.toUUID(): UUID = UUID.fromString(this)

fun getBaseSelection(info: MenuInfo, user: User): Selection =
    selection {
        title = info.title
        vault = info.type.vault
        rows = info.rows
        columns = info.columns
        money = "Ваш ${info.type.title} ${
            when (info.type) {
                StatsType.MONEY  -> user.data.statistics.money.toMoney()
                StatsType.LEVEL  -> user.data.statistics.level
                StatsType.CREDIT -> Bank.playersData[user.player.uniqueId]!!.sumOf { it.creditValue }.toMoney()
            }
        }"
    }

private fun getDisplayNameFromContext(context: IPermissionContext, name: String): String {
    val group = context.displayGroup
    val color = if (context.color == null) "" else context.color
    val prefix =
        if (context.customProfile.chatPrefix != null) context.customProfile.chatPrefix else (group.prefixColor + group.prefix)
    return ((if (prefix.isNotEmpty()) "$prefix " else "") + group.nameColor) + color + name
}

fun Location.toPosition(): BlockPosition = BlockPosition(x, y, z)

fun Location.toV3(): V3 = V3(x, y, z)

operator fun Location.unaryMinus(): Location = Location(world, -x, -y, -z, yaw, pitch)

fun BlockPosition.toLocation(world: World): Location =
    Location(world, this.x.toDouble(), this.y.toDouble(), this.z.toDouble())

fun BlockPosition.add(position: Location): BlockPosition = this.add(position.x, position.y, position.z)

operator fun BlockPosition.minus(additionalPosition: Location): BlockPosition = BlockPosition(
    this.x - additionalPosition.x,
    this.y - additionalPosition.y,
    this.z - additionalPosition.z
)

fun BlockFace.toYaw(): Float = when (this) {
    BlockFace.EAST       -> -90
    BlockFace.WEST       -> 90
    BlockFace.SOUTH      -> 0
    BlockFace.NORTH      -> 180
    BlockFace.NORTH_WEST -> 135
    BlockFace.NORTH_EAST -> -135
    BlockFace.SOUTH_WEST -> 45
    BlockFace.SOUTH_EAST -> -45
    else                 -> 0
}.toFloat()

fun getFaceCenter(cell: PlayerCell) = cell.box.bottomCenter.clone().apply {
    when (cell.face) {
        BlockFace.EAST       -> x = cell.box.max.x
        BlockFace.NORTH      -> z = cell.box.min.z
        BlockFace.WEST       -> x = cell.box.min.x
        BlockFace.SOUTH      -> z = cell.box.max.z
        BlockFace.NORTH_EAST -> {
            x = cell.box.max.x
            z = cell.box.min.z
        }

        BlockFace.NORTH_WEST -> {
            x = cell.box.min.x
            z = cell.box.min.z
        }

        BlockFace.SOUTH_EAST -> {
            x = cell.box.max.x
            z = cell.box.max.z
        }

        BlockFace.SOUTH_WEST -> {
            x = cell.box.min.x
            z = cell.box.max.z
        }

        else                 -> throw IllegalArgumentException("Incorrect structure face")
    }
}

private const val OFFSET = 0.52

fun loadBanner(banner: Map<*, *>, location: Location, withPitch: Boolean = false, opacity: Double = 0.45) {
    Banners.new(
        loadBannerFromConfig(banner, location, withPitch, opacity)
    )
}

fun loadBannerFromConfig(
    banner: Map<*, *>,
    location: Location,
    withPitch: Boolean = false,
    opacity: Double = 0.45,
) =
    Banner.builder()
        .weight(banner["weight"] as Int)
        .height(banner["height"] as Int)
        .content(banner["content"] as String)
        .carveSize(banner["carve-size"] as Double)
        .opacity(opacity)
        .x(location.toCenterLocation().x)
        .y(location.y + banner["offset"] as Double)
        .z(location.toCenterLocation().z)
        .xray(0.0)
        .apply {
            if (withPitch) {
                watchingOnPlayer(true)
            } else {
                watchingOnPlayerWithoutPitch(true)
            }
            (banner["lines-size"] as List<*>).forEachIndexed { index, value ->
                this.resizeLine(index, value as Double)
            }
        }
        .build()

fun createFloorBanner(location: Location, color: RGB): Banner {
    return createBanner(
        BannerInfo(
            location,
            BlockFace.UP,
            listOf(),
            16 * 23,
            16 * 23,
            color,
            0.24,
            MotionType.CONSTANT,
            -90.0F
        )
    )
}

fun createDual(info: BannerInfo): Pair<Banner, Banner> {
    info.run {
        return Pair(
            createBanner(BannerInfo(source, blockFace, content, width, height, color, opacity, motionType, pitch)),
            createBanner(
                BannerInfo(
                    Location(
                        source.world,
                        source.x + blockFace.modX,
                        source.y,
                        source.z + blockFace.modZ
                    ), blockFace.oppositeFace, content, width, height, color, opacity, motionType, pitch
                )
            )
        )
    }
}

fun createRectangle(center: Location, radius: Double, color: Tricolor, width: Int, height: Int): HashSet<Banner> {
    val banners = hashSetOf<Banner>()
    listOf(BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH, BlockFace.EAST).forEach {
        banners.addAll(
            createDual(
                BannerInfo(
                    center.clone().add(it.modX * radius, 0.0, it.modZ * radius),
                    it,
                    listOf(),
                    width * 16,
                    height * 16,
                    color,
                    0.25
                )
            ).toList()
        )
    }
    return banners
}

private fun createBanner(info: BannerInfo): Banner {
    info.run {
        return ReactiveBanner.builder()
            .weight(width)
            .height(height)
            .opacity(opacity)
            .color(color)
            .motionType(motionType)
            .xray(0.0)
            .carveSize(carveSize)
            .apply {
                content(content.joinToString("\n") { it.first })
                content.forEachIndexed { index, value ->
                    resizeLine(index, value.second)
                }
            }
            .watchingOnPlayer(watchingOnPlayer)
            .build()
            .apply {
                location(
                    source.clone().add(Vector(OFFSET * blockFace.modX, -0.5 + height / 16, OFFSET * blockFace.modZ))
                        .apply {
                            setYaw(blockFace.toYaw())
                            setPitch(info.pitch)
                        }
                )
            }
    }
}
