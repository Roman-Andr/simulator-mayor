package me.slavita.construction.city.project

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import me.slavita.construction.city.City
import me.slavita.construction.player.sound.MusicSound
import me.slavita.construction.reward.ExperienceReward
import me.slavita.construction.reward.MoneyReward
import me.slavita.construction.reward.ReputationReward
import me.slavita.construction.reward.Reward
import me.slavita.construction.structure.BuildingStructure
import me.slavita.construction.structure.BuildingStructureDeserializer
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.playSound
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.GREEN
import java.lang.reflect.Type

open class Project(
    val city: City,
    var id: Int,
    val rewards: HashSet<Reward>,
) {
    lateinit var structure: BuildingStructure
    val owner = city.owner
    val timeLast: Int // TODO: use this
        get() = 0

    fun initialize() {
        var projectsCount = 0
        owner.data.cities.forEach {
            projectsCount += it.projects.size
        }
        id = owner.data.totalProjects + projectsCount
    }

    fun start() {
        structure.startBuilding(this)
        owner.player.playSound(MusicSound.SUCCESS3)
    }

    open fun onEnter() {
        when (structure.state) {
            StructureState.BUILDING -> structure.showVisual()
            StructureState.FINISHED -> {
                finish()
                owner.data.addProjects(1)
                structure.cityStructure!!.state = CityStructureState.FUNCTIONING
                owner.updateIncome()
                city.finishProject(this@Project)
            }

            else -> {}
        }
    }

    fun finish() {
        structure.claimed()
        rewards.forEach { reward ->
            reward.getReward(owner)
        }
        owner.player.playSound(MusicSound.UI_CLICK)
        owner.data.totalProjects++
    }

    fun onLeave() {
        when (structure.state) {
            StructureState.BUILDING -> structure.hideVisual()
            else -> {}
        }
    }

    override fun toString() = """
        ${GOLD}Информация про проект:
          Номер: ${GRAY}${structure.cell.id}
          Название: ${GOLD}${structure.structure.name}
          Локация: ${GREEN}${structure.cell.city.title}
          Награды:
          ${rewards.sortedBy { it::class.java.name }.joinToString { it.toString() }}
    """.trimIndent()
}

class ProjectSerializer : JsonSerializer<Project> {
    override fun serialize(project: Project, type: Type, context: JsonSerializationContext): JsonElement {
        val json = JsonObject()

        project.run {
            json.addProperty("id", id)
            json.add("structure", context.serialize(structure))
            json.add("rewards", context.serialize(rewards))
        }

        return json
    }
}

class ProjectDeserializer(val city: City) : JsonDeserializer<Project> {
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext) =
        json.asJsonObject.run {
            val rewards = hashSetOf<Reward>()
            get("rewards").asJsonArray.forEach {
                rewards.add(
                    it.asJsonObject.run {
                        when {
                            has("experience") -> ExperienceReward(get("experience").asLong)
                            has("reputation") -> ReputationReward(get("reputation").asLong)
                            has("money") -> MoneyReward(get("money").asLong)
                            else -> throw JsonParseException("Unknown reward type")
                        }
                    }
                )
            }

            val project = Project(city, get("id").asInt, rewards)

            val gson = GsonBuilder()
                .registerTypeAdapter(BuildingStructure::class.java, BuildingStructureDeserializer(project))
                .create()

            project.structure = gson.fromJson(get("structure"), BuildingStructure::class.java)

            project
        }
}
