package me.slavita.construction.structure.instance

import me.slavita.construction.prepare.IRegistrable

object Structures : IRegistrable {
    val structures = arrayListOf<Structure>()
    lateinit var structureGroups: Array<StructureGroup>

    override fun register() {
        structureGroups = arrayOf(
            StructureGroup("Группа 1", "1", 10),
            StructureGroup("Группа 2", "2", 10),
            StructureGroup("Группа 3", "3", 10),
            StructureGroup("Группа 4", "4", 10),
            StructureGroup("Группа 5", "5", 10),
            StructureGroup("Группа 6", "6", 10),
            StructureGroup("Группа 7", "7", 10),
            StructureGroup("Группа 8", "8", 10),
            StructureGroup("Группа 9", "9", 10),
            StructureGroup("Группа 10", "10", 10),
            StructureGroup("Группа 11", "11", 10),
        )
    }
}
