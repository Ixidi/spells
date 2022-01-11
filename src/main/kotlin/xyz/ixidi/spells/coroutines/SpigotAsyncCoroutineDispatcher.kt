package xyz.ixidi.spells.coroutines

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Delay
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Runnable
import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class, InternalCoroutinesApi::class)
class SpigotAsyncCoroutineDispatcher(
    private val plugin: Plugin
) : CoroutineDispatcher(), Delay {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (!plugin.isEnabled) return

        if (!plugin.server.isPrimaryThread) {
            block.run()
        } else {
            plugin.server.scheduler.runTaskAsynchronously(plugin, block)
        }
    }

    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        val runnable = Runnable {
            with(continuation) { resumeUndispatched(Unit) }
        }
        val task = plugin.server.scheduler.runTaskLaterAsynchronously(plugin, runnable, timeMillis / 50)
        continuation.invokeOnCancellation { task.cancel() }
    }

}