package me.slavita.construction.service

import ru.cristalix.core.network.Capability
import ru.cristalix.core.network.CorePackage
import ru.cristalix.core.network.ISocketClient
import kotlin.reflect.KClass

fun ISocketClient.capabilities(vararg classes: KClass<out CorePackage>) = registerCapabilities(*classes.map {
    Capability.builder()
        .className(it.java.name)
        .notification(true)
        .build()
}.toTypedArray())