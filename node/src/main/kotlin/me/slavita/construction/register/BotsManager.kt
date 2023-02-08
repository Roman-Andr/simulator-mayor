package me.slavita.construction.register

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.utils.logTg

object BotsManager : IRegistrable {

    val tg = bot {
        token = System.getenv("TG_TOKEN")
        dispatch {}
    }

    override fun register() {
        tg.startPolling()
        logTg("Node initialized")
    }
}
