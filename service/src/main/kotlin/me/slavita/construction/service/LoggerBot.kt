package me.slavita.construction.service

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch

object LoggerBot {
    val chatId = System.getenv("TG_CHAT_ID").toLong()

    val tg: Bot = bot {
        token = System.getenv("TG_TOKEN")
        dispatch {}
    }
}