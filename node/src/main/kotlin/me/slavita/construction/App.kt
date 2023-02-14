package me.slavita.construction

import dev.implario.bukkit.platform.Platforms
import dev.implario.platform.impl.darkpaper.PlatformDarkPaper
import me.func.mod.Anime
import me.func.mod.Kit
import me.func.world.WorldMeta
import me.slavita.construction.action.chat.AdminCommands
import me.slavita.construction.action.chat.UserCommands
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.booster.Boosters
import me.slavita.construction.common.utils.register
import me.slavita.construction.dontate.Abilities
import me.slavita.construction.listener.OnActions
import me.slavita.construction.listener.OnChat
import me.slavita.construction.listener.OnJoin
import me.slavita.construction.listener.OnLeave
import me.slavita.construction.listener.OnUserLoad
import me.slavita.construction.listener.PhysicsDisabler
import me.slavita.construction.player.User
import me.slavita.construction.player.UserLoader
import me.slavita.construction.player.UserSaver
import me.slavita.construction.register.BotsManager
import me.slavita.construction.register.MapLoader
import me.slavita.construction.register.ModCallbacks
import me.slavita.construction.register.ModLoader
import me.slavita.construction.register.RealmConfigurator
import me.slavita.construction.register.ServicesLoader
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.ui.ItemsManager
import me.slavita.construction.world.Leaderboards
import me.slavita.construction.utils.AnimeTimer
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.coroutineForAll
import me.slavita.construction.utils.language.EnumLang
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.utils.runTimer
import me.slavita.construction.utils.runTimerAsync
import me.slavita.construction.utils.toUUID
import me.slavita.construction.world.GameWorld
import me.slavita.construction.world.ItemProperties
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import ru.cristalix.core.datasync.EntityDataParameters
import java.util.UUID

lateinit var app: App

class App : JavaPlugin() {

    lateinit var structureMap: WorldMeta
    lateinit var mainWorld: GameWorld
    val chatId = System.getenv("TG_CHAT_ID").toLong()
    val users = hashMapOf<UUID, User>()
    val allBlocks = hashSetOf<ItemProperties>()
    val waitResponseTime = 5L

    val localStaff = hashSetOf(
        "e2543a0a-5799-11e9-8374-1cb72caa35fd",
        "f83a7e5d-9361-11e9-80c4-1cb72caa35fd",
        "ba821208-6b64-11e9-8374-1cb72caa35fd",
    ).map { it.toUUID() }

    var pass = 0L
        private set

    override fun onEnable() {
        app = this

        EntityDataParameters.register()
        Platforms.set(PlatformDarkPaper())

        Anime.include(Kit.DEBUG, Kit.STANDARD, Kit.EXPERIMENTAL, Kit.DIALOG, Kit.LOOTBOX, Kit.NPC)

        register(
            ServicesLoader,
            RealmConfigurator,
            ModLoader,
            MapLoader,
            Boosters,
            UserCommands,
            AdminCommands,
            ModCallbacks,
            ItemsManager,
            Structures,
            LanguageHelper,
            PhysicsDisabler,
            OnJoin,
            OnLeave,
            OnChat,
            OnActions,
            OnUserLoad,
            UserLoader,
            UserSaver,
            BotsManager,
        )

        runTimerAsync(2 * 60 * 20) {
            Leaderboards.load()
        }

        coroutineForAll(1) {
            data.cities.forEach { city ->
                city.projects.forEach { project ->
                    if (project.structure is WorkerStructure) (project.structure as WorkerStructure).build()
                }
            }
        }

        coroutineForAll(20) {
            data.addMoney(income.applyBoosters(BoosterType.INCOME_BOOSTER))
        }

        coroutineForAll(2 * 60 * 20) {
            data.cities.forEach { city ->
                if (data.abilities.contains(Abilities.NO_BRAKE_STRUCTURES)) return@coroutineForAll
                city.breakStructure()
            }
        }

        coroutineForAll(5 * 60 * 20) {
            showcases.forEach { showcase ->
                showcase.updatePrices()
            }
            showcaseMenu?.updateButtons()
            player.accept("Цены обновлены!")
        }

        runTimer(0, 1) {
            pass++

            AnimeTimer.timers.forEach { timer ->
                timer.update()
            }
        }
    }

    override fun onDisable() {
        EnumLang.clean()
    }

    fun getUserOrNull(uuid: UUID) = users[uuid]

    fun getUser(uuid: UUID) = users[uuid]!!

    fun getUser(player: Player) = getUser(player.uniqueId)
}
