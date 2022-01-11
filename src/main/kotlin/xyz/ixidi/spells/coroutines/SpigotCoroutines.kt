package xyz.ixidi.spells.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.bukkit.plugin.Plugin

class SpigotCoroutines internal constructor(
    private val plugin: Plugin
) {

    val asyncDispatcher: CoroutineDispatcher by lazy { SpigotAsyncCoroutineDispatcher(plugin) }
    val syncDispatcher: CoroutineDispatcher by lazy { SpigotSyncCoroutineDispatcher(plugin) }

    val pluginScope by lazy { CoroutineScope(syncDispatcher) }

}