package me.slavita.construction.player

import me.slavita.construction.worker.Worker

data class Data(
    var statistics: Statistics = Statistics(),
    var workers: HashSet<Worker> = hashSetOf(),
)
