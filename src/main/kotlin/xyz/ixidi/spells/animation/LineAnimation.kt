package xyz.ixidi.spells.animation

import kotlinx.coroutines.delay
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector

data class LineAnimationData(
    val particle: Particle,
    val start: Location,
    val direction: Vector,
    val maxLength: Int
)

private val animation = createAnimation<LineAnimationData> {
    val current = data.start
    val direction = data.direction.normalize()

    var t = 2
    while (t <= data.maxLength) {
        val vec = direction.clone().multiply(t)
        val x = data.start.x + vec.x
        val y = data.start.y + vec.y
        val z = data.start.z + vec.z
        current.world?.spawnParticle(data.particle, x, y, z, 6, 0.05, 0.05, 0.05, 0.0, null, false)
        t += 2
        delay(50)
    }
}

val Animations.lineAnimation: Animation<LineAnimationData>
    get() = animation