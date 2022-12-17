package me.slavita.construction.utils.language

import me.slavita.construction.utils.language.LanguageHelper.translateToLocal
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionType
import java.util.*

enum class EnumPotionEffect(
    val potionType: PotionType,
    val unlocalizedName: String,
    val unlocalizedSplashName: String,
    val unlocalizedLingeringName: String,
    val unlocalizedArrowName: String,
) {
    UNCRAFTABLE(
        PotionType.UNCRAFTABLE,
        "potion.effect.empty",
        "splash_potion.effect.empty",
        "lingering_potion.effect.empty",
        "tipped_arrow.effect.empty"
    ),
    WATER(
        PotionType.WATER,
        "potion.effect.water",
        "splash_potion.effect.water",
        "lingering_potion.effect.water",
        "tipped_arrow.effect.water"
    ),
    MUNDANE(
        PotionType.MUNDANE,
        "potion.effect.mundane",
        "splash_potion.effect.mundane",
        "lingering_potion.effect.mundane",
        "tipped_arrow.effect.mundane"
    ),
    THICK(
        PotionType.THICK,
        "potion.effect.thick",
        "splash_potion.effect.thick",
        "lingering_potion.effect.thick",
        "tipped_arrow.effect.thick"
    ),
    AWKWARD(
        PotionType.AWKWARD,
        "potion.effect.awkward",
        "splash_potion.effect.awkward",
        "lingering_potion.effect.awkward",
        "tipped_arrow.effect.awkward"
    ),
    NIGHT_VISION(
        PotionType.NIGHT_VISION,
        "potion.effect.night_vision",
        "splash_potion.effect.night_vision",
        "lingering_potion.effect.night_vision",
        "tipped_arrow.effect.night_vision"
    ),
    INVISIBILITY(
        PotionType.INVISIBILITY,
        "potion.effect.invisibility",
        "splash_potion.effect.invisibility",
        "lingering_potion.effect.invisibility",
        "tipped_arrow.effect.invisibility"
    ),
    JUMP(
        PotionType.JUMP,
        "potion.effect.leaping",
        "splash_potion.effect.leaping",
        "lingering_potion.effect.leaping",
        "tipped_arrow.effect.leaping"
    ),
    FIRE_RESISTANCE(
        PotionType.FIRE_RESISTANCE,
        "potion.effect.fire_resistance",
        "splash_potion.effect.fire_resistance",
        "lingering_potion.effect.fire_resistance",
        "tipped_arrow.effect.fire_resistance"
    ),
    SPEED(
        PotionType.SPEED,
        "potion.effect.swiftness",
        "splash_potion.effect.swiftness",
        "lingering_potion.effect.swiftness",
        "tipped_arrow.effect.swiftness"
    ),
    SLOW(
        PotionType.SLOWNESS,
        "potion.effect.slowness",
        "splash_potion.effect.slowness",
        "lingering_potion.effect.slowness",
        "tipped_arrow.effect.slowness"
    ),
    WATER_BREATHING(
        PotionType.WATER_BREATHING,
        "potion.effect.water_breathing",
        "splash_potion.effect.water_breathing",
        "lingering_potion.effect.water_breathing",
        "tipped_arrow.effect.water_breathing"
    ),
    HEAL(
        PotionType.INSTANT_HEAL,
        "potion.effect.healing",
        "splash_potion.effect.healing",
        "lingering_potion.effect.healing",
        "tipped_arrow.effect.healing"
    ),
    HARM(
        PotionType.INSTANT_DAMAGE,
        "potion.effect.harming",
        "splash_potion.effect.harming",
        "lingering_potion.effect.harming",
        "tipped_arrow.effect.harming"
    ),
    POISON(
        PotionType.POISON,
        "potion.effect.poison",
        "splash_potion.effect.poison",
        "lingering_potion.effect.poison",
        "tipped_arrow.effect.poison"
    ),
    REGENERATION(
        PotionType.REGEN,
        "potion.effect.regeneration",
        "splash_potion.effect.regeneration",
        "lingering_potion.effect.regeneration",
        "tipped_arrow.effect.regeneration"
    ),
    INCREASE_DAMAGE(
        PotionType.STRENGTH,
        "potion.effect.strength",
        "splash_potion.effect.strength",
        "lingering_potion.effect.strength",
        "tipped_arrow.effect.strength"
    ),
    WEAKNESS(
        PotionType.WEAKNESS,
        "potion.effect.weakness",
        "splash_potion.effect.weakness",
        "lingering_potion.effect.weakness",
        "tipped_arrow.effect.weakness"
    ),
    LUCK(
        PotionType.LUCK,
        "potion.effect.luck",
        "splash_potion.effect.luck",
        "lingering_potion.effect.luck",
        "tipped_arrow.effect.luck"
    );

    companion object {
        private val lookup: HashMap<PotionType, EnumPotionEffect> = hashMapOf()

        init {
            for (effect in EnumSet.allOf(EnumPotionEffect::class.java))
                lookup[effect.potionType] = effect
        }

        operator fun get(effectType: PotionType): EnumPotionEffect? {
            return lookup[effectType]
        }

        fun getUnlocalizedName(potion: ItemStack): String {
            val meta = potion.itemMeta as PotionMeta
            val type = meta.basePotionData.getType()
            val effect = Companion[type]
            return effect?.unlocalizedName ?: type.name
        }

        fun getUnlocalizedSplashName(potion: ItemStack): String {
            val meta = potion.itemMeta as PotionMeta
            val type = meta.basePotionData.getType()
            val effect = Companion[type]
            return effect?.unlocalizedSplashName ?: ("SPLASH_" + type.name)
        }

        fun getUnlocalizedLingeringName(potion: ItemStack): String {
            val meta = potion.itemMeta as PotionMeta
            val type = meta.basePotionData.getType()
            val effect = Companion[type]
            return effect?.unlocalizedLingeringName ?: ("LINGERING_" + type.name)
        }

        fun getUnlocalizedArrowName(arrow: ItemStack): String {
            val meta = arrow.itemMeta as PotionMeta
            val type = meta.basePotionData.getType()
            val effect = Companion[type]
            return effect?.unlocalizedArrowName ?: ("TIPPED_ARROW_" + type.name)
        }

        fun getLocalizedName(itemStack: ItemStack, locale: String?): String {
            return when (itemStack.getType()) {
                Material.SPLASH_POTION    -> translateToLocal(
                    getUnlocalizedSplashName(
                        itemStack
                    ), locale!!
                )

                Material.LINGERING_POTION -> translateToLocal(
                    getUnlocalizedLingeringName(
                        itemStack
                    ), locale!!
                )

                Material.TIPPED_ARROW     -> translateToLocal(
                    getUnlocalizedArrowName(
                        itemStack
                    ), locale!!
                )

                else                      -> translateToLocal(
                    getUnlocalizedName(
                        itemStack
                    ), locale!!
                )
            }
        }
    }
}