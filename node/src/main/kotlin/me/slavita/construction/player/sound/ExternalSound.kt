package me.slavita.construction.player.sound

import me.func.mod.conversation.ModTransfer
import org.bukkit.entity.Player
import ru.cristalix.core.display.messages.RadioMessage

class ExternalSound(private val sound: String) : SoundSource {
    override fun play(player: Player, volume: Float, pitch: Float) {
        ModTransfer().byteArray(*RadioMessage.serialize(RadioMessage(true, ""))).send("ilyafx:radio", player)
        ModTransfer().byteArray(*RadioMessage.serialize(RadioMessage(true, Music.storageUrl + sound)))
            .send("ilyafx:radio", player)
    }
}