import dev.xdark.clientapi.event.render.PlayerListRender
import ru.cristalix.clientapi.KotlinMod
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.utility.V3

lateinit var mod: App

class App : KotlinMod() {

    lateinit var cube: V3
    var inited = false
    var gameActive = false

    override fun onEnable() {
        UIEngine.initialize(this)

        mod = this

        registerHandler<PlayerListRender> { isCancelled = gameActive }
    }
}