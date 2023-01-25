package me.slavita.construction.utils.language

import me.slavita.construction.prepare.IRegistrable
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

object LanguageHelper : IRegistrable {

    override fun register() {
        EnumLang.init()
    }

    fun getItemDisplayName(item: ItemStack, locale: String): String {
        return if (item.hasItemMeta() && item.itemMeta.hasDisplayName()) item.itemMeta.displayName else getItemName(
            item,
            locale
        )
    }

    fun getItemDisplayName(item: ItemStack, player: Player?): String {
        return getItemDisplayName(item, getPlayerLanguage(player))
    }

    fun getItemName(item: ItemStack, locale: String): String {
        return when (item.getType()) {
            Material.POTION,
            Material.SPLASH_POTION,
            Material.LINGERING_POTION,
            Material.TIPPED_ARROW,
                                 -> EnumPotionEffect.getLocalizedName(
                item,
                locale
            )

            Material.MONSTER_EGG -> EnumEntity.getSpawnEggName(
                item,
                locale
            )

            Material.SKULL_ITEM  -> {
                if (item.getDurability().toInt() == 3) {
                    EnumItem.getPlayerSkullName(item, locale)
                } else translateToLocal(getItemUnlocalizedName(item), locale)
            }

            else                 -> translateToLocal(
                getItemUnlocalizedName(item),
                locale
            )
        }
    }

    fun getItemName(item: ItemStack, player: Player?): String {
        return getItemName(item, getPlayerLanguage(player))
    }

    fun getMetaName(material: Material, meta: Int, player: Player?): String {
        return translateToLocal(getItemUnlocalizedName(material, meta), getPlayerLanguage(player))
    }

    fun getMetaName(material: Material, meta: Int, locale: String): String {
        return translateToLocal(getItemUnlocalizedName(material, meta), locale)
    }

    fun getItemUnlocalizedName(item: ItemStack): String {
        val enumItem = EnumItem[ItemEntry.from(item)]
        return enumItem?.unlocalizedName ?: item.getType().name
    }

    fun getItemUnlocalizedName(material: Material, meta: Int): String {
        val enumItem = EnumItem[ItemEntry.from(material, meta)]
        return enumItem?.unlocalizedName ?: material.name
    }

    fun getEntityUnlocalizedName(entity: Entity): String {
        val enumEntity = EnumEntity[entity.type]
        return enumEntity?.unlocalizedName ?: entity.type.toString()
    }

    fun getEntityUnlocalizedName(entityType: EntityType): String {
        val enumEntity = EnumEntity.get(entityType)
        return enumEntity?.unlocalizedName ?: entityType.toString()
    }

    fun getEntityDisplayName(entity: Entity, locale: String): String {
        return if (entity.customName != null) entity.customName else getEntityName(entity, locale)
    }

    fun getEntityDisplayName(entity: Entity, player: Player?): String {
        return getEntityDisplayName(entity, getPlayerLanguage(player))
    }

    fun getEntityName(entity: Entity, locale: String): String {
        return translateToLocal(getEntityUnlocalizedName(entity), locale)
    }

    fun getEntityName(entity: Entity, player: Player?): String {
        return getEntityName(entity, getPlayerLanguage(player))
    }

    fun getEntityName(entityType: EntityType, locale: String): String {
        return translateToLocal(getEntityUnlocalizedName(entityType), locale)
    }

    fun getEntityName(entityType: EntityType, player: Player?): String {
        return getEntityName(entityType, getPlayerLanguage(player))
    }

    fun getEnchantmentLevelUnlocalizedName(level: Int): String {
        val enumEnchLevel = EnumEnchantmentLevel[level]
        return enumEnchLevel?.unlocalizedName ?: level.toString()
    }

    fun getEnchantmentLevelName(level: Int, player: Player?): String {
        return translateToLocal(getEnchantmentLevelUnlocalizedName(level), getPlayerLanguage(player))
    }

    fun getEnchantmentLevelName(level: Int, locale: String): String {
        return translateToLocal(getEnchantmentLevelUnlocalizedName(level), locale)
    }

    fun getEnchantmentUnlocalizedName(enchantment: Enchantment): String {
        val enumEnch = EnumEnchantment[enchantment]
        return enumEnch?.unlocalizedName ?: enchantment.name
    }

    fun getEnchantmentName(enchantment: Enchantment, player: Player?): String {
        return getEnchantmentName(enchantment, getPlayerLanguage(player))
    }

    fun getEnchantmentName(enchantment: Enchantment, locale: String): String {
        return translateToLocal(getEnchantmentUnlocalizedName(enchantment), locale)
    }

    fun getEnchantmentDisplayName(enchantment: Enchantment, level: Int, player: Player?): String {
        return getEnchantmentDisplayName(enchantment, level, getPlayerLanguage(player))
    }

    fun getEnchantmentDisplayName(enchantment: Enchantment, level: Int, locale: String): String {
        val name = getEnchantmentName(enchantment, locale)
        val enchLevel = getEnchantmentLevelName(level, locale)
        return name + if (enchLevel.isNotEmpty()) " $enchLevel" else ""
    }

    fun getEnchantmentDisplayName(entry: Map.Entry<Enchantment, Int>, locale: String): String {
        return getEnchantmentDisplayName(entry.key, entry.value, locale)
    }

    fun getEnchantmentDisplayName(entry: Map.Entry<Enchantment, Int>, player: Player?): String {
        return getEnchantmentDisplayName(entry.key, entry.value, player)
    }

    fun translateToLocal(unlocalizedName: String, locale: String): String {
        var result = EnumLang[locale.lowercase(Locale.getDefault())].map[unlocalizedName]
        if (result != null) return result else {
            result = EnumLang["ru_ru"].map[unlocalizedName]
            if (result == null) result = EnumLang.RU_RU.map[unlocalizedName]
        }
        return result ?: unlocalizedName
    }

    fun getPlayerLanguage(player: Player?): String {
        return player!!.locale
    }
}