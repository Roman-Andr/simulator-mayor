package me.slavita.construction.service

import me.func.serviceapi.runListener
import me.slavita.construction.protocol.TestPackage
import ru.cristalix.core.microservice.MicroServicePlatform
import ru.cristalix.core.microservice.MicroserviceBootstrap
import ru.cristalix.core.network.Capability
import ru.cristalix.core.network.ISocketClient

fun main() {
    MicroserviceBootstrap.bootstrap(MicroServicePlatform(2))
    val socketClient = ISocketClient.get()

    socketClient.apply {
        registerCapabilities(
            Capability.builder()
                .className(TestPackage::class.java.name)
                .notification(true)
                .build()
        )

        runListener<TestPackage> { realm, pckg ->
            println("Get from ${realm.realmName} ${pckg.message}")
        }
    }
}
