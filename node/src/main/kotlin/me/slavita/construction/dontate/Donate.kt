package me.slavita.construction.dontate

import me.func.mod.Anime
import me.func.mod.ui.Glow
import me.func.mod.ui.menu.confirmation.Confirmation
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.player.User
import org.bukkit.ChatColor
import ru.cristalix.core.formatting.Formatting
import ru.cristalix.core.invoice.IInvoiceService
import ru.cristalix.core.invoice.Invoice

abstract class Donate(val title: String, val description: String, val price: Int) {
    fun purchase(user: User) {
        val player = user.player

        Confirmation("Купить §a$title\n§fза §b$price кристалик(а)") {
            Anime.close(player)
            Glow.animate(player, 0.4, GlowColor.GREEN)
            purchaseSuccess(user)
            Anime.killboardMessage(player, "${ChatColor.GREEN}Спасибо за покупку")
//            IInvoiceService.get().bill(
//                user.uuid,
//                Invoice.builder()
//                    .price(price)
//                    .description(description)
//                    .build()
//            ).thenAccept { bill ->
//                if (bill.errorMessage != null) {
//                    Anime.killboardMessage(player, Formatting.error(bill.errorMessage))
//                    Glow.animate(player, 0.4, GlowColor.RED)
//                    return@thenAccept
//                }
//            }
        }.open(player)
    }

    protected abstract fun purchaseSuccess(user: User)
}