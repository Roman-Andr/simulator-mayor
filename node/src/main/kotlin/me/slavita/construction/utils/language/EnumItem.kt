package me.slavita.construction.utils.language

import me.slavita.construction.utils.language.LanguageHelper.translateToLocal
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.EnumSet

enum class EnumItem(val material: Material, val metadata: Int, val unlocalizedName: String) {
    AIR(Material.AIR, "tile.air.name"), BARRIER(Material.BARRIER, "tile.barrier.name"), STONE(
        Material.STONE,
        "tile.stone.stone.name"
    ),
    GRANITE(
        Material.STONE, 1, "tile.stone.granite.name"
    ),
    POLISHED_GRANITE(Material.STONE, 2, "tile.stone.graniteSmooth.name"), DIORITE(
        Material.STONE, 3, "tile.stone.diorite.name"
    ),
    POLISHED_DIORITE(Material.STONE, 4, "tile.stone.dioriteSmooth.name"), ANDESITE(
        Material.STONE, 5, "tile.stone.andesite.name"
    ),
    POLISHED_ANDESITE(Material.STONE, 6, "tile.stone.andesiteSmooth.name"), HAY_BALE(
        Material.HAY_BLOCK, "tile.hayBlock.name"
    ),
    GRASS_BLOCK(Material.GRASS, "tile.grass.name"), DIRT(Material.DIRT, 0, "tile.dirt.default.name"), COARSE_DIRT(
        Material.DIRT, 1, "tile.dirt.coarse.name"
    ),
    PODZOL(Material.DIRT, 2, "tile.dirt.podzol.name"), COBBLESTONE(
        Material.COBBLESTONE, "tile.stonebrick.name"
    ),
    OAK_WOOD_PLANKS(Material.WOOD, "tile.wood.oak.name"), SPRUCE_WOOD_PLANKS(
        Material.WOOD, 1, "tile.wood.spruce.name"
    ),
    BIRCH_WOOD_PLANKS(Material.WOOD, 2, "tile.wood.birch.name"), JUNGLE_WOOD_PLANKS(
        Material.WOOD, 3, "tile.wood.jungle.name"
    ),
    ACACIA_WOOD_PLANKS(Material.WOOD, 4, "tile.wood.acacia.name"), DARK_OAK_WOOD_PLANKS(
        Material.WOOD, 5, "tile.wood.big_oak.name"
    ),
    OAK_SAPLING(Material.SAPLING, "tile.sapling.oak.name"), SPRUCE_SAPLING(
        Material.SAPLING, 1, "tile.sapling.spruce.name"
    ),
    BIRCH_SAPLING(Material.SAPLING, 2, "tile.sapling.birch.name"), JUNGLE_SAPLING(
        Material.SAPLING, 3, "tile.sapling.jungle.name"
    ),
    ACACIA_SAPLING(Material.SAPLING, 4, "tile.sapling.acacia.name"), DARK_OAK_SAPLING(
        Material.SAPLING, 5, "tile.sapling.big_oak.name"
    ),
    DEAD_BUSH(Material.DEAD_BUSH, "tile.deadbush.name"), BEDROCK(
        Material.BEDROCK, "tile.bedrock.name"
    ),
    WATER(Material.WATER, "tile.water.name"), LAVA(Material.LAVA, "tile.lava.name"), SAND(
        Material.SAND, 0, "tile.sand.default.name"
    ),
    RED_SAND(Material.SAND, 1, "tile.sand.red.name"), SANDSTONE(
        Material.SANDSTONE, 0, "tile.sandStone.default.name"
    ),
    CHISELED_SANDSTONE(Material.SANDSTONE, 1, "tile.sandStone.chiseled.name"), SMOOTH_SANDSTONE(
        Material.SANDSTONE, 2, "tile.sandStone.smooth.name"
    ),
    RED_SANDSTONE(Material.RED_SANDSTONE, 0, "tile.redSandStone.default.name"), CHISELED_RED_SANDSTONE(
        Material.RED_SANDSTONE, 1, "tile.redSandStone.chiseled.name"
    ),
    SMOOTH_RED_SANDSTONE(Material.RED_SANDSTONE, 2, "tile.redSandStone.smooth.name"), GRAVEL(
        Material.GRAVEL, "tile.gravel.name"
    ),
    GOLD_ORE(Material.GOLD_ORE, "tile.oreGold.name"), IRON_ORE(Material.IRON_ORE, "tile.oreIron.name"), COAL_ORE(
        Material.COAL_ORE, "tile.oreCoal.name"
    ),
    OAK_WOOD(Material.LOG, "tile.log.oak.name"), SPRUCE_WOOD(Material.LOG, 1, "tile.log.spruce.name"), BIRCH_WOOD(
        Material.LOG, 2, "tile.log.birch.name"
    ),
    JUNGLE_WOOD(Material.LOG, 3, "tile.log.jungle.name"), ACACIA_WOOD(
        Material.LOG_2, "tile.log.acacia.name"
    ),
    DARK_OAK_WOOD(Material.LOG_2, 1, "tile.log.big_oak.name"), OAK_LEAVES(
        Material.LEAVES, "tile.leaves.oak.name"
    ),
    SPRUCE_LEAVES(Material.LEAVES, 1, "tile.leaves.spruce.name"), BIRCH_LEAVES(
        Material.LEAVES, 2, "tile.leaves.birch.name"
    ),
    JUNGLE_LEAVES(Material.LEAVES, 3, "tile.leaves.jungle.name"), ACACIA_LEAVES(
        Material.LEAVES_2, "tile.leaves.acacia.name"
    ),
    DARK_OAK_LEAVES(Material.LEAVES_2, 1, "tile.leaves.big_oak.name"), SHRUB(
        Material.LONG_GRASS, 0, "tile.tallgrass.shrub.name"
    ),
    GRASS(Material.LONG_GRASS, 1, "tile.tallgrass.grass.name"), FERN(
        Material.LONG_GRASS, 2, "tile.tallgrass.fern.name"
    ),
    SPONGE(Material.SPONGE, "tile.sponge.dry.name"), WET_SPONGE(
        Material.SPONGE, 1, "tile.sponge.wet.name"
    ),
    GLASS(Material.GLASS, "tile.glass.name"), BLACK_STAINED_GLASS(
        Material.STAINED_GLASS, 15, "tile.stainedGlass.black.name"
    ),
    RED_STAINED_GLASS(Material.STAINED_GLASS, 14, "tile.stainedGlass.red.name"), GREEN_STAINED_GLASS(
        Material.STAINED_GLASS, 13, "tile.stainedGlass.green.name"
    ),
    BROWN_STAINED_GLASS(Material.STAINED_GLASS, 12, "tile.stainedGlass.brown.name"), BLUE_STAINED_GLASS(
        Material.STAINED_GLASS, 11, "tile.stainedGlass.blue.name"
    ),
    PURPLE_STAINED_GLASS(Material.STAINED_GLASS, 10, "tile.stainedGlass.purple.name"), CYAN_STAINED_GLASS(
        Material.STAINED_GLASS, 9, "tile.stainedGlass.cyan.name"
    ),
    LIGHT_GRAY_STAINED_GLASS(Material.STAINED_GLASS, 8, "tile.stainedGlass.silver.name"), GRAY_STAINED_GLASS(
        Material.STAINED_GLASS, 7, "tile.stainedGlass.gray.name"
    ),
    PINK_STAINED_GLASS(Material.STAINED_GLASS, 6, "tile.stainedGlass.pink.name"), LIME_STAINED_GLASS(
        Material.STAINED_GLASS, 5, "tile.stainedGlass.lime.name"
    ),
    YELLOW_STAINED_GLASS(Material.STAINED_GLASS, 4, "tile.stainedGlass.yellow.name"), LIGHT_BLUE_STAINED_GLASS(
        Material.STAINED_GLASS, 3, "tile.stainedGlass.lightBlue.name"
    ),
    MAGENTA_STAINED_GLASS(Material.STAINED_GLASS, 2, "tile.stainedGlass.magenta.name"), ORANGE_STAINED_GLASS(
        Material.STAINED_GLASS, 1, "tile.stainedGlass.orange.name"
    ),
    WHITE_STAINED_GLASS(Material.STAINED_GLASS, "tile.stainedGlass.white.name"), BLACK_STAINED_GLASS_PANE(
        Material.STAINED_GLASS_PANE, 15, "tile.thinStainedGlass.black.name"
    ),
    RED_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 14, "tile.thinStainedGlass.red.name"), GREEN_STAINED_GLASS_PANE(
        Material.STAINED_GLASS_PANE, 13, "tile.thinStainedGlass.green.name"
    ),
    BROWN_STAINED_GLASS_PANE(
        Material.STAINED_GLASS_PANE,
        12,
        "tile.thinStainedGlass.brown.name"
    ),
    BLUE_STAINED_GLASS_PANE(
        Material.STAINED_GLASS_PANE, 11, "tile.thinStainedGlass.blue.name"
    ),
    PURPLE_STAINED_GLASS_PANE(
        Material.STAINED_GLASS_PANE,
        10,
        "tile.thinStainedGlass.purple.name"
    ),
    CYAN_STAINED_GLASS_PANE(
        Material.STAINED_GLASS_PANE, 9, "tile.thinStainedGlass.cyan.name"
    ),
    LIGHT_GRAY_STAINED_GLASS_PANE(
        Material.STAINED_GLASS_PANE,
        8,
        "tile.thinStainedGlass.silver.name"
    ),
    GRAY_STAINED_GLASS_PANE(
        Material.STAINED_GLASS_PANE, 7, "tile.thinStainedGlass.gray.name"
    ),
    PINK_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 6, "tile.thinStainedGlass.pink.name"), LIME_STAINED_GLASS_PANE(
        Material.STAINED_GLASS_PANE, 5, "tile.thinStainedGlass.lime.name"
    ),
    YELLOW_STAINED_GLASS_PANE(
        Material.STAINED_GLASS_PANE,
        4,
        "tile.thinStainedGlass.yellow.name"
    ),
    LIGHT_BLUE_STAINED_GLASS_PANE(
        Material.STAINED_GLASS_PANE, 3, "tile.thinStainedGlass.lightBlue.name"
    ),
    MAGENTA_STAINED_GLASS_PANE(
        Material.STAINED_GLASS_PANE,
        2,
        "tile.thinStainedGlass.magenta.name"
    ),
    ORANGE_STAINED_GLASS_PANE(
        Material.STAINED_GLASS_PANE, 1, "tile.thinStainedGlass.orange.name"
    ),
    WHITE_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, "tile.thinStainedGlass.white.name"), GLASS_PANE(
        Material.THIN_GLASS, "tile.thinGlass.name"
    ),
    DANDELION(Material.YELLOW_FLOWER, "tile.flower1.dandelion.name"), POPPY(
        Material.RED_ROSE, "tile.flower2.poppy.name"
    ),
    BLUE_ORCHID(Material.RED_ROSE, 1, "tile.flower2.blueOrchid.name"), ALLIUM(
        Material.RED_ROSE, 2, "tile.flower2.allium.name"
    ),
    AZURE_BLUET(Material.RED_ROSE, 3, "tile.flower2.houstonia.name"), RED_TULIP(
        Material.RED_ROSE, 4, "tile.flower2.tulipRed.name"
    ),
    ORANGE_TULIP(Material.RED_ROSE, 5, "tile.flower2.tulipOrange.name"), WHITE_TULIP(
        Material.RED_ROSE, 6, "tile.flower2.tulipWhite.name"
    ),
    PINK_TULIP(Material.RED_ROSE, 7, "tile.flower2.tulipPink.name"), OXEYE_DAISY(
        Material.RED_ROSE, 8, "tile.flower2.oxeyeDaisy.name"
    ),
    SUNFLOWER(Material.DOUBLE_PLANT, "tile.doublePlant.sunflower.name"), LILAC(
        Material.DOUBLE_PLANT, 1, "tile.doublePlant.syringa.name"
    ),
    DOUBLE_TALLGRASS(Material.DOUBLE_PLANT, 2, "tile.doublePlant.grass.name"), LARGE_FERN(
        Material.DOUBLE_PLANT, 3, "tile.doublePlant.fern.name"
    ),
    ROSE_BUSH(Material.DOUBLE_PLANT, 4, "tile.doublePlant.rose.name"), PEONY(
        Material.DOUBLE_PLANT, 5, "tile.doublePlant.paeonia.name"
    ),
    BROWN_MUSHROOM(Material.BROWN_MUSHROOM, "tile.mushroom.name"), RED_MUSHROOM(
        Material.RED_MUSHROOM, "tile.mushroom.name"
    ),
    HUGE_MUSHROOM1(Material.HUGE_MUSHROOM_1, "tile.mushroom.name"), HUGE_MUSHROOM2(
        Material.HUGE_MUSHROOM_2, "tile.mushroom.name"
    ),
    BLOCK_OF_GOLD(Material.GOLD_BLOCK, "tile.blockGold.name"), BLOCK_OF_IRON(
        Material.IRON_BLOCK, "tile.blockIron.name"
    ),
    STONE_SLAB(Material.STEP, "tile.stoneSlab.stone.name"), SANDSTONE_SLAB(
        Material.STEP, 1, "tile.stoneSlab.sand.name"
    ),
    WOODEN_SLAB(Material.STEP, 2, "tile.stoneSlab.wood.name"), COBBLESTONE_SLAB(
        Material.STEP, 3, "tile.stoneSlab.cobble.name"
    ),
    BRICKS_SLAB(Material.STEP, 4, "tile.stoneSlab.brick.name"), STONE_BRICKS_SLAB(
        Material.STEP, 5, "tile.stoneSlab.smoothStoneBrick.name"
    ),
    NETHER_BRICK_SLAB(Material.STEP, 6, "tile.stoneSlab.netherBrick.name"), QUARTZ_SLAB(
        Material.STEP, 7, "tile.stoneSlab.quartz.name"
    ),
    RED_SANDSTONE_SLAB(Material.STONE_SLAB2, "tile.stoneSlab2.red_sandstone.name"), OAK_WOOD_SLAB(
        Material.WOOD_STEP, "tile.woodSlab.oak.name"
    ),
    SPRUCE_WOOD_SLAB(Material.WOOD_STEP, 1, "tile.woodSlab.spruce.name"), BIRCH_WOOD_SLAB(
        Material.WOOD_STEP, 2, "tile.woodSlab.birch.name"
    ),
    JUNGLE_WOOD_SLAB(Material.WOOD_STEP, 3, "tile.woodSlab.jungle.name"), ACACIA_WOOD_SLAB(
        Material.WOOD_STEP, 4, "tile.woodSlab.acacia.name"
    ),
    DARK_OAK_WOOD_SLAB(Material.WOOD_STEP, 5, "tile.woodSlab.big_oak.name"), BRICKS(
        Material.BRICK, "tile.brick.name"
    ),
    TNT(Material.TNT, "tile.tnt.name"), BOOKSHELF(Material.BOOKSHELF, "tile.bookshelf.name"), MOSS_STONE(
        Material.MOSSY_COBBLESTONE, "tile.stoneMoss.name"
    ),
    OBSIDIAN(Material.OBSIDIAN, "tile.obsidian.name"), TORCH(
        Material.TORCH, "tile.torch.name"
    ),
    FIRE(Material.FIRE, "tile.fire.name"), MONSTER_SPAWNER(
        Material.MOB_SPAWNER,
        "tile.mobSpawner.name"
    ),
    OAK_WOOD_STAIRS(
        Material.WOOD_STAIRS, "tile.stairsWood.name"
    ),
    SPRUCE_WOOD_STAIRS(Material.SPRUCE_WOOD_STAIRS, "tile.stairsWoodSpruce.name"), BIRCH_WOOD_STAIRS(
        Material.BIRCH_WOOD_STAIRS, "tile.stairsWoodBirch.name"
    ),
    JUNGLE_WOOD_STAIRS(Material.JUNGLE_WOOD_STAIRS, "tile.stairsWoodJungle.name"), ACACIA_WOOD_STAIRS(
        Material.ACACIA_STAIRS, "tile.stairsWoodAcacia.name"
    ),
    DARK_OAK_WOOD_STAIRS(Material.DARK_OAK_STAIRS, "tile.stairsWoodDarkOak.name"), CHEST(
        Material.CHEST, "tile.chest.name"
    ),
    TRAPPED_CHEST(Material.TRAPPED_CHEST, "tile.chestTrap.name"), REDSTONE_DUST(
        Material.REDSTONE_WIRE, "tile.redstoneDust.name"
    ),
    DIAMOND_ORE(Material.DIAMOND_ORE, "tile.oreDiamond.name"), BLOCK_OF_COAL(
        Material.COAL_BLOCK, "tile.blockCoal.name"
    ),
    BLOCK_OF_DIAMOND(Material.DIAMOND_BLOCK, "tile.blockDiamond.name"), CRAFTING_TABLE(
        Material.WORKBENCH, "tile.workbench.name"
    ),
    CROPS(Material.CROPS, "tile.crops.name"), FURNACE(Material.FURNACE, "tile.furnace.name"), BURN_FURNACE(
        Material.BURNING_FURNACE, "tile.furnace.name"
    ),
    SIGN(Material.SIGN, "tile.sign.name"), SIGN_POST(Material.SIGN_POST, "tile.sign.name"), SIGN_WALL(
        Material.WALL_SIGN, "tile.sign.name"
    ),
    LADDER(Material.LADDER, "tile.ladder.name"), RAIL(Material.RAILS, "tile.rail.name"), POWERED_RAIL(
        Material.POWERED_RAIL, "tile.goldenRail.name"
    ),
    ACTIVATOR_RAIL(Material.ACTIVATOR_RAIL, "tile.activatorRail.name"), DETECTOR_RAIL(
        Material.DETECTOR_RAIL, "tile.detectorRail.name"
    ),
    COBBLESTONE_STAIRS(Material.COBBLESTONE_STAIRS, "tile.stairsStone.name"), SANDSTONE_STAIRS(
        Material.SANDSTONE_STAIRS, "tile.stairsSandStone.name"
    ),
    RED_SANDSTONE_STAIRS(Material.RED_SANDSTONE_STAIRS, "tile.stairsRedSandStone.name"), LEVER(
        Material.LEVER, "tile.lever.name"
    ),
    STONE_PRESSURE_PLATE(Material.STONE_PLATE, "tile.pressurePlateStone.name"), WOODEN_PRESSURE_PLATE(
        Material.WOOD_PLATE, "tile.pressurePlateWood.name"
    ),
    WEIGHTED_PRESSURE_PLATE_LIGHT(Material.GOLD_PLATE, "tile.weightedPlate_light.name"), WEIGHTED_PRESSURE_PLATE_HEAVY(
        Material.IRON_PLATE, "tile.weightedPlate_heavy.name"
    ),
    IRON_DOOR_BLOCK(Material.IRON_DOOR_BLOCK, "tile.doorIron.name"), REDSTONE_ORE(
        Material.REDSTONE_ORE, "tile.oreRedstone.name"
    ),
    REDSTONE_TORCH_OFF(Material.REDSTONE_TORCH_OFF, "tile.notGate.name"), REDSTONE_TORCH_ON(
        Material.REDSTONE_TORCH_ON, "tile.notGate.name"
    ),
    WOOD_BUTTON(Material.WOOD_BUTTON, "tile.button.name"), STONE_BUTTON(
        Material.STONE_BUTTON, "tile.button.name"
    ),
    SNOW(Material.SNOW, "tile.snow.name"), BLACK_CARPET(Material.CARPET, 15, "tile.woolCarpet.black.name"), RED_CARPET(
        Material.CARPET, 14, "tile.woolCarpet.red.name"
    ),
    GREEN_CARPET(Material.CARPET, 13, "tile.woolCarpet.green.name"), BROWN_CARPET(
        Material.CARPET, 12, "tile.woolCarpet.brown.name"
    ),
    BLUE_CARPET(Material.CARPET, 11, "tile.woolCarpet.blue.name"), PURPLE_CARPET(
        Material.CARPET, 10, "tile.woolCarpet.purple.name"
    ),
    CYAN_CARPET(Material.CARPET, 9, "tile.woolCarpet.cyan.name"), LIGHT_GRAY_CARPET(
        Material.CARPET, 8, "tile.woolCarpet.silver.name"
    ),
    GRAY_CARPET(Material.CARPET, 7, "tile.woolCarpet.gray.name"), PINK_CARPET(
        Material.CARPET, 6, "tile.woolCarpet.pink.name"
    ),
    LIME_CARPET(Material.CARPET, 5, "tile.woolCarpet.lime.name"), YELLOW_CARPET(
        Material.CARPET, 4, "tile.woolCarpet.yellow.name"
    ),
    LIGHT_BLUE_CARPET(Material.CARPET, 3, "tile.woolCarpet.lightBlue.name"), MAGENTA_CARPET(
        Material.CARPET, 2, "tile.woolCarpet.magenta.name"
    ),
    ORANGE_CARPET(Material.CARPET, 1, "tile.woolCarpet.orange.name"), CARPET(
        Material.CARPET, "tile.woolCarpet.white.name"
    ),
    ICE(Material.ICE, "tile.ice.name"), FROSTED_ICE(Material.FROSTED_ICE, "tile.frostedIce.name"), PACKED_ICE(
        Material.PACKED_ICE, "tile.icePacked.name"
    ),
    CACTUS(Material.CACTUS, "tile.cactus.name"), CLAY(Material.CLAY, "tile.clay.name"), BLACK_STAINED_CLAY(
        Material.STAINED_CLAY, 15, "tile.clayHardenedStained.black.name"
    ),
    RED_STAINED_CLAY(Material.STAINED_CLAY, 14, "tile.clayHardenedStained.red.name"), GREEN_STAINED_CLAY(
        Material.STAINED_CLAY, 13, "tile.clayHardenedStained.green.name"
    ),
    BROWN_STAINED_CLAY(Material.STAINED_CLAY, 12, "tile.clayHardenedStained.brown.name"), BLUE_STAINED_CLAY(
        Material.STAINED_CLAY, 11, "tile.clayHardenedStained.blue.name"
    ),
    PURPLE_STAINED_CLAY(Material.STAINED_CLAY, 10, "tile.clayHardenedStained.purple.name"), CYAN_STAINED_CLAY(
        Material.STAINED_CLAY, 9, "tile.clayHardenedStained.cyan.name"
    ),
    LIGHT_GRAY_STAINED_CLAY(Material.STAINED_CLAY, 8, "tile.clayHardenedStained.silver.name"), GRAY_STAINED_CLAY(
        Material.STAINED_CLAY, 7, "tile.clayHardenedStained.gray.name"
    ),
    PINK_STAINED_CLAY(Material.STAINED_CLAY, 6, "tile.clayHardenedStained.pink.name"), LIME_STAINED_CLAY(
        Material.STAINED_CLAY, 5, "tile.clayHardenedStained.lime.name"
    ),
    YELLOW_STAINED_CLAY(Material.STAINED_CLAY, 4, "tile.clayHardenedStained.yellow.name"), LIGHT_BLUE_STAINED_CLAY(
        Material.STAINED_CLAY, 3, "tile.clayHardenedStained.lightBlue.name"
    ),
    MAGENTA_STAINED_CLAY(Material.STAINED_CLAY, 2, "tile.clayHardenedStained.magenta.name"), ORANGE_STAINED_CLAY(
        Material.STAINED_CLAY, 1, "tile.clayHardenedStained.orange.name"
    ),
    WHITE_STAINED_CLAY(Material.STAINED_CLAY, "tile.clayHardenedStained.white.name"), HARDENED_CLAY(
        Material.HARD_CLAY, "tile.clayHardened.name"
    ),
    SUGAR_CANE(Material.SUGAR_CANE_BLOCK, "tile.reeds.name"), JUKEBOX(
        Material.JUKEBOX, "tile.jukebox.name"
    ),
    OAK_FENCE(Material.FENCE, "tile.fence.name"), SPRUCE_FENCE(
        Material.SPRUCE_FENCE,
        "tile.spruceFence.name"
    ),
    BIRCH_FENCE(
        Material.BIRCH_FENCE, "tile.birchFence.name"
    ),
    JUNGLE_FENCE(Material.JUNGLE_FENCE, "tile.jungleFence.name"), DARK_OAK_FENCE(
        Material.DARK_OAK_FENCE, "tile.darkOakFence.name"
    ),
    ACACIA_FENCE(Material.ACACIA_FENCE, "tile.acaciaFence.name"), OAK_FENCE_GATE(
        Material.FENCE_GATE, "tile.fenceGate.name"
    ),
    SPRUCE_FENCE_GATE(Material.SPRUCE_FENCE_GATE, "tile.spruceFenceGate.name"), BIRCH_FENCE_GATE(
        Material.BIRCH_FENCE_GATE, "tile.birchFenceGate.name"
    ),
    JUNGLE_FENCE_GATE(Material.JUNGLE_FENCE_GATE, "tile.jungleFenceGate.name"), DARK_OAK_FENCE_GATE(
        Material.DARK_OAK_FENCE_GATE, "tile.darkOakFenceGate.name"
    ),
    ACACIA_FENCE_GATE(Material.ACACIA_FENCE_GATE, "tile.acaciaFenceGate.name"), PUMPKIN_STEM(
        Material.PUMPKIN_STEM, "tile.pumpkinStem.name"
    ),
    PUMPKIN(Material.PUMPKIN, "tile.pumpkin.name"), JACK_O_LANTERN(
        Material.JACK_O_LANTERN, "tile.litpumpkin.name"
    ),
    NETHERRACK(Material.NETHERRACK, "tile.hellrock.name"), SOUL_SAND(
        Material.SOUL_SAND, "tile.hellsand.name"
    ),
    GLOWSTONE(Material.GLOWSTONE, "tile.lightgem.name"), PORTAL(Material.PORTAL, "tile.portal.name"), BLACK_WOOL(
        Material.WOOL, 15, "tile.cloth.black.name"
    ),
    RED_WOOL(Material.WOOL, 14, "tile.cloth.red.name"), GREEN_WOOL(
        Material.WOOL, 13, "tile.cloth.green.name"
    ),
    BROWN_WOOL(Material.WOOL, 12, "tile.cloth.brown.name"), BLUE_WOOL(
        Material.WOOL, 11, "tile.cloth.blue.name"
    ),
    PURPLE_WOOL(Material.WOOL, 10, "tile.cloth.purple.name"), CYAN_WOOL(
        Material.WOOL, 9, "tile.cloth.cyan.name"
    ),
    LIGHT_GRAY_WOOL(Material.WOOL, 8, "tile.cloth.silver.name"), GRAY_WOOL(
        Material.WOOL, 7, "tile.cloth.gray.name"
    ),
    PINK_WOOL(Material.WOOL, 6, "tile.cloth.pink.name"), LIME_WOOL(
        Material.WOOL, 5, "tile.cloth.lime.name"
    ),
    YELLOW_WOOL(Material.WOOL, 4, "tile.cloth.yellow.name"), LIGHT_BLUE_WOOL(
        Material.WOOL, 3, "tile.cloth.lightBlue.name"
    ),
    MAGENTA_WOOL(Material.WOOL, 2, "tile.cloth.magenta.name"), ORANGE_WOOL(
        Material.WOOL, 1, "tile.cloth.orange.name"
    ),
    WOOL(Material.WOOL, "tile.cloth.white.name"), LAPIS_LAZULI_ORE(
        Material.LAPIS_ORE, "tile.oreLapis.name"
    ),
    LAPIS_LAZULI_BLOCK(Material.LAPIS_BLOCK, "tile.blockLapis.name"), DISPENSER(
        Material.DISPENSER, "tile.dispenser.name"
    ),
    DROPPER(Material.DROPPER, "tile.dropper.name"), NOTE_BLOCK(Material.NOTE_BLOCK, "tile.musicBlock.name"), CAKE_BLOCK(
        Material.CAKE_BLOCK, "tile.cake.name"
    ),
    CAKE(Material.CAKE, "tile.cake.name"), WHITE_BED(Material.BED, "item.bed.white.name"), BLACK_BED(
        Material.BED, 15, "item.bed.black.name"
    ),
    RED_BED(Material.BED, 14, "item.bed.red.name"), GREEN_BED(Material.BED, 13, "item.bed.green.name"), BROWN_BED(
        Material.BED, 12, "item.bed.brown.name"
    ),
    BLUE_BED(Material.BED, 11, "item.bed.blue.name"), PURPLE_BED(Material.BED, 10, "item.bed.purple.name"), CYAN_BED(
        Material.BED, 9, "item.bed.cyan.name"
    ),
    LIGHT_GRAY_BED(Material.BED, 8, "item.bed.silver.name"), GRAY_BED(
        Material.BED, 7, "item.bed.gray.name"
    ),
    PINK_BED(Material.BED, 6, "item.bed.pink.name"), LIME_BED(Material.BED, 5, "item.bed.lime.name"), YELLOW_BED(
        Material.BED, 4, "item.bed.yellow.name"
    ),
    LIGHT_BLUE_BED(Material.BED, 3, "item.bed.lightBlue.name"), MAGENTA_BED(
        Material.BED, 2, "item.bed.magenta.name"
    ),
    ORANGE_BED(Material.BED, 1, "item.bed.orange.name"), WOODEN_TRAPDOOR(
        Material.TRAP_DOOR, "tile.trapdoor.name"
    ),
    IRON_TRAPDOOR(Material.IRON_TRAPDOOR, "tile.ironTrapdoor.name"), COBWEB(
        Material.WEB, "tile.web.name"
    ),
    STONE_BRICKS(Material.SMOOTH_BRICK, "tile.stonebricksmooth.default.name"), MOSSY_STONE_BRICKS(
        Material.SMOOTH_BRICK, 1, "tile.stonebricksmooth.mossy.name"
    ),
    CRACKED_STONE_BRICKS(Material.SMOOTH_BRICK, 2, "tile.stonebricksmooth.cracked.name"), CHISELED_STONE_BRICKS(
        Material.SMOOTH_BRICK, 3, "tile.stonebricksmooth.chiseled.name"
    ),
    STONE_MONSTER_EGG(Material.MONSTER_EGGS, "tile.monsterStoneEgg.stone.name"), COBBLESTONE_MONSTER_EGG(
        Material.MONSTER_EGGS, 1, "tile.monsterStoneEgg.cobble.name"
    ),
    STONE_BRICK_MONSTER_EGG(Material.MONSTER_EGGS, 2, "tile.monsterStoneEgg.brick.name"), MOSSY_STONE_BRICK_MONSTER_EGG(
        Material.MONSTER_EGGS, 3, "tile.monsterStoneEgg.mossybrick.name"
    ),
    CRACKED_STONE_BRICK_MONSTER_EGG(
        Material.MONSTER_EGGS,
        4,
        "tile.monsterStoneEgg.crackedbrick.name"
    ),
    CHISELED_STONE_BRICK_MONSTER_EGG(
        Material.MONSTER_EGGS, 5, "tile.monsterStoneEgg.chiseledbrick.name"
    ),
    PISTON(Material.PISTON_BASE, "tile.pistonBase.name"), STICKY_PISTON(
        Material.PISTON_STICKY_BASE, "tile.pistonStickyBase.name"
    ),
    IRON_BARS(Material.IRON_FENCE, "tile.fenceIron.name"), MELON_BLOCK(
        Material.MELON_BLOCK, "tile.melon.name"
    ),
    BRICK_STAIRS(Material.BRICK_STAIRS, "tile.stairsBrick.name"), STONE_BRICK_STAIRS(
        Material.SMOOTH_STAIRS, "tile.stairsStoneBrickSmooth.name"
    ),
    VINES(Material.VINE, "tile.vine.name"), NETHER_BRICK_BLOCK(
        Material.NETHER_BRICK, "tile.netherBrick.name"
    ),
    NETHER_BRICK_FENCE(Material.NETHER_FENCE, "tile.netherFence.name"), NETHER_BRICK_STAIRS(
        Material.NETHER_BRICK_STAIRS, "tile.stairsNetherBrick.name"
    ),
    NETHER_WART_STALK(Material.NETHER_WARTS, "tile.netherStalk.name"), CAULDRON_BLOCK(
        Material.CAULDRON, "tile.cauldron.name"
    ),
    ENCHANTMENT_TABLE(Material.ENCHANTMENT_TABLE, "tile.enchantmentTable.name"), ANVIL(
        Material.ANVIL, 0, "tile.anvil.intact.name"
    ),
    SLIGHTLY_DAMAGED_ANVIL(Material.ANVIL, 1, "tile.anvil.slightlyDamaged.name"), VERY_DAMAGED_ANVIL(
        Material.ANVIL, 2, "tile.anvil.veryDamaged.name"
    ),
    END_STONE(Material.ENDER_STONE, "tile.whiteStone.name"), END_PORTAL(
        Material.ENDER_PORTAL, "tile.endPortalFrame.name"
    ),
    MYCELIUM(Material.MYCEL, "tile.mycel.name"), LILY_PAD(
        Material.WATER_LILY, "tile.waterlily.name"
    ),
    DRAGON_EGG(Material.DRAGON_EGG, "tile.dragonEgg.name"), REDSTONE_LAMP_ON(
        Material.REDSTONE_LAMP_ON, "tile.redstoneLight.name"
    ),
    REDSTONE_LAMP_OFF(Material.REDSTONE_LAMP_OFF, "tile.redstoneLight.name"), COCOA(
        Material.COCOA, "tile.cocoa.name"
    ),
    ENDER_CHEST(Material.ENDER_CHEST, "tile.enderChest.name"), EMERALD_ORE(
        Material.EMERALD_ORE, "tile.oreEmerald.name"
    ),
    BLOCK_OF_EMERALD(Material.EMERALD_BLOCK, "tile.blockEmerald.name"), BLOCK_OF_REDSTONE(
        Material.REDSTONE_BLOCK, "tile.blockRedstone.name"
    ),
    TRIPWIRE(Material.TRIPWIRE, "tile.tripWire.name"), TRIPWIRE_HOOK(
        Material.TRIPWIRE_HOOK, "tile.tripWireSource.name"
    ),
    COMMAND_BLOCK(Material.COMMAND, "tile.commandBlock.name"), REPEATING_COMMAND_BLOCK(
        Material.COMMAND_REPEATING, "tile.repeatingCommandBlock.name"
    ),
    CHAIN_COMMAND_BLOCK(Material.COMMAND_CHAIN, "tile.chainCommandBlock.name"), BEACON(
        Material.BEACON, "tile.beacon.name"
    ),
    COBBLESTONE_WALL(Material.COBBLE_WALL, "tile.cobbleWall.normal.name"), MOSSY_COBBLESTONE_WALL(
        Material.COBBLE_WALL, 1, "tile.cobbleWall.mossy.name"
    ),
    CARROTS(Material.CARROT, "tile.carrots.name"), POTATOES(
        Material.POTATO, "tile.potatoes.name"
    ),
    DAYLIGHT_SENSOR(Material.DAYLIGHT_DETECTOR, "tile.daylightDetector.name"), DAYLIGHT_SENSOR_INVERT(
        Material.DAYLIGHT_DETECTOR_INVERTED, "tile.daylightDetector.name"
    ),
    NETHER_QUARTZ_ORE(Material.QUARTZ_ORE, "tile.netherquartz.name"), HOPPER(
        Material.HOPPER, "tile.hopper.name"
    ),
    BLOCK_OF_QUARTZ(Material.QUARTZ_BLOCK, "tile.quartzBlock.default.name"), CHISELED_QUARTZ_BLOCK(
        Material.QUARTZ_BLOCK, 1, "tile.quartzBlock.chiseled.name"
    ),
    PILLAR_QUARTZ_BLOCK(Material.QUARTZ_BLOCK, 2, "tile.quartzBlock.lines.name"), QUARTZ_STAIRS(
        Material.QUARTZ_STAIRS, "tile.stairsQuartz.name"
    ),
    SLIME_BLOCK(Material.SLIME_BLOCK, "tile.slime.name"), PRISMARINE(
        Material.PRISMARINE, "tile.prismarine.rough.name"
    ),
    PRISMARINE_BRICKS(Material.PRISMARINE, 1, "tile.prismarine.bricks.name"), DARK_PRISMARINE(
        Material.PRISMARINE, 2, "tile.prismarine.dark.name"
    ),
    SEA_LANTERN(Material.SEA_LANTERN, "tile.seaLantern.name"), END_ROD(
        Material.END_ROD, "tile.endRod.name"
    ),
    CHORUS_PLANT(Material.CHORUS_PLANT, "tile.chorusPlant.name"), CHORUS_FLOWER(
        Material.CHORUS_FLOWER, "tile.chorusFlower.name"
    ),
    PURPUR_BLOCK(Material.PURPUR_BLOCK, "tile.purpurBlock.name"), PURPUR_PILLAR(
        Material.PURPUR_PILLAR, "tile.purpurPillar.name"
    ),
    PURPUR_STAIRS(Material.PURPUR_STAIRS, "tile.stairsPurpur.name"), PURPUR_DOUBLE_SLAB(
        Material.PURPUR_DOUBLE_SLAB, "tile.purpurSlab.name"
    ),
    PURPUR_SLAB(Material.PURPUR_SLAB, "tile.purpurSlab.name"), END_STONE_BRICKS(
        Material.END_BRICKS, "tile.endBricks.name"
    ),
    BEETROOTS(Material.BEETROOT_BLOCK, "tile.beetroots.name"), GRASS_PATH(
        Material.GRASS_PATH, "tile.grassPath.name"
    ),
    STRUCTURE_BLOCK(Material.STRUCTURE_BLOCK, "tile.structureBlock.name"), STRUCTURE_VOID(
        Material.STRUCTURE_VOID, "tile.structureVoid.name"
    ),
    NAME_TAG(Material.NAME_TAG, "item.nameTag.name"), LEAD(
        Material.LEASH, "item.leash.name"
    ),
    IRON_SHOVEL(Material.IRON_SPADE, "item.shovelIron.name"), IRON_PICKAXE(
        Material.IRON_PICKAXE, "item.pickaxeIron.name"
    ),
    IRON_AXE(Material.IRON_AXE, "item.hatchetIron.name"), FLINT_AND_STEEL(
        Material.FLINT_AND_STEEL, "item.flintAndSteel.name"
    ),
    APPLE(Material.APPLE, "item.apple.name"), COOKIE(Material.COOKIE, "item.cookie.name"), BOW(
        Material.BOW, "item.bow.name"
    ),
    ARROW(Material.ARROW, "item.arrow.name"), SPECTRAL_ARROW(
        Material.SPECTRAL_ARROW,
        "item.spectral_arrow.name"
    ),
    TIPPED_ARROW(
        Material.TIPPED_ARROW, "item.tipped_arrow.name"
    ),
    COAL(Material.COAL, "item.coal.name"), CHARCOAL(Material.COAL, 1, "item.charcoal.name"), DIAMOND(
        Material.DIAMOND, "item.diamond.name"
    ),
    EMERALD(Material.EMERALD, "item.emerald.name"), IRON_INGOT(Material.IRON_INGOT, "item.ingotIron.name"), GOLD_INGOT(
        Material.GOLD_INGOT, "item.ingotGold.name"
    ),
    IRON_SWORD(Material.IRON_SWORD, "item.swordIron.name"), WOODEN_SWORD(
        Material.WOOD_SWORD, "item.swordWood.name"
    ),
    WOODEN_SHOVEL(Material.WOOD_SPADE, "item.shovelWood.name"), WOODEN_PICKAXE(
        Material.WOOD_PICKAXE, "item.pickaxeWood.name"
    ),
    WOODEN_AXE(Material.WOOD_AXE, "item.hatchetWood.name"), STONE_SWORD(
        Material.STONE_SWORD, "item.swordStone.name"
    ),
    STONE_SHOVEL(Material.STONE_SPADE, "item.shovelStone.name"), STONE_PICKAXE(
        Material.STONE_PICKAXE, "item.pickaxeStone.name"
    ),
    STONE_AXE(Material.STONE_AXE, "item.hatchetStone.name"), DIAMOND_SWORD(
        Material.DIAMOND_SWORD, "item.swordDiamond.name"
    ),
    DIAMOND_SHOVEL(Material.DIAMOND_SPADE, "item.shovelDiamond.name"), DIAMOND_PICKAXE(
        Material.DIAMOND_PICKAXE, "item.pickaxeDiamond.name"
    ),
    DIAMOND_AXE(Material.DIAMOND_AXE, "item.hatchetDiamond.name"), STICK(
        Material.STICK, "item.stick.name"
    ),
    BOWL(Material.BOWL, "item.bowl.name"), MUSHROOM_STEW(
        Material.MUSHROOM_SOUP,
        "item.mushroomStew.name"
    ),
    GOLDEN_SWORD(
        Material.GOLD_SWORD, "item.swordGold.name"
    ),
    GOLDEN_SHOVEL(Material.GOLD_SPADE, "item.shovelGold.name"), GOLDEN_PICKAXE(
        Material.GOLD_PICKAXE, "item.pickaxeGold.name"
    ),
    GOLDEN_AXE(Material.GOLD_AXE, "item.hatchetGold.name"), STRING(
        Material.STRING, "item.string.name"
    ),
    FEATHER(Material.FEATHER, "item.feather.name"), GUNPOWDER(Material.SULPHUR, "item.sulphur.name"), WOODEN_HOE(
        Material.WOOD_HOE, "item.hoeWood.name"
    ),
    STONE_HOE(Material.STONE_HOE, "item.hoeStone.name"), IRON_HOE(Material.IRON_HOE, "item.hoeIron.name"), DIAMOND_HOE(
        Material.DIAMOND_HOE, "item.hoeDiamond.name"
    ),
    GOLDEN_HOE(Material.GOLD_HOE, "item.hoeGold.name"), SEEDS(
        Material.SEEDS, "item.seeds.name"
    ),
    PUMPKIN_SEEDS(Material.PUMPKIN_SEEDS, "item.seeds_pumpkin.name"), MELON_SEEDS(
        Material.MELON_SEEDS, "item.seeds_melon.name"
    ),
    MELON(Material.MELON, "item.melon.name"), WHEAT(Material.WHEAT, "item.wheat.name"), BREAD(
        Material.BREAD, "item.bread.name"
    ),
    LEATHER_CAP(Material.LEATHER_HELMET, "item.helmetCloth.name"), LEATHER_TUNIC(
        Material.LEATHER_CHESTPLATE, "item.chestplateCloth.name"
    ),
    LEATHER_PANTS(Material.LEATHER_LEGGINGS, "item.leggingsCloth.name"), LEATHER_BOOTS(
        Material.LEATHER_BOOTS, "item.bootsCloth.name"
    ),
    CHAIN_HELMET(Material.CHAINMAIL_HELMET, "item.helmetChain.name"), CHAIN_CHESTPLATE(
        Material.CHAINMAIL_CHESTPLATE, "item.chestplateChain.name"
    ),
    CHAIN_LEGGINGS(Material.CHAINMAIL_LEGGINGS, "item.leggingsChain.name"), CHAIN_BOOTS(
        Material.CHAINMAIL_BOOTS, "item.bootsChain.name"
    ),
    IRON_HELMET(Material.IRON_HELMET, "item.helmetIron.name"), IRON_CHESTPLATE(
        Material.IRON_CHESTPLATE, "item.chestplateIron.name"
    ),
    IRON_LEGGINGS(Material.IRON_LEGGINGS, "item.leggingsIron.name"), IRON_BOOTS(
        Material.IRON_BOOTS, "item.bootsIron.name"
    ),
    DIAMOND_HELMET(Material.DIAMOND_HELMET, "item.helmetDiamond.name"), DIAMOND_CHESTPLATE(
        Material.DIAMOND_CHESTPLATE, "item.chestplateDiamond.name"
    ),
    DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, "item.leggingsDiamond.name"), DIAMOND_BOOTS(
        Material.DIAMOND_BOOTS, "item.bootsDiamond.name"
    ),
    GOLDEN_HELMET(Material.GOLD_HELMET, "item.helmetGold.name"), GOLDEN_CHESTPLATE(
        Material.GOLD_CHESTPLATE, "item.chestplateGold.name"
    ),
    GOLDEN_LEGGINGS(Material.GOLD_LEGGINGS, "item.leggingsGold.name"), GOLDEN_BOOTS(
        Material.GOLD_BOOTS, "item.bootsGold.name"
    ),
    FLINT(Material.FLINT, "item.flint.name"), RAW_PORKCHOP(Material.PORK, "item.porkchopRaw.name"), COOKED_PORKCHOP(
        Material.GRILLED_PORK, "item.porkchopCooked.name"
    ),
    RAW_CHICKEN(Material.RAW_CHICKEN, "item.chickenRaw.name"), COOKED_CHICKEN(
        Material.COOKED_CHICKEN, "item.chickenCooked.name"
    ),
    RAW_MUTTON(Material.MUTTON, "item.muttonRaw.name"), COOKED_MUTTON(
        Material.COOKED_MUTTON, "item.muttonCooked.name"
    ),
    RAW_RABBIT(Material.RABBIT, "item.rabbitRaw.name"), COOKED_RABBIT(
        Material.COOKED_RABBIT, "item.rabbitCooked.name"
    ),
    RABBIT_STEW(Material.RABBIT_STEW, "item.rabbitStew.name"), RABBIT_S_FOOT(
        Material.RABBIT_FOOT, "item.rabbitFoot.name"
    ),
    RABBIT_HIDE(Material.RABBIT_HIDE, "item.rabbitHide.name"), RAW_BEEF(
        Material.RAW_BEEF, "item.beefRaw.name"
    ),
    STEAK(Material.COOKED_BEEF, "item.beefCooked.name"), PAINTING(Material.PAINTING, "item.painting.name"), ITEM_FRAME(
        Material.ITEM_FRAME, "item.frame.name"
    ),
    GOLDEN_APPLE(Material.GOLDEN_APPLE, "item.appleGold.name"), OAK_DOOR(
        Material.WOOD_DOOR, "item.doorOak.name"
    ),
    SPRUCE_DOOR(Material.SPRUCE_DOOR_ITEM, "item.doorSpruce.name"), BIRCH_DOOR(
        Material.BIRCH_DOOR_ITEM, "item.doorBirch.name"
    ),
    JUNGLE_DOOR(Material.JUNGLE_DOOR_ITEM, "item.doorJungle.name"), ACACIA_DOOR(
        Material.ACACIA_DOOR_ITEM, "item.doorAcacia.name"
    ),
    DARK_OAK_DOOR(Material.DARK_OAK_DOOR_ITEM, "item.doorDarkOak.name"), BUCKET(
        Material.BUCKET, "item.bucket.name"
    ),
    WATER_BUCKET(Material.WATER_BUCKET, "item.bucketWater.name"), LAVA_BUCKET(
        Material.LAVA_BUCKET, "item.bucketLava.name"
    ),
    MINECART(Material.MINECART, "item.minecart.name"), SADDLE(
        Material.SADDLE, "item.saddle.name"
    ),
    IRON_DOOR(Material.IRON_DOOR, "item.doorIron.name"), REDSTONE(Material.REDSTONE, "item.redstone.name"), SNOWBALL(
        Material.SNOW_BALL, "item.snowball.name"
    ),
    BOAT(Material.BOAT, "item.boat.oak.name"), OAK_BOAT(Material.BOAT, "item.boat.oak.name"), SPRUCE_BOAT(
        Material.BOAT_SPRUCE, "item.boat.spruce.name"
    ),
    BIRCH_BOAT(Material.BOAT_BIRCH, "item.boat.birch.name"), JUNGLE_BOAT(
        Material.BOAT_JUNGLE, "item.boat.jungle.name"
    ),
    ACACIA_BOAT(Material.BOAT_ACACIA, "item.boat.acacia.name"), DARK_OAK_BOAT(
        Material.BOAT_DARK_OAK, "item.boat.dark_oak.name"
    ),
    LEATHER(Material.LEATHER, "item.leather.name"), MILK(
        Material.MILK_BUCKET, "item.milk.name"
    ),
    BRICK(Material.CLAY_BRICK, "item.brick.name"), CLAY_BALL(Material.CLAY_BALL, "item.clay.name"), SUGAR_CANES(
        Material.SUGAR_CANE, "item.reeds.name"
    ),
    PAPER(Material.PAPER, "item.paper.name"), BOOK(Material.BOOK, "item.book.name"), SLIMEBALL(
        Material.SLIME_BALL, "item.slimeball.name"
    ),
    MINECART_WITH_CHEST(Material.STORAGE_MINECART, "item.minecartChest.name"), MINECART_WITH_FURNACE(
        Material.POWERED_MINECART, "item.minecartFurnace.name"
    ),
    MINECART_WITH_TNT(Material.EXPLOSIVE_MINECART, "item.minecartTnt.name"), MINECART_WITH_HOPPER(
        Material.HOPPER_MINECART, "item.minecartHopper.name"
    ),
    MINECART_WITH_COMMAND_BLOCK(Material.COMMAND_MINECART, "item.minecartCommandBlock.name"), EGG(
        Material.EGG, "item.egg.name"
    ),
    COMPASS(Material.COMPASS, "item.compass.name"), FISHING_ROD(Material.FISHING_ROD, "item.fishingRod.name"), CLOCK(
        Material.WATCH, "item.clock.name"
    ),
    GLOWSTONE_DUST(Material.GLOWSTONE_DUST, "item.yellowDust.name"), RAW_FISH(
        Material.RAW_FISH, "item.fish.cod.raw.name"
    ),
    RAW_SALMON(Material.RAW_FISH, 1, "item.fish.salmon.raw.name"), PUFFERFISH(
        Material.RAW_FISH, 3, "item.fish.pufferfish.raw.name"
    ),
    CLOWNFISH(Material.RAW_FISH, 2, "item.fish.clownfish.raw.name"), COOKED_FISH(
        Material.COOKED_FISH, "item.fish.cod.cooked.name"
    ),
    COOKED_SALMON(Material.COOKED_FISH, 1, "item.fish.salmon.cooked.name"), RECORD_1(
        Material.GREEN_RECORD, "item.record.name"
    ),
    RECORD_2(Material.GOLD_RECORD, "item.record.name"), RECORD_3(
        Material.RECORD_3, "item.record.name"
    ),
    RECORD_4(Material.RECORD_4, "item.record.name"), RECORD_5(Material.RECORD_5, "item.record.name"), RECORD_6(
        Material.RECORD_6, "item.record.name"
    ),
    RECORD_7(Material.RECORD_7, "item.record.name"), RECORD_8(Material.RECORD_8, "item.record.name"), RECORD_9(
        Material.RECORD_9, "item.record.name"
    ),
    RECORD_10(Material.RECORD_10, "item.record.name"), RECORD_11(Material.RECORD_11, "item.record.name"), RECORD_12(
        Material.RECORD_12, "item.record.name"
    ),
    BONE(Material.BONE, "item.bone.name"), INK_SAC(Material.INK_SACK, "item.dyePowder.black.name"), ROSE_RED(
        Material.INK_SACK, 1, "item.dyePowder.red.name"
    ),
    CACTUS_GREEN(Material.INK_SACK, 2, "item.dyePowder.green.name"), COCOA_BEANS(
        Material.INK_SACK, 3, "item.dyePowder.brown.name"
    ),
    LAPIS_LAZULI(Material.INK_SACK, 4, "item.dyePowder.blue.name"), PURPLE_DYE(
        Material.INK_SACK, 5, "item.dyePowder.purple.name"
    ),
    CYAN_DYE(Material.INK_SACK, 6, "item.dyePowder.cyan.name"), LIGHT_GRAY_DYE(
        Material.INK_SACK, 7, "item.dyePowder.silver.name"
    ),
    GRAY_DYE(Material.INK_SACK, 8, "item.dyePowder.gray.name"), PINK_DYE(
        Material.INK_SACK, 9, "item.dyePowder.pink.name"
    ),
    LIME_DYE(Material.INK_SACK, 10, "item.dyePowder.lime.name"), DANDELION_YELLOW(
        Material.INK_SACK, 11, "item.dyePowder.yellow.name"
    ),
    LIGHT_BLUE_DYE(Material.INK_SACK, 12, "item.dyePowder.lightBlue.name"), MAGENTA_DYE(
        Material.INK_SACK, 13, "item.dyePowder.magenta.name"
    ),
    ORANGE_DYE(Material.INK_SACK, 14, "item.dyePowder.orange.name"), BONE_MEAL(
        Material.INK_SACK, 15, "item.dyePowder.white.name"
    ),
    SUGAR(Material.SUGAR, "item.sugar.name"), REDSTONE_REPEATER(
        Material.DIODE, "item.diode.name"
    ),
    REDSTONE_REPEATER_ON(Material.DIODE_BLOCK_ON, "item.diode.name"), REDSTONE_REPEATER_OFF(
        Material.DIODE_BLOCK_OFF, "item.diode.name"
    ),
    REDSTONE_COMPARATOR(Material.REDSTONE_COMPARATOR, "item.comparator.name"), REDSTONE_COMPARATOR_ON(
        Material.REDSTONE_COMPARATOR_ON, "item.comparator.name"
    ),
    REDSTONE_COMPARATOR_OFF(Material.REDSTONE_COMPARATOR_OFF, "item.comparator.name"), MAP(
        Material.MAP, "item.map.name"
    ),
    SHEARS(Material.SHEARS, "item.shears.name"), ROTTEN_FLESH(
        Material.ROTTEN_FLESH,
        "item.rottenFlesh.name"
    ),
    ENDER_PEARL(
        Material.ENDER_PEARL, "item.enderPearl.name"
    ),
    BLAZE_ROD(Material.BLAZE_ROD, "item.blazeRod.name"), GHAST_TEAR(
        Material.GHAST_TEAR, "item.ghastTear.name"
    ),
    NETHER_WART(Material.NETHER_STALK, "item.netherStalkSeeds.name"), POTION(
        Material.POTION, "item.potion.name"
    ),
    SPLASH_POTION(Material.SPLASH_POTION, "item.splash_potion.name"), LINGERING_POTION(
        Material.LINGERING_POTION, "item.lingering_potion.name"
    ),
    END_CRYSTAL(Material.END_CRYSTAL, "item.end_crystal.name"), GOLD_NUGGET(
        Material.GOLD_NUGGET, "item.goldNugget.name"
    ),
    GLASS_BOTTLE(Material.GLASS_BOTTLE, "item.glassBottle.name"), SPIDER_EYE(
        Material.SPIDER_EYE, "item.spiderEye.name"
    ),
    FERMENTED_SPIDER_EYE(Material.FERMENTED_SPIDER_EYE, "item.fermentedSpiderEye.name"), BLAZE_POWDER(
        Material.BLAZE_POWDER, "item.blazePowder.name"
    ),
    MAGMA_CREAM(Material.MAGMA_CREAM, "item.magmaCream.name"), CAULDRON(
        Material.CAULDRON_ITEM, "item.cauldron.name"
    ),
    BREWING_STAND(Material.BREWING_STAND_ITEM, "item.brewingStand.name"), BREWING_STAND_BLOCK(
        Material.BREWING_STAND, "item.brewingStand.name"
    ),
    EYE_OF_ENDER(Material.EYE_OF_ENDER, "item.eyeOfEnder.name"), GLISTERING_MELON(
        Material.SPECKLED_MELON, "item.speckledMelon.name"
    ),
    SPAWN(Material.MONSTER_EGG, "item.monsterPlacer.name"), BOTTLE_O_ENCHANTING(
        Material.EXP_BOTTLE, "item.expBottle.name"
    ),
    FIRE_CHARGE(Material.FIREBALL, "item.fireball.name"), BOOK_AND_QUILL(
        Material.BOOK_AND_QUILL, "item.writingBook.name"
    ),
    WRITTEN_BOOK(Material.WRITTEN_BOOK, "item.writtenBook.name"), FLOWER_POT(
        Material.FLOWER_POT_ITEM, "item.flowerPot.name"
    ),
    FLOWER_POT_BLOCK(Material.FLOWER_POT, "item.flowerPot.name"), EMPTY_MAP(
        Material.EMPTY_MAP, "item.emptyMap.name"
    ),
    CARROT(Material.CARROT_ITEM, "item.carrots.name"), GOLDEN_CARROT(
        Material.GOLDEN_CARROT, "item.carrotGolden.name"
    ),
    POTATO(Material.POTATO_ITEM, "item.potato.name"), BAKED_POTATO(
        Material.BAKED_POTATO, "item.potatoBaked.name"
    ),
    POISONOUS_POTATO(Material.POISONOUS_POTATO, "item.potatoPoisonous.name"), SKELETON_SKULL(
        Material.SKULL_ITEM, "item.skull.skeleton.name"
    ),
    WITHER_SKELETON_SKULL(Material.SKULL_ITEM, 1, "item.skull.wither.name"), ZOMBIE_HEAD(
        Material.SKULL_ITEM, 2, "item.skull.zombie.name"
    ),
    PLAYER_HEAD(Material.SKULL_ITEM, 3, "item.skull.char.name"), CREEPER_HEAD(
        Material.SKULL_ITEM, 4, "item.skull.creeper.name"
    ),
    DRAGON_HEAD(Material.SKULL_ITEM, 5, "item.skull.dragon.name"), CARROT_ON_A_STICK(
        Material.CARROT_STICK, "item.carrotOnAStick.name"
    ),
    NETHER_STAR(Material.NETHER_STAR, "item.netherStar.name"), PUMPKIN_PIE(
        Material.PUMPKIN_PIE, "item.pumpkinPie.name"
    ),
    ENCHANTED_BOOK(Material.ENCHANTED_BOOK, "item.enchantedBook.name"), FIREWORK_ROCKET(
        Material.FIREWORK, "item.fireworks.name"
    ),
    FIREWORK_ROCKET_CHARGE(Material.FIREWORK_CHARGE, "item.fireworksCharge.name"), NETHER_BRICK(
        Material.NETHER_BRICK_ITEM, "item.netherbrick.name"
    ),
    NETHER_QUARTZ(Material.QUARTZ, "item.netherquartz.name"), ARMOR_STAND(
        Material.ARMOR_STAND, "item.armorStand.name"
    ),
    IRON_HORSE_ARMOR(Material.IRON_BARDING, "item.horsearmormetal.name"), GOLD_HORSE_ARMOR(
        Material.GOLD_BARDING, "item.horsearmorgold.name"
    ),
    DIAMOND_HORSE_ARMOR(Material.DIAMOND_BARDING, "item.horsearmordiamond.name"), PRISMARINE_SHARD(
        Material.PRISMARINE_SHARD, "item.prismarineShard.name"
    ),
    PRISMARINE_CRYSTALS(Material.PRISMARINE_CRYSTALS, "item.prismarineCrystals.name"), CHORUS_FRUIT(
        Material.CHORUS_FRUIT, "item.chorusFruit.name"
    ),
    POPPED_CHORUS_FRUIT(Material.CHORUS_FRUIT_POPPED, "item.chorusFruitPopped.name"), BEETROOT(
        Material.BEETROOT, "item.beetroot.name"
    ),
    BEETROOT_SEEDS(Material.BEETROOT_SEEDS, "item.beetroot_seeds.name"), BEETROOT_SOUP(
        Material.BEETROOT_SOUP, "item.beetroot_soup.name"
    ),
    DRAGONS_BREATH(Material.DRAGONS_BREATH, "item.dragon_breath.name"), ELYTRA(
        Material.ELYTRA, "item.elytra.name"
    ),
    BLACK_BANNER(Material.BANNER, "item.banner.black.name"), RED_BANNER(
        Material.BANNER, 1, "item.banner.red.name"
    ),
    GREEN_BANNER(Material.BANNER, 2, "item.banner.green.name"), BROWN_BANNER(
        Material.BANNER, 3, "item.banner.brown.name"
    ),
    BLUE_BANNER(Material.BANNER, 4, "item.banner.blue.name"), PURPLE_BANNER(
        Material.BANNER, 5, "item.banner.purple.name"
    ),
    CYAN_BANNER(Material.BANNER, 6, "item.banner.cyan.name"), LIGHT_GRAY_BANNER(
        Material.BANNER, 7, "item.banner.silver.name"
    ),
    GRAY_BANNER(Material.BANNER, 8, "item.banner.gray.name"), PINK_BANNER(
        Material.BANNER, 9, "item.banner.pink.name"
    ),
    LIME_BANNER(Material.BANNER, 10, "item.banner.lime.name"), YELLOW_BANNER(
        Material.BANNER, 11, "item.banner.yellow.name"
    ),
    LIGHT_BLUE_BANNER(Material.BANNER, 12, "item.banner.lightBlue.name"), MAGENTA_BANNER(
        Material.BANNER, 13, "item.banner.magenta.name"
    ),
    ORANGE_BANNER(Material.BANNER, 14, "item.banner.orange.name"), WHITE_BANNER(
        Material.BANNER, 15, "item.banner.white.name"
    ),
    SHIELD(Material.SHIELD, "item.shield.name"), BLACK_SHIELD(
        Material.SHIELD, 1, "item.shield.black.name"
    ),
    RED_SHIELD(Material.SHIELD, 2, "item.shield.red.name"), GREEN_SHIELD(
        Material.SHIELD, 3, "item.shield.green.name"
    ),
    BROWN_SHIELD(Material.SHIELD, 4, "item.shield.brown.name"), BLUE_SHIELD(
        Material.SHIELD, 5, "item.shield.blue.name"
    ),
    PURPLE_SHIELD(Material.SHIELD, 6, "item.shield.purple.name"), CYAN_SHIELD(
        Material.SHIELD, 7, "item.shield.cyan.name"
    ),
    LIGHT_GRAY_SHIELD(Material.SHIELD, 8, "item.shield.silver.name"), GRAY_SHIELD(
        Material.SHIELD, 9, "item.shield.gray.name"
    ),
    PINK_SHIELD(Material.SHIELD, 10, "item.shield.pink.name"), LIME_SHIELD(
        Material.SHIELD, 11, "item.shield.lime.name"
    ),
    YELLOW_SHIELD(Material.SHIELD, 12, "item.shield.yellow.name"), LIGHT_BLUE_SHIELD(
        Material.SHIELD, 13, "item.shield.lightBlue.name"
    ),
    MAGENTA_SHIELD(Material.SHIELD, 14, "item.shield.magenta.name"), ORANGE_SHIELD(
        Material.SHIELD, 15, "item.shield.orange.name"
    ),
    WHITE_SHIELD(Material.SHIELD, 16, "item.shield.white.name"), MAGMA_BLOCK(
        Material.MAGMA, "tile.magma.name"
    ),
    NETHER_WART_BLOCK(Material.NETHER_WART_BLOCK, "tile.netherWartBlock.name"), RED_NETHER_BRICK(
        Material.RED_NETHER_BRICK, "tile.redNetherBrick.name"
    ),
    BONE_BLOCK(Material.BONE_BLOCK, "tile.boneBlock.name"), OBSERVER(
        Material.OBSERVER, "tile.observer.name"
    ),
    WHITE_SHULKER_BOX(Material.WHITE_SHULKER_BOX, "tile.shulkerBoxWhite.name"), ORANGE_SHULKER_BOX(
        Material.ORANGE_SHULKER_BOX, "tile.shulkerBoxOrange.name"
    ),
    MAGENTA_SHULKER_BOX(Material.MAGENTA_SHULKER_BOX, "tile.shulkerBoxMagenta.name"), LIGHT_BLUE_SHULKER_BOX(
        Material.LIGHT_BLUE_SHULKER_BOX, "tile.shulkerBoxLightBlue.name"
    ),
    YELLOW_SHULKER_BOX(Material.YELLOW_SHULKER_BOX, "tile.shulkerBoxYellow.name"), LIME_SHULKER_BOX(
        Material.LIME_SHULKER_BOX, "tile.shulkerBoxLime.name"
    ),
    PINK_SHULKER_BOX(Material.PINK_SHULKER_BOX, "tile.shulkerBoxPink.name"), GRAY_SHULKER_BOX(
        Material.GRAY_SHULKER_BOX, "tile.shulkerBoxGray.name"
    ),
    LIGHT_GRAY_SHULKER_BOX(Material.SILVER_SHULKER_BOX, "tile.shulkerBoxSilver.name"), CYAN_SHULKER_BOX(
        Material.CYAN_SHULKER_BOX, "tile.shulkerBoxCyan.name"
    ),
    PURPLE_SHULKER_BOX(Material.PURPLE_SHULKER_BOX, "tile.shulkerBoxPurple.name"), BLUE_SHULKER_BOX(
        Material.BLUE_SHULKER_BOX, "tile.shulkerBoxBlue.name"
    ),
    BROWN_SHULKER_BOX(Material.BROWN_SHULKER_BOX, "tile.shulkerBoxBrown.name"), GREEN_SHULKER_BOX(
        Material.GREEN_SHULKER_BOX, "tile.shulkerBoxGreen.name"
    ),
    RED_SHULKER_BOX(Material.RED_SHULKER_BOX, "tile.shulkerBoxRed.name"), BLACK_SHULKER_BOX(
        Material.BLACK_SHULKER_BOX, "tile.shulkerBoxBlack.name"
    ),
    SHULKER_SHELL(Material.SHULKER_SHELL, "item.shulkerShell.name"), TOTEM(
        Material.TOTEM, "item.totem.name"
    ),
    IRON_NUGGET(Material.IRON_NUGGET, "item.ironNugget.name"), WHITE_GLAZED_TERRACOTTA(
        Material.WHITE_GLAZED_TERRACOTTA, "tile.glazedTerracottaWhite.name"
    ),
    ORANGE_GLAZED_TERRACOTTA(
        Material.ORANGE_GLAZED_TERRACOTTA,
        "tile.glazedTerracottaOrange.name"
    ),
    MAGENTA_GLAZED_TERRACOTTA(
        Material.MAGENTA_GLAZED_TERRACOTTA, "tile.glazedTerracottaMagenta.name"
    ),
    LIGHT_BLUE_GLAZED_TERRACOTTA(
        Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
        "tile.glazedTerracottaLightBlue.name"
    ),
    YELLOW_GLAZED_TERRACOTTA(
        Material.YELLOW_GLAZED_TERRACOTTA, "tile.glazedTerracottaYellow.name"
    ),
    LIME_GLAZED_TERRACOTTA(Material.LIME_GLAZED_TERRACOTTA, "tile.glazedTerracottaLime.name"), PINK_GLAZED_TERRACOTTA(
        Material.PINK_GLAZED_TERRACOTTA, "tile.glazedTerracottaPink.name"
    ),
    GRAY_GLAZED_TERRACOTTA(
        Material.GRAY_GLAZED_TERRACOTTA,
        "tile.glazedTerracottaGray.name"
    ),
    LIGHT_GRAY_GLAZED_TERRACOTTA(
        Material.SILVER_GLAZED_TERRACOTTA, "tile.glazedTerracottaSilver.name"
    ),
    CYAN_GLAZED_TERRACOTTA(Material.CYAN_GLAZED_TERRACOTTA, "tile.glazedTerracottaCyan.name"), PURPLE_GLAZED_TERRACOTTA(
        Material.PURPLE_GLAZED_TERRACOTTA, "tile.glazedTerracottaPurple.name"
    ),
    BLUE_GLAZED_TERRACOTTA(Material.BLUE_GLAZED_TERRACOTTA, "tile.glazedTerracottaBlue.name"), BROWN_GLAZED_TERRACOTTA(
        Material.BROWN_GLAZED_TERRACOTTA, "tile.glazedTerracottaBrown.name"
    ),
    GREEN_GLAZED_TERRACOTTA(Material.GREEN_GLAZED_TERRACOTTA, "tile.glazedTerracottaGreen.name"), RED_GLAZED_TERRACOTTA(
        Material.RED_GLAZED_TERRACOTTA, "tile.glazedTerracottaRed.name"
    ),
    BLACK_GLAZED_TERRACOTTA(Material.BLACK_GLAZED_TERRACOTTA, "tile.glazedTerracottaBlack.name"), BLACK_CONCRETE(
        Material.CONCRETE, 15, "tile.concrete.black.name"
    ),
    RED_CONCRETE(Material.CONCRETE, 14, "tile.concrete.red.name"), GREEN_CONCRETE(
        Material.CONCRETE, 13, "tile.concrete.green.name"
    ),
    BROWN_CONCRETE(Material.CONCRETE, 12, "tile.concrete.brown.name"), BLUE_CONCRETE(
        Material.CONCRETE, 11, "tile.concrete.blue.name"
    ),
    PURPLE_CONCRETE(Material.CONCRETE, 10, "tile.concrete.purple.name"), CYAN_CONCRETE(
        Material.CONCRETE, 9, "tile.concrete.cyan.name"
    ),
    LIGHT_GRAY_CONCRETE(Material.CONCRETE, 8, "tile.concrete.silver.name"), GRAY_CONCRETE(
        Material.CONCRETE, 7, "tile.concrete.gray.name"
    ),
    PINK_CONCRETE(Material.CONCRETE, 6, "tile.concrete.pink.name"), LIME_CONCRETE(
        Material.CONCRETE, 5, "tile.concrete.lime.name"
    ),
    YELLOW_CONCRETE(Material.CONCRETE, 4, "tile.concrete.yellow.name"), LIGHT_BLUE_CONCRETE(
        Material.CONCRETE, 3, "tile.concrete.lightBlue.name"
    ),
    MAGENTA_CONCRETE(Material.CONCRETE, 2, "tile.concrete.magenta.name"), ORANGE_CONCRETE(
        Material.CONCRETE, 1, "tile.concrete.orange.name"
    ),
    WHITE_CONCRETE(Material.CONCRETE, "tile.concrete.white.name"), BLACK_CONCRETE_POWDER(
        Material.CONCRETE_POWDER, 15, "tile.concretePowder.black.name"
    ),
    RED_CONCRETE_POWDER(Material.CONCRETE_POWDER, 14, "tile.concretePowder.red.name"), GREEN_CONCRETE_POWDER(
        Material.CONCRETE_POWDER, 13, "tile.concretePowder.green.name"
    ),
    BROWN_CONCRETE_POWDER(Material.CONCRETE_POWDER, 12, "tile.concretePowder.brown.name"), BLUE_CONCRETE_POWDER(
        Material.CONCRETE_POWDER, 11, "tile.concretePowder.blue.name"
    ),
    PURPLE_CONCRETE_POWDER(Material.CONCRETE_POWDER, 10, "tile.concretePowder.purple.name"), CYAN_CONCRETE_POWDER(
        Material.CONCRETE_POWDER, 9, "tile.concretePowder.cyan.name"
    ),
    LIGHT_GRAY_CONCRETE_POWDER(Material.CONCRETE_POWDER, 8, "tile.concretePowder.silver.name"), GRAY_CONCRETE_POWDER(
        Material.CONCRETE_POWDER, 7, "tile.concretePowder.gray.name"
    ),
    PINK_CONCRETE_POWDER(Material.CONCRETE_POWDER, 6, "tile.concretePowder.pink.name"), LIME_CONCRETE_POWDER(
        Material.CONCRETE_POWDER, 5, "tile.concretePowder.lime.name"
    ),
    YELLOW_CONCRETE_POWDER(Material.CONCRETE_POWDER, 4, "tile.concretePowder.yellow.name"), LIGHT_BLUE_CONCRETE_POWDER(
        Material.CONCRETE_POWDER, 3, "tile.concretePowder.lightBlue.name"
    ),
    MAGENTA_CONCRETE_POWDER(Material.CONCRETE_POWDER, 2, "tile.concretePowder.magenta.name"), ORANGE_CONCRETE_POWDER(
        Material.CONCRETE_POWDER, 1, "tile.concretePowder.orange.name"
    ),
    WHITE_CONCRETE_POWDER(Material.CONCRETE_POWDER, "tile.concretePowder.white.name"), KNOWLEDGE_BOOK(
        Material.KNOWLEDGE_BOOK, "item.knowledgeBook.name"
    );

    constructor(material: Material, unlocalizedName: String) : this(material, 0, unlocalizedName)

    companion object {
        private val lookup: MutableMap<ItemEntry, EnumItem> = HashMap()

        init {
            EnumSet.allOf(EnumItem::class.java).forEach {
                lookup[
                    ItemEntry(
                        it.material,
                        it.metadata
                    )
                ] = it
            }
        }

        operator fun get(entry: ItemEntry): EnumItem? {
            var result = lookup[entry]
            if (result == null) result = lookup[ItemEntry.from(entry.material)]
            return result
        }

        //    public static EnumItem get(ItemEntry entry) {
        //        for (EnumItem item : EnumItem.values())
        //            if (item.material == entry.getMaterial() && item.metadata == entry.getMetadata())
        //                return item;
        //        return null;
        //    }
        @Suppress("DEPRECATION")
        fun getPlayerSkullName(skull: ItemStack, locale: String?): String {
            val meta = skull.itemMeta as SkullMeta
            return if (meta.hasOwner()) {
                String.format(
                    translateToLocal("item.skull.player.name", locale!!),
                    meta.owner
                )
            } else translateToLocal("item.skull.char.name", locale!!)
        }
    }
}
