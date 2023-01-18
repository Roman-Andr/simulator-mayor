package me.slavita.construction.structure

import com.google.gson.*
import me.func.mod.util.after
import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.project.Project
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.structure.tools.StructureVisual
import me.slavita.construction.utils.log
import me.slavita.construction.utils.nextTick
import me.slavita.construction.utils.runAsync
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.GameWorld
import me.slavita.construction.world.StructureBlock
import java.lang.reflect.Type
import java.util.UUID

abstract class BuildingStructure(
    val structure: Structure,
    val playerCell: PlayerCell,
) {
    protected var currentBlock: StructureBlock? = null
    protected var hidden = false
    var currentProject: Project? = null
    val owner = playerCell.owner
    var cityStructure: CityStructure? = null
    var state = StructureState.NOT_STARTED
    var blocksPlaced = 0
    val box = playerCell.box
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

        enterBuilding()
        currentProject = project
        visual.start()
        owner.updatePosition()
    }

    fun continueBuilding(project: Project) {
        state = StructureState.BUILDING
        currentBlock = structure.getFirstBlock()

        repeat(blocksPlaced) {
            app.mainWorld.placeFakeBlock(owner.player, currentBlock!!.withOffset(allocation))
            currentBlock = structure.getNextBlock(currentBlock!!.position)
        }

        visual.update()
        currentProject = project
        owner.updatePosition()
    }

    fun placeCurrentBlock() {
        if (state != StructureState.BUILDING) return

        app.mainWorld.placeFakeBlock(owner.player, currentBlock!!.withOffset(allocation))
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
        cityStructure = playerCell.city.addStructure(CityStructure(owner.player, structure, playerCell))
        owner.updatePosition()
        onFinish()
    }

    fun claimed() {
        visual.hideFinish()
        state = StructureState.REWARD_CLAIMED
    }
}

class BuildingStructureSerializer : JsonSerializer<BuildingStructure> {
    override fun serialize(buildingStructure: BuildingStructure, type: Type, context: JsonSerializationContext): JsonElement {
        val json = JsonObject()

        buildingStructure.run {
            json.addProperty("type", when (buildingStructure) {
                is ClientStructure -> "client"
                is WorkerStructure -> "worker"
                else -> throw JsonParseException("Unknown structure type!")
            })
            json.addProperty("structureId", structure.id)
            json.addProperty("playerCellId", playerCell.id)
            json.addProperty("blocksPlaced", blocksPlaced)
            json.add("state", context.serialize(state))

            if (type == WorkerStructure::class.java) {
                buildingStructure as WorkerStructure

                val workers = JsonArray()
                buildingStructure.workers.forEach { workers.add(it.uuid.toString()) }
                json.add("workers", workers)
            }
        }

        return json
    }
}

class BuildingStructureDeserializer(val project: Project) : JsonDeserializer<BuildingStructure> {
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext) = json.asJsonObject.run {
        val structure = Structures.structures[get("structureId").asInt]
        val playerCell = project.city.playerCells[get("playerCellId").asInt]
        val blocksPlaced = get("blocksPlaced").asInt

        when (get("type").asString) {
            "Structure" -> {
                WorkerStructure(structure, playerCell).apply {
                    nextTick {
                        get("workers").asJsonArray.forEach { id ->
                            val workerUuid = UUID.fromString(id.asString)
                            workers.add(project.owner.data.workers.first { it.uuid == workerUuid })
                        }
                    }
                }
            }
            "client" -> ClientStructure(structure, playerCell)
            else -> throw JsonParseException("Unknown structure type!")
        }.apply {
            this@apply.currentProject = project
            this@apply.blocksPlaced = blocksPlaced
            visual.start()
            when (StructureState.valueOf(get("state").asString)) {
                StructureState.BUILDING -> runAsync(200) { continueBuilding(project) }
                StructureState.FINISHED -> nextTick { finishBuilding() }
                else -> this@apply.state = state
            }
        }
    }
}
