package me.slavita.construction.utils

import me.func.mod.conversation.ModTransfer
import me.func.protocol.data.color.RGB
import me.func.protocol.ui.alert.NotificationButton
import me.func.protocol.ui.alert.NotificationData
import org.bukkit.entity.Player
import java.nio.charset.StandardCharsets
import java.util.*

object Alert {

    private val alertTemplates = hashMapOf<String, NotificationData>()

    fun send(
        player: Player,
        text: String,
        millis: Long,
        frontColor: RGB,
        backGroundColor: RGB,
        chatMessage: String?,
        vararg buttons: NotificationButton
    ) {
        NotificationData(
            UUID.randomUUID(),
            "notify",
            text,
            frontColor.toRGB(),
            backGroundColor.toRGB(),
            millis,
            buttons.asList(),
            chatMessage
        ).send(player)
    }

    fun button(
        message: String,
        command: String,
        color: RGB,
        removeButton: Boolean = false,
        removeNotification: Boolean = true
    ): NotificationButton =
        NotificationButton(message, color.toRGB(), command, removeButton, removeNotification)

    fun find(key: String) = alertTemplates[key]!!

    fun NotificationData.replace(placeholder: String, content: String) =
        clone().apply { this.text?.let { this.text = this.text!!.replace(placeholder, content) } }

    fun NotificationData.send(player: Player) = ModTransfer()
        .byteArray(*ru.cristalix.core.GlobalSerializers.toJson(this).toByteArray(StandardCharsets.UTF_8))
        .send("socials:notify", player)
}