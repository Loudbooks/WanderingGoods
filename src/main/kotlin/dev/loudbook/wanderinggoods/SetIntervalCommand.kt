package dev.loudbook.wanderinggoods

import net.md_5.bungee.api.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class SetIntervalCommand(private val worldTimer: WorldTimer, private val fileManager: FileManager) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val days = args[0].toDoubleOrNull()
        if (args.isEmpty() || days == null) {
            sender.sendMessage("${ChatColor.RED}Usage: /setinterval <days>")
            return true
        }

        if (!sender.isOp) {
            sender.sendMessage("${ChatColor.RED}You must be an operator to use this command.")
            return true
        }

        sender.sendMessage("${ChatColor.GREEN}Set interval to $days days.")

        worldTimer.daysInterval = days

        fileManager.overwriteKey("interval", days.toString())

        return true
    }
}