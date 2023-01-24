package me.slavita.construction.player.sound

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.utils.SOUND_URL
import org.bukkit.entity.Player
import ru.cristalix.core.display.messages.RadioMessage

class ExternalSound(private val sound: String) : SoundSource {
    override fun play(player: Player, volume: Float, pitch: Float) {
        ModTransfer().byteArray(*RadioMessage.serialize(RadioMessage(true, ""))).send("ilyafx:radio", player)
        ModTransfer().byteArray(*RadioMessage.serialize(RadioMessage(true, SOUND_URL + sound)))
            .send("ilyafx:radio", player)
    }
}