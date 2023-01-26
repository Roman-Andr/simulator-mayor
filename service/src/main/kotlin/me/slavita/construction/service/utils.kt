package me.slavita.construction.service

import com.github.kotlintelegrambot.entities.ChatId
import me.func.serviceapi.runListener
import ru.cristalix.core.network.Capability
import ru.cristalix.core.network.CorePackage
import ru.cristalix.core.network.ISocketClient
import ru.cristalix.core.realm.RealmId

fun ISocketClient.capabilities(vararg classes: Class<out CorePackage>) = registerCapabilities(*classes.map {
    Capability.builder()
        .className(it.name)
        .notification(false)
        .build()
}.toTypedArray())

inline fun <reified T : CorePackage> ISocketClient.listener(crossinline handler: T.(RealmId) -> Unit) {
    capabilities(T::class.java)
    runListener<T> { realm, packet ->
        try {
            //todo: log with time
            log("${T::class.simpleName}: ${realm.realmName}")
            packet.run {
                handler.invoke(packet, realm)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            logTg("${logFormat("ERROR: ")}${e.message}")
        }
    }
}

fun logFormat(text: String) = "[${System.getenv("REALM_TYPE")}-${System.getenv("REALM_ID")}] $text"

fun log(text: String) = println(logFormat(text))

fun logTg(text: String) = LoggerBot.tg.sendMessage(ChatId.fromId(LoggerBot.chatId), text)
