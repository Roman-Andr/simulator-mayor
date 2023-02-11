<<<<<<<< HEAD:node/src/main/kotlin/me/slavita/construction/showcase/ShowcaseClientData.kt
package me.slavita.construction.showcase
========
package me.slavita.construction.city.showcase
>>>>>>>> test:node/src/main/kotlin/me/slavita/construction/city/showcase/ShowcaseClientData.kt

import ru.cristalix.core.math.V3

data class ShowcaseClientData(
    val id: Int,
    val title: String,
    val min: V3,
    val max: V3,
)
