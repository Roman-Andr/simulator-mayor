package me.slavita.construction.register

import me.func.world.MapLoader
import me.slavita.construction.app
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.utils.nextTick
import me.slavita.construction.world.GameWorld

object MapLoader : IRegistrable {
    override fun register() {
        app.structureMap = MapLoader.load("construction", "structures")
        val map = MapLoader.load("construction", "test")
        nextTick {
            GameWorld(
                map.apply {
                    world.apply {
                        setGameRuleValue("randomTickSpeed", "0")
                        setGameRuleValue("gameLoopFunction", "false")
                        setGameRuleValue("disableElytraMovementCheck", "true")
                    }
                }
            )
        }
    }
}
