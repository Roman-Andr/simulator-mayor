package me.slavita.construction.player

import me.slavita.construction.dontate.ability.Ability
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.ItemProperties
import org.bukkit.inventory.ItemStack

class Statistics(
    var money: Long = 0,
    var level: Int = 1,
    var experience: Long = 0,
    var reputation: Double = 0.0,
    var totalProjects: Int = 0,
    var maxProjects: Int = 0,
    var speed: Float = 0.2F,
    var trainStep: Int = 0,
    var income: Long = 0,
    var cities: HashSet<City> = hashSetOf(),
    val blocks: HashMap<ItemProperties, ItemStack> = hashMapOf(),
    val abilities: HashSet<Ability> = hashSetOf(),
    var workers: HashSet<Worker> = hashSetOf(),
    var exitTime: Long = 0
)