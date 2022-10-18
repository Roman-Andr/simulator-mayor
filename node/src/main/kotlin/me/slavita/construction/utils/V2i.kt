package me.slavita.construction.utils

class V2i(val x: Int, val z: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as V2i

        if (x != other.x) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + z
        return result
    }
}