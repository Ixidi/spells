package xyz.ixidi.spells.animation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import xyz.ixidi.spells.coroutines.SpigotCoroutines

interface Animation<D> {

    fun play(spigotCoroutines: SpigotCoroutines, data: D): Job

}

class AnimationRuntime<D>(
    val asyncDispatcher: CoroutineDispatcher,
    val syncDispatcher: CoroutineDispatcher,
    val data: D,
    scope: CoroutineScope
) : CoroutineScope by scope

fun <D> createAnimation(onPlay: suspend AnimationRuntime<D>.() -> Unit) = object : Animation<D> {

    override fun play(spigotCoroutines: SpigotCoroutines, data: D): Job {
        return spigotCoroutines.pluginScope.launch(spigotCoroutines.syncDispatcher) {
            AnimationRuntime(
                spigotCoroutines.asyncDispatcher,
                spigotCoroutines.syncDispatcher,
                data,
                this
            ).apply {
                onPlay()
            }
        }
    }

}