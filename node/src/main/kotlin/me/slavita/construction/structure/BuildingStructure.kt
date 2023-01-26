package me.slavita.construction.structure

import com.google.gson.*
import me.slavita.construction.app
import me.slavita.construction.player.sound.MusicSound
import me.slavita.construction.project.FreelanceProject
import me.slavita.construction.project.Project
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.instance.Structures
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.structure.tools.StructureVisual
import me.slavita.construction.utils.*
import me.slavita.construction.world.StructureBlock
import java.lang.reflect.Type

abstract class BuildingStructure(
    val structure: Structure,
    val cell: PlayerCell,
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
            json.addProperty("playerCellId", cell.id)
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
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext) =
        json.asJsonObject.run {
            val structure = Structures.structures[get("structureId").asInt]
            val playerCell = project.city.playerCells[get("playerCellId").asInt]
            val blocksPlaced = get("blocksPlaced").asInt

            when (get("type").asString) {
                "worker" -> {
                    WorkerStructure(structure, playerCell).apply {
                        nextTick {
                            get("workers").asJsonArray.forEach { id ->
                                val workerUuid = id.asString.toUUID()
                                workers.add(project.owner.data.workers.first { it.uuid == workerUuid })
                            }
                        }
                    }
                }

                "client" -> ClientStructure(structure, playerCell)
                else     -> throw JsonParseException("Unknown structure type!")
            }.apply {
                this@apply.currentProject = project
                this@apply.blocksPlaced = blocksPlaced
                visual.start()
                when (StructureState.valueOf(get("state").asString)) {
                    StructureState.BUILDING -> runAsync(100) { continueBuilding(project) }
                    StructureState.FINISHED -> nextTick { finishBuilding() }
                    else                    -> this@apply.state = state
                }
            }
        }
}
