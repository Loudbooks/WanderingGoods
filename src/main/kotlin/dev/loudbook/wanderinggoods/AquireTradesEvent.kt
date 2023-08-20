package dev.loudbook.wanderinggoods

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.VillagerAcquireTradeEvent

class AquireTradesEvent : Listener {
    @EventHandler
    fun onVillagerAquireTrade(event: VillagerAcquireTradeEvent) {
        if (event.recipe.result.type == Material.ENCHANTED_BOOK) event.isCancelled = true
    }
}