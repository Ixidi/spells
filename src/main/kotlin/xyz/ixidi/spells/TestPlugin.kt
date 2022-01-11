package xyz.ixidi.spells

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import xyz.ixidi.spells.animation.Animations
import xyz.ixidi.spells.animation.CircleAnimationData
import xyz.ixidi.spells.animation.LineAnimationData
import xyz.ixidi.spells.animation.circleAnimation
import xyz.ixidi.spells.animation.lineAnimation
import xyz.ixidi.spells.coroutines.SpigotAsyncCoroutineDispatcher
import xyz.ixidi.spells.coroutines.SpigotCoroutines
import xyz.ixidi.spells.coroutines.SpigotSyncCoroutineDispatcher

class TestPlugin : JavaPlugin(), Listener {

    private val coroutines by lazy { SpigotCoroutines(this) }

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }

    @EventHandler
    fun onPlaced(e: PlayerInteractEvent) {
        val job = Animations.circleAnimation.play(coroutines, CircleAnimationData(Particle.FIREWORKS_SPARK, e.player.eyeLocation, e.player.eyeLocation.direction, 30, 1.0))
        val jobTwo = Animations.lineAnimation.play(coroutines, LineAnimationData(Particle.HEART, e.player.eyeLocation, e.player.eyeLocation.direction, 30))
        job.invokeOnCompletion {
            println("completed ${it?.stackTraceToString()}")
        }
        jobTwo.invokeOnCompletion {
            println("completed 2")
        }
    }

    fun log(s: Any?) {
        logger.info("$s")
    }

}