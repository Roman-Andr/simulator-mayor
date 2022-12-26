package me.slavita.construction.action.command.menu.worker

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.UpgradeWorker
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.user
import me.slavita.construction.worker.Worker
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class WorkerUpgradeMenu(player: Player, val worker: Worker) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            var infoButton: ReactiveButton
            return choicer {
                title = "${GOLD}${BOLD}Улучшение рабочего"
                description = ""
                info = """
                    ${GOLD}${BOLD}Характеристики:
                      ${YELLOW}Имя ${GRAY}»
                        ${WHITE}Наименование рабочего
                        ${WHITE}в вашей команде
                      
                      ${DARK_GREEN}Редкость ${GRAY}»
                        ${WHITE}Показывает на сколько
                        ${WHITE}характеристики рабочего хороши
                      
                      ${GOLD}Уровень ${GRAY}»
                        ${WHITE}Показывает уровень прокачки
                        ${WHITE}рабочего и влиет
                        ${WHITE}на все его характеристики
                      
                      ${AQUA}Скорость ${GRAY}»
                        ${WHITE}Количество блоков,
                        ${WHITE}которые ставит рабочий
                        ${WHITE}за секунду
                      
                      ${GREEN}Надёжность ${GRAY}»
                        ${WHITE}Влияет на то, как часто
                        ${WHITE}будет ломаться здания,
                        ${WHITE}построенные этим рабочим
                      
                      ${RED}Жадность ${GRAY}»
                        ${WHITE}Влияет на награду
                        ${WHITE}за окончания постройки
                        ${WHITE}здания этим рабочим
                """.trimIndent()
                storage = mutableListOf(
                    button {
                        item = ItemIcons.get("other", "info1")
                        title = "Информация"
                        hover = worker.toString()
                        hint = ""
                    }.apply { infoButton = this },
                    button {
                        item = ItemIcons.get("other", "add")
                        title = "Улучшить"
                        hover = getUpgradeHover()
                        hint = "Улучшить"
                        backgroundColor = GlowColor.GREEN
                        onClick { _, _, button ->
                            UpgradeWorker(player, worker).tryExecute()
                            button.hover = getUpgradeHover()
                            infoButton.hover = worker.toString()
                        }
                    },
                    button {
                        item = ItemIcons.get("other", "reload")
                        title = "Продать"
                        hover = getSellHover()
                        hint = "Продать"
                        backgroundColor = GlowColor.RED
                        onClick { _, _, button ->
                            WorkerSellConfirm(player, worker).tryExecute()
                            button.hover = getSellHover()
                            infoButton.hover = worker.toString()
                        }
                    }
                )
            }
        }
    }

    private fun getUpgradeHover(): String {
        return "${AQUA}Улучшить ${GREEN}${worker.level}${AQUA}->${GREEN}${worker.level + 1} ${AQUA}за ${worker.upgradePrice.toMoneyIcon()}"
    }

    private fun getSellHover(): String {
        return "${AQUA}Продать за ${GREEN}${worker.sellPrice.toMoneyIcon()}"
    }
}