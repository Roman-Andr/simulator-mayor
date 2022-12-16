package me.slavita.construction.dontate

import me.slavita.construction.player.User

enum class Abilities(
    val applyAction: (user: User) -> Unit,
) {
    FLY({ user ->
        user.player.allowFlight = true
    }),
    NO_LIMIT_TELEPORT({ user ->

    }),
    SHOP_TIPS({ user ->

    }),
    CREDIT_PERCENT({ user ->

    }),
    CREDITS_LIMIT({ user ->

    })
}