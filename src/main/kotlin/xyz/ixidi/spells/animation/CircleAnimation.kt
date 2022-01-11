package xyz.ixidi.spells.animation

import kotlinx.coroutines.delay
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

data class CircleAnimationData(
    val particle: Particle,
    val start: Location,
    val direction: Vector,
    val maxLength: Int,
    val radius: Double
)

private val animation = createAnimation<CircleAnimationData> {
    val current = data.start
    val direction = data.direction.normalize()

    var t = 0.1
    while (t <= data.maxLength) {
        repeat(20) {
            var k = direction
            var i = Vector(0, 0, 1).crossProduct(k)
            var j = k.clone().crossProduct(i)

            k.normalize()
            i.normalize()
            j.normalize()

            var x = data.start.x + (cos(t) * i.x) + (sin(t) * j.x) + (t * k.x)
            var y = data.start.y + (cos(t) * i.y) + (sin(t) * j.y) + (t * k.y)
            var z = data.start.z + (cos(t) * i.z) + (sin(t) * j.z) + (t * k.z)
            current.world?.spawnParticle(data.particle, x, y, z, 1, 0.0, 0.0, 0.0, 0.0, null, false)


            k = direction
            i = Vector(0, 0, 1).crossProduct(k).multiply(-1)
            j = k.clone().crossProduct(i)

            k.normalize()
            i.normalize()
            j.normalize()

            x = data.start.x + (cos(t) * i.x) + (sin(t) * j.x) + (t * k.x)
            y = data.start.y + (cos(t) * i.y) + (sin(t) * j.y) + (t * k.y)
            z = data.start.z + (cos(t) * i.z) + (sin(t) * j.z) + (t * k.z)
            current.world?.spawnParticle(data.particle, x, y, z, 1, 0.0, 0.0, 0.0, 0.0, null, false)

            t += 0.1
        }
        delay(50)
    }
}

val Animations.circleAnimation: Animation<CircleAnimationData>
    get() = animation