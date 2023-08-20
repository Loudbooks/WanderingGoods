package dev.loudbook.wanderinggoods

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class WanderingGoods : JavaPlugin() {
    private val fileManager = FileManager(this)

    override fun onEnable() {
        val cacheMap = fileManager.loadFromFile()

        var days = cacheMap["interval"]
        var uuid = cacheMap["uuid"]

        if (days == null || uuid == null) {
            cacheMap["interval"] = "3.0"
            cacheMap["uuid"] = "00000000-0000-0000-0000-000000000000"
            days = "3.0"
            uuid = "00000000-0000-0000-0000-000000000000"

            Bukkit.getLogger().info("No cache found, creating new cache.")
            fileManager.saveToFile(cacheMap)
        }

        Bukkit.getPluginCommand("spawnInterval")!!.setExecutor(SetIntervalCommand(
            WorldTimer(this, days.toDouble(), UUID.fromString(uuid), fileManager),
            fileManager
        ))
        Bukkit.getPluginManager().registerEvents(AquireTradesEvent(), this)
        Bukkit.getPluginManager().registerEvents(EnchantTableEvent(), this)
    }
}