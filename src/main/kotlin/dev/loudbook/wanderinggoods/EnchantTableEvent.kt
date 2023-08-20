package dev.loudbook.wanderinggoods

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class EnchantTableEvent : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.clickedBlock?.type == Material.ENCHANTING_TABLE) {
            event.isCancelled = true
        }
    }
}