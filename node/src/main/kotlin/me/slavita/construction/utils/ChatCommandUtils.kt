package me.slavita.construction.utils

import org.apache.logging.log4j.util.BiConsumer
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object ChatCommandUtils {
	fun opCommand(name: String, biConsumer: BiConsumer<Player, Array<out String>>) {
		Bukkit.getCommandMap().register("anime", object : Command(name) {
			override fun execute(sender: CommandSender, var2: String, agrs: Array<out String>): Boolean {
				if (sender is Player && sender.isOp) biConsumer.accept(sender, agrs)
				return true
			}
		})
	}
}