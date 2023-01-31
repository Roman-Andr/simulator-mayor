package me.slavita.construction.structure

import com.google.gson.*
import me.slavita.construction.app
import me.slavita.construction.city.project.FreelanceProject
import me.slavita.construction.city.project.Project
import me.slavita.construction.player.sound.MusicSound
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.structure.tools.StructureVisual
import me.slavita.construction.utils.playSound
import me.slavita.construction.utils.runAsync
import me.slavita.construction.utils.toUUID
import me.slavita.construction.world.AmountItemProperties
import me.slavita.construction.world.StructureBlock
import java.lang.reflect.Type

abstract class BuildingStructure(
    val structure: Structure,
    val cell: CityCell,
) {
    protected var currentBlock: StructureBlock? = null
    protected var hidden = false
    var currentProject: Project? = null
    val owner = cell.owner
    var cityStructure: CityStructure? = null
    var state = StructureState.NOT_STARTED
    var blocksPlaced = 0
    val box = cell.box
    val allocation = box.min
    val visual = StructureVisual(this)

    protected abstract fun enterBuilding()

    protected abstract fun blockPlaced()

    protected abstract fun onFinish()

    protected abstract fun onShow()

    protected abstract fun onHide()

    abstract fun getBannerInfo(): List<Pair<String, Double>>

    fun showVisual() {
        hidden = false
        visual.show()
        onShow()
    }

    fun hideVisual() {
        hidden = true
        visual.hide()
        onHide()
    }

    fun deleteVisual() {
        hidden = true
        visual.delete()
        onHide()
    }

    fun startBuilding(project: Project) {
        state = StructureState.BUILDING
        currentBlock = structure.getFirstBlock()
        visual.start()
        currentProject = project

        enterBuilding()
        owner.updatePosition()
    }

    open fun continueBuilding() {
        state = StructureState.BUILDING
        currentBlock = structure.getFirstBlock()

        repeat(blocksPlaced) {
            app.mainWorld.placeFakeBlock(owner.player, currentBlock!!.withOffset(allocation))
            currentBlock = structure.getNextBlock(currentBlock!!.position)
        }

        visual.update()
        owner.updatePosition()
    }

    fun placeCurrentBlock() {
        if (state != StructureState.BUILDING) return
        owner.player.playSound(MusicSound.HINT)

        app.mainWorld.placeFakeBlock(
            owner.player,
            currentBlock!!.withOffset(allocation),
            currentProject!! !is FreelanceProject
        )
        currentBlock = structure.getNextBlock(currentBlock!!.position)
        blockPlaced()

        blocksPlaced++
        visual.update()
        if (currentBlock == null) {
            finishBuilding()
            return
        }
    }

    fun finishBuilding() {
        state = StructureState.FINISHED
        deleteVisual()
        visual.finishShow()
        if (currentProject!! !is FreelanceProject) cityStructure =
            cell.city.addStructure(CityStructure(owner.player, structure, cell))
        onFinish()
        owner.updatePosition()
    }

    fun claimed() {
        visual.hideFinish()
        state = StructureState.REWARD_CLAIMED
    }
}

class BuildingStructureSerializer : JsonSerializer<BuildingStructure> {
    override fun serialize(
        buildingStructure: BuildingStructure,
        type: Type,
        context: JsonSerializationContext,
    ): JsonElement {
        val json = JsonObject()

        buildingStructure.run {
            json.addProperty(
                "type", when (buildingStructure) {
                    is ClientStructure -> "client"
                    is WorkerStructure -> "worker"
                    else               -> throw JsonParseException("Unknown structure type!")
                }
            )
            json.addProperty("structureId", structure.id)
            json.addProperty("cityCellId", cell.id)
            json.addProperty("blocksPlaced", blocksPlaced)
            json.add("state", context.serialize(state))

            if (type == WorkerStructure::class.java) {
                buildingStructure as WorkerStructure

                val workers = JsonArray()
                val storage = JsonArray()

                buildingStructure.workers.forEach { workers.add(it.uuid.toString()) }
                buildingStructure.blocksStorage.forEach {
                    storage.add(
                        context.serialize(
                            AmountItemProperties(
                                it.key,
                                it.value
                            )
                        )
                    )
                }

                json.add("workers", workers)
                json.add("storage", storage)
            }
        }

        return json
    }
}

class BuildingStructureDeserializer(val project: Project) : JsonDeserializer<BuildingStructure> {
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext) =
        json.asJsonObject.run {
            val structure = Structures.structures[get("structureId").asInt]
            val cityCell = project.city.cityCells[get("cityCellId").asInt]
            val blocksPlaced = get("blocksPlaced").asInt

            when (get("type").asString) {
                "worker" -> {
                    WorkerStructure(structure, cityCell).apply {
                        runAsync(2) {
                            get("workers").asJsonArray.forEach { id ->
                                val workerUuid = id.asString.toUUID()
                                workers.add(project.owner.data.workers.first { it.uuid == workerUuid })
                            }
                            get("storage").asJsonArray.forEach {
                                val item =
                                    context.deserialize<AmountItemProperties>(it, AmountItemProperties::class.java)
                                blocksStorage[item] = item.amount
                            }
                        }
                    }
                }

                "client" -> ClientStructure(structure, cityCell)
                else     -> throw JsonParseException("Unknown structure type!")
            }.apply {
                this.blocksPlaced = blocksPlaced
                currentProject = project

                when (StructureState.valueOf(get("state").asString)) {
                    StructureState.BUILDING -> {
                        runAsync(100) {
                            startBuilding(project)
                            visual.hide()
                        }
                    }

                    StructureState.FINISHED -> runAsync(100) {
                        visual.start()
                        finishBuilding()
                    }

                    else                    -> this@apply.state = StructureState.valueOf(get("state").asString)
                }
            }
        }
}
