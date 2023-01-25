package me.slavita.construction.service

import me.func.serviceapi.runListener
import me.slavita.construction.protocol.GetUserPackage
import ru.cristalix.core.network.Capability
import ru.cristalix.core.network.CorePackage
import ru.cristalix.core.network.ISocketClient
import ru.cristalix.core.realm.RealmId
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

fun ISocketClient.capabilities(vararg classes: KClass<out CorePackage>) = registerCapabilities(*classes.map {
    Capability.builder()
        .className(it.java.name)
        .notification(false)
        .build()
}.toTypedArray())

inline fun <reified T : CorePackage> ISocketClient.listener(targetClass: KClass<T>, crossinline handler: T.(RealmId) -> Unit) {
    capabilities(targetClass)
    runListener<T> { realm, packet ->
        try {
            //todo: log with time
            println("${targetClass.simpleName}: ${realm.realmName}")
            packet.run {
                handler.invoke(packet, realm)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //todo: add tg log
        }
    }
}
