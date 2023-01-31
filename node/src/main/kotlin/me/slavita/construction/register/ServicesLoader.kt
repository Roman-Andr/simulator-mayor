package me.slavita.construction.register

import me.func.Lock
import me.func.sound.Category
import me.func.sound.Music
import me.func.stronghold.Stronghold
import me.slavita.construction.app
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.utils.socket
import org.bukkit.Bukkit
import ru.cristalix.core.CoreApi
import ru.cristalix.core.invoice.IInvoiceService
import ru.cristalix.core.invoice.InvoiceService
import ru.cristalix.core.keyboard.IKeyService
import ru.cristalix.core.keyboard.KeyService
import ru.cristalix.core.multichat.ChatMessage
import ru.cristalix.core.multichat.IMultiChatService
import ru.cristalix.core.multichat.MultiChatService
import ru.cristalix.core.party.IPartyService
import ru.cristalix.core.party.PartyService
import ru.cristalix.core.scoreboard.IScoreboardService
import ru.cristalix.core.scoreboard.ScoreboardService
import ru.cristalix.core.transfer.ITransferService
import ru.cristalix.core.transfer.TransferService

object ServicesLoader : IRegistrable {
    override fun register() {
        CoreApi.get().run {
            registerService(ITransferService::class.java, TransferService(socket))
            registerService(IPartyService::class.java, PartyService(socket))
            registerService(IScoreboardService::class.java, ScoreboardService())
            registerService(IInvoiceService::class.java, InvoiceService(socket))
            registerService(IMultiChatService::class.java, MultiChatService(socket))
            registerService(IKeyService::class.java, KeyService(app))
        }

        IMultiChatService.get().run {
            setRealmTag("slvt")
            addSingleChatHandler("construction") { message: ChatMessage ->
                Bukkit.getOnlinePlayers().forEach { player ->
                    player.sendMessage(*message.components.toMutableList().toTypedArray())
                }
            }
        }

        Music.block(Category.MUSIC)
        Lock.realms("SLVT")
        Stronghold.namespace("construction")
    }
}