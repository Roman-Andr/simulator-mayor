package me.slavita.construction.player

import me.slavita.construction.worker.Worker

data class Data(
    var statistics: Statistics = Statistics(),
    var workers: HashSet<Worker> = hashSetOf(),
    var settings: SettingsData = SettingsData(),
    var tag: Tags = Tags.NONE,
    var ownTags: HashSet<Tags> = hashSetOf()
)
