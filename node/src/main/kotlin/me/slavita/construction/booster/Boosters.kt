package me.slavita.construction.booster

import me.func.stronghold.Stronghold
import me.func.stronghold.booster.BoosterGlobal
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

object Boosters {
    fun activateGlobal(player: Player, booster: BoosterType) {
        Stronghold.activateBoosters(
            BoosterGlobal.builder()
                .type(booster.label)
                .title(booster.title)
                .owner(player)
                .duration(30, TimeUnit.MINUTES)
                .multiplier(1.5)
                .maxStackable(4)
                .build()
        )
    }
}