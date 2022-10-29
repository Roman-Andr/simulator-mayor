package me.slavita.construction.utils.extensions

import me.func.protocol.data.color.RGB
import me.func.protocol.data.color.Tricolor
import me.func.protocol.data.rare.DropRare

object Extensions {
    fun DropRare.getColor(): RGB {
        return Tricolor(red, blue, green)
    }
}