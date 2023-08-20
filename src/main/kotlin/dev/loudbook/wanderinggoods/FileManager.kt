package dev.loudbook.wanderinggoods

import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileManager(private val instance: WanderingGoods) {
    fun saveToFile(map: MutableMap<String, String>) {
        instance.dataFolder.mkdirs()
        val file = instance.dataFolder.resolve("cache.db")
        val objectOutputStream = ObjectOutputStream(file.outputStream())
        objectOutputStream.writeObject(map)
        objectOutputStream.close()
    }

    fun loadFromFile(): MutableMap<String, String> {
        val file = instance.dataFolder.resolve("cache.db")
        val objectInputStream = ObjectInputStream(file.inputStream())
        return objectInputStream.readObject() as MutableMap<String, String>
    }

    fun overwriteKey(key: String, value: String) {
        val cacheMap = loadFromFile()
        cacheMap[key] = value
        saveToFile(cacheMap)
    }
}