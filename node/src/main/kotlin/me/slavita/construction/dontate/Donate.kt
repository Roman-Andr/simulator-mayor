package me.slavita.construction.dontate

import me.func.mod.Anime
import me.func.mod.ui.menu.confirmation.Confirmation
import me.slavita.construction.player.User
import me.slavita.construction.ui.HumanizableValues.CRI_MONEY
import me.slavita.construction.utils.PlayerExtensions.accept
import org.bukkit.ChatColor.*

abstract class Donate(var title: String, var description: String, val price: Int) {
    fun purchase(user: User) {
        val player = user.player

        Confirmation("${BOLD}Купить \n$title\n${WHITE}${BOLD}за ${AQUA}${BOLD}$price ${AQUA}${CRI_MONEY.get(price)}") {
            Anime.close(player)
            purchaseSuccess(user)
            user.player.accept("${GREEN}Спасибо за покупку")
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