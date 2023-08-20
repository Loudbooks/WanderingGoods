package dev.loudbook.wanderinggoods

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.entity.WanderingTrader
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.MerchantRecipe
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import java.util.*


class WorldTimer(private val instance: WanderingGoods, var daysInterval: Double, private var currentUUID: UUID, private val fileManager: FileManager) {
    init {
        startTimer()
    }

    private fun startTimer() {
        val world = Bukkit.getWorld(Bukkit.getWorlds()[0].name)
        if (world == null) {
            Bukkit.getLogger().info("World is null.")
            return
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, {
            if ((world.fullTime % (daysInterval * 24000.0).toLong()).toInt() != 0) return@scheduleSyncRepeatingTask

            world.entities.filter { it.uniqueId == currentUUID }.forEach { it.remove() }

            val randomX = (0..1000).random() - 500
            val randomY = (0..1000).random() - 500

            val location = world.getHighestBlockAt(randomX, randomY).location
            location.y += 1

            val trader = world.spawnEntity(
                location,
                EntityType.WANDERING_TRADER
            ) as WanderingTrader

            trader.despawnDelay = -1
            trader.isPersistent = true

            val book = ItemStack(Material.ENCHANTED_BOOK)
            val enchantMeta = book.itemMeta as EnchantmentStorageMeta
            val randEnchant: Enchantment? = Enchantment.values()[(Math.random() * Enchantment.values().size).toInt()]

            enchantMeta.addStoredEnchant(randEnchant!!, (1..randEnchant.maxLevel).random(), true)
            book.itemMeta = enchantMeta

            val merchantRecipe = MerchantRecipe(book, 0, 1, false)

            merchantRecipe.addIngredient(ItemStack(Material.BOOK))
            merchantRecipe.addIngredient(ItemStack(Material.AMETHYST_SHARD, (1..5).random()))

            trader.recipes = listOf(merchantRecipe)

            this.currentUUID = trader.uniqueId

            fileManager.overwriteKey("uuid", currentUUID.toString())
        }, 1, 1)
    }
}