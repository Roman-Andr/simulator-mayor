package me.slavita.construction.dontate

import me.slavita.construction.player.User

enum class Abilities(
    val applyAction: (user: User) -> Unit,
) {
    FLY({ user ->
        user.player.allowFlight = true
    }),
    NO_LIMIT_TELEPORT({ _ ->
    }),
    SHOP_TIPS({ _ ->
    }),
    CREDIT_PERCENT({ _ ->
    }),
    CREDITS_LIMIT({ _ ->
    }),
    UNBREKABLE_STRUCTURES({ _ ->
    })
}
