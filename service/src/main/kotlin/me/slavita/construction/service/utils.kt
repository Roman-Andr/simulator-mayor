package me.slavita.construction.service

import com.github.kotlintelegrambot.entities.ChatId
import com.mongodb.client.model.Projections
import me.func.serviceapi.mongo.MongoAdapter
import me.func.serviceapi.runListener
import me.slavita.construction.protocol.LeaderboardItem
import org.bson.Document
import ru.cristalix.core.network.Capability
import ru.cristalix.core.network.CorePackage
import ru.cristalix.core.network.ISocketClient
import ru.cristalix.core.realm.RealmId

fun ISocketClient.capabilities(vararg classes: Class<out CorePackage>) = registerCapabilities(
    *classes.map { packet ->
        Capability.builder()
            .className(packet.name)
            .notification(false)
            .build()
    }.toTypedArray()
)

inline fun <reified T : CorePackage> ISocketClient.listener(crossinline handler: T.(RealmId) -> Unit) {
    capabilities(T::class.java)
    runListener<T> { realm, packet ->
        try {
            // TODO: log with time
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

val prefix = "[${System.getenv("REALM_TYPE")}-${System.getenv("REALM_ID")}]"

fun logFormat(text: String) = "$prefix $text"

fun log(text: String) = println(logFormat(text))

fun logTg(text: String) = LoggerBot.tg.sendMessage(ChatId.fromId(LoggerBot.chatId), logFormat(text))

fun MongoAdapter.getTop(field: String, after: ArrayList<LeaderboardItem>.() -> Unit) {
    val response = arrayListOf<LeaderboardItem>()
    collection
        .find()
        .projection(Projections.include(field))
        .sort(Document().append(field, -1))
        .limit(10)
        .forEach({
            response.add(LeaderboardItem(it.getString("_id"), it.getLong(field)))
        }) { _, _ ->
            after(response)
        }
}
