package me.slavita.construction.utils.language

import me.slavita.construction.utils.language.LanguageHelper.translateToLocal
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack
import java.lang.reflect.InvocationTargetException
import java.util.*

enum class EnumEntity(val type: EntityType, val unlocalizedName: String) {
    ITEM(EntityType.DROPPED_ITEM, "entity.Item.name"), EXPERIENCE_ORB(
        EntityType.EXPERIENCE_ORB,
        "entity.XPOrb.name"
    ),
    SMALL_FIREBALL(EntityType.SMALL_FIREBALL, "entity.SmallFireball.name"), FIREBALL(
        EntityType.FIREBALL,
        "entity.Fireball.name"
    ),
    DRAGON_FIREBALL(EntityType.DRAGON_FIREBALL, "entity.DragonFireball.name"), POTION(
        EntityType.SPLASH_POTION,
        "entity.ThrownPotion.name"
    ),
    ARROW(EntityType.ARROW, "entity.Arrow.name"), SNOWBALL(EntityType.SNOWBALL, "entity.Snowball.name"), PAINTING(
        EntityType.PAINTING,
        "entity.Painting.name"
    ),
    ARMOR_STAND(EntityType.ARMOR_STAND, "entity.ArmorStand.name"), CREEPER(
        EntityType.CREEPER,
        "entity.Creeper.name"
    ),
    SKELETON(EntityType.SKELETON, "entity.Skeleton.name"), SPIDER(EntityType.SPIDER, "entity.Spider.name"), GIANT(
        EntityType.GIANT,
        "entity.Giant.name"
    ),
    ZOMBIE(EntityType.ZOMBIE, "entity.Zombie.name"), SLIME(
        EntityType.SLIME,
        "entity.Slime.name"
    ),
    GHAST(EntityType.GHAST, "entity.Ghast.name"), ZOMBIE_PIGMAN(
        EntityType.PIG_ZOMBIE,
        "entity.PigZombie.name"
    ),
    ENDERMAN(EntityType.ENDERMAN, "entity.Enderman.name"), ENDERMITE(
        EntityType.ENDERMITE,
        "entity.Endermite.name"
    ),
    SILVERFISH(EntityType.SILVERFISH, "entity.Silverfish.name"), CAVE_SPIDER(
        EntityType.CAVE_SPIDER,
        "entity.CaveSpider.name"
    ),
    BLAZE(EntityType.BLAZE, "entity.Blaze.name"), MAGMA_CUBE(EntityType.MAGMA_CUBE, "entity.LavaSlime.name"), MOOSHROOM(
        EntityType.MUSHROOM_COW,
        "entity.MushroomCow.name"
    ),
    VILLAGER(EntityType.VILLAGER, "entity.Villager.name"), IRON_GOLEM(
        EntityType.IRON_GOLEM,
        "entity.VillagerGolem.name"
    ),
    SNOW_GOLEM(EntityType.SNOWMAN, "entity.SnowMan.name"), ENDER_DRAGON(
        EntityType.ENDER_DRAGON,
        "entity.EnderDragon.name"
    ),
    WITHER(EntityType.WITHER, "entity.WitherBoss.name"), WITCH(EntityType.WITCH, "entity.Witch.name"), GUARDIAN(
        EntityType.GUARDIAN,
        "entity.Guardian.name"
    ),
    SHULKER(EntityType.SHULKER, "entity.Shulker.name"), PIG(EntityType.PIG, "entity.Pig.name"), SHEEP(
        EntityType.SHEEP,
        "entity.Sheep.name"
    ),
    COW(EntityType.COW, "entity.Cow.name"), CHICKEN(EntityType.CHICKEN, "entity.Chicken.name"), SQUID(
        EntityType.SQUID,
        "entity.Squid.name"
    ),
    WOLF(EntityType.WOLF, "entity.Wolf.name"), OCELOT(EntityType.OCELOT, "entity.Ozelot.name"), BAT(
        EntityType.BAT,
        "entity.Bat.name"
    ),
    HORSE(EntityType.HORSE, "entity.Horse.name"), RABBIT(EntityType.RABBIT, "entity.Rabbit.name"), BLOCK_OF_TNT(
        EntityType.PRIMED_TNT,
        "entity.PrimedTnt.name"
    ),
    FALLING_BLOCK(EntityType.FALLING_BLOCK, "entity.FallingSand.name"), MINECART(
        EntityType.MINECART,
        "entity.Minecart.name"
    ),
    MINECART_WITH_HOPPER(
        EntityType.MINECART_HOPPER,
        "entity.MinecartHopper.name"
    ),
    MINECART_WITH_CHEST(EntityType.MINECART_CHEST, "entity.MinecartChest.name"), BOAT(
        EntityType.BOAT,
        "entity.Boat.name"
    ),
    POLAR_BEAR(EntityType.POLAR_BEAR, "entity.PolarBear.name"), ZOMBIE_VILLIGER(
        EntityType.ZOMBIE_VILLAGER,
        "entity.ZombieVillager.name"
    ),
    ELDER_GUARDIAN(EntityType.ELDER_GUARDIAN, "entity.ElderGuardian.name"), EVOKER(
        EntityType.EVOKER,
        "entity.EvocationIllager.name"
    ),
    VEX(EntityType.VEX, "entity.Vex.name"), VINDICATOR(EntityType.VINDICATOR, "entity.VindicationIllager.name"), LLAMA(
        EntityType.LLAMA,
        "entity.Llama.name"
    ),
    WITHER_SKELETON(EntityType.WITHER_SKELETON, "entity.WitherSkeleton.name"), STRAY(
        EntityType.STRAY,
        "entity.Stray.name"
    ),
    HUSK(EntityType.HUSK, "entity.Husk.name"), SKELETON_HORSE(
        EntityType.SKELETON_HORSE,
        "entity.SkeletonHorse.name"
    ),
    ZOMBIE_HORSE(EntityType.ZOMBIE_HORSE, "entity.ZombieHorse.name"), DONKEY(
        EntityType.DONKEY,
        "entity.Donkey.name"
    ),
    MULE(EntityType.MULE, "entity.Mule.name"), PARROT(
        EntityType.PARROT,
        "entity.Parrot.name"
    ),
    ILLUSIONER(EntityType.ILLUSIONER, "entity.IllusionIllager.name");

    companion object {
        // Some entity subtypes are not included
        private val lookup: MutableMap<EntityType?, EnumEntity> = HashMap()

        init {
            for (entity in EnumSet.allOf(EnumEntity::class.java))
                lookup[entity.type] = entity
        }

        operator fun get(entityType: EntityType?): EnumEntity? {
            return lookup[entityType]
        }

        fun getSpawnEggName(egg: ItemStack?, locale: String?): String {
            var type: EntityType? = null
            try {
                type = getEntityType(egg)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val entity = Companion[type]
            return if (entity != null) (translateToLocal("item.monsterPlacer.name", locale!!) + " "
                    + translateToLocal(entity.unlocalizedName, locale)) else translateToLocal(
                "item.monsterPlacer.name",
                locale!!
            )
        }

        @Throws(
            ClassNotFoundException::class,
            NoSuchMethodException::class,
            InvocationTargetException::class,
            IllegalAccessException::class
        )
        fun getEntityType(egg: ItemStack?): EntityType {
            val nmsStack = Class.forName(
                "org.bukkit.craftbukkit." + Bukkit.getServer().javaClass.getPackage().name.replace(
                    ".",
                    ","
                ).split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[3] + "." + "inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack::class.java)
                .invoke(null, egg)
            val tag = nmsStack.javaClass.getMethod("getTag").invoke(nmsStack)
            val entityTag = tag.javaClass.getMethod("getCompound", String::class.java).invoke(tag, "EntityTag")
            val id = entityTag.javaClass.getMethod("getString", String::class.java).invoke(entityTag, "id") as String
            return EntityType.fromName(id.replace("minecraft:", ""))
        }
    }
}