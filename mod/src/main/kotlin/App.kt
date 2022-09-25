import ru.cristalix.clientapi.KotlinMod
import ru.cristalix.uiengine.UIEngine

lateinit var mod: App

class App : KotlinMod() {

    override fun onEnable() {
        UIEngine.initialize(this)
        mod = this
        StructureBuilding()
    }
}