package me.slavita.construction.service

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import me.slavita.construction.common.utils.IRegistrable

object LoggerBot : IRegistrable {
    val chatId = System.getenv("TG_CHAT_ID").toLong()

    lateinit var tg: Bot

    override fun register() {
        tg = bot {
            token = System.getenv("TG_TOKEN")
            dispatch {}
        }
        tg.startPolling()
        logTg("Service initialized")
    }
}
