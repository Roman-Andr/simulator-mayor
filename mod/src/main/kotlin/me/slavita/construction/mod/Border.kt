package me.slavita.construction.mod

import me.func.protocol.data.color.RGB
import ru.cristalix.uiengine.utility.V3
import java.util.UUID

class Border(
    val uuid: UUID,
    var color: RGB,
    var alpha: Int,
    val width: Double,
    val height: Double,
    val location: V3,
) {
    val vertices = arrayListOf<ArrayList<V3>>()

    init {
        arrayOf(-1, 1).forEach { direction ->
            val offset = direction * width / 2.0

            arrayOf(0, 1).forEach { side ->
                val sideVertices = arrayListOf<V3>()
                arrayOf(
                    Pair(0, 0),
                    Pair(0, 1),
                    Pair(1, 0),
                    Pair(1, 1),
                ).forEach { (xz, y) ->
                    sideVertices.add(
                        V3(
                            direction * side * xz * width - offset,
                            y * height,
                            direction * (side xor 1) * xz * width - offset
                        )
                    )
                }
                vertices.add(sideVertices)
            }
        }
    }
}
