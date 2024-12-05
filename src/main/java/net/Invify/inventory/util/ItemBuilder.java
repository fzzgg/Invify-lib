package net.Invify.inventory.util;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;
import java.util.function.Consumer;

/**
 * A flexible and fluent utility class for creating and manipulating Minecraft ItemStacks.
 *
 * This builder provides a convenient way to construct and customize items with various properties
 * such as name, lore, enchantments, NBT data, and more. It supports method chaining for
 * easy and readable item creation.
 */
public final class ItemBuilder {

    // The ItemStack being constructed
    private final ItemStack itemStack;

    // Metadata for the ItemStack that allows for detailed customization
    private final ItemMeta itemMeta;

    /**
     * Constructor that creates an ItemBuilder from a Material.
     *
     * @param material The type of material to create an ItemStack from
     */
    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    /**
     * Constructor that creates an ItemBuilder from an existing ItemStack.
     *
     * @param itemStack The base ItemStack to build upon
     * @throws NullPointerException if itemStack is null
     * @throws IllegalArgumentException if the item type doesn't support item meta
     */
    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = Objects.requireNonNull(itemStack, "ItemStack cannot be null");
        this.itemMeta = Optional.ofNullable(itemStack.getItemMeta())
                .orElseThrow(() -> new IllegalArgumentException("Type " + itemStack.getType() + " does not support item meta"));
    }

    /**
     * Changes the material type of the ItemStack.
     *
     * @param material The new material to set
     * @return This ItemBuilder for method chaining
     */
    public ItemBuilder type(Material material) {
        itemStack.setType(material);
        return this;
    }

    /**
     * Sets the amount of items in the stack, ensuring it's between 1 and 64.
     *
     * @param amount The number of items in the stack
     * @return This ItemBuilder for method chaining
     */
    public ItemBuilder amount(int amount) {
        itemStack.setAmount(Math.max(1, Math.min(amount, 64)));
        return this;
    }

    /**
     * Sets the damage of the item (for tools and armor).
     *
     * @param damage The damage value to set
     * @return This ItemBuilder for method chaining
     */
    public ItemBuilder damage(int damage) {
        if (itemMeta instanceof Damageable damageable) {
            damageable.setDamage(Math.max(0, damage));
        }
        return this;
    }

    /**
     * Adds an enchantment to the item.
     *
     * @param enchantment The enchantment to add
     * @param level The level of the enchantment
     * @return This ItemBuilder for method chaining
     */
    public ItemBuilder enchant(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * Sets the display name of the item.
     *
     * @param name The name to display for the item
     * @return This ItemBuilder for method chaining
     */
    public ItemBuilder name(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    /**
     * Sets the lore (description lines) of the item.
     *
     * @param loreLines A list of strings to use as lore
     * @return This ItemBuilder for method chaining
     */
    public ItemBuilder lore(List<String> loreLines) {
        itemMeta.setLore(loreLines);
        return this;
    }

    /**
     * Hides all item flags, making enchantments and other special properties invisible.
     *
     * @return This ItemBuilder for method chaining
     */
    public ItemBuilder hideAllFlags() {
        itemMeta.addItemFlags(ItemFlag.values());
        return this;
    }

    /**
     * Sets the color of leather armor.
     *
     * @param color The color to apply to leather armor
     * @return This ItemBuilder for method chaining
     */
    public ItemBuilder armorColor(Color color) {
        if (itemMeta instanceof LeatherArmorMeta leatherMeta) {
            leatherMeta.setColor(color);
        }
        return this;
    }

    /**
     * Allows custom modification of item metadata for specific item types.
     *
     * @param metaModifier A consumer that applies modifications to the meta
     * @return This ItemBuilder for method chaining
     */
    public ItemBuilder editItemMeta(Consumer<ItemMeta> metaModifier) {
        if (itemMeta != null) {
            metaModifier.accept(itemMeta);
        }
        return this;
    }

    /**
     * Sets whether the item is unbreakable.
     *
     * @param unbreakable Whether the item should be unbreakable
     * @return This ItemBuilder for method chaining
     */
    public ItemBuilder unbreakable(boolean unbreakable) {
        itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Adds custom NBT (Named Binary Tag) data to the item.
     * Supports various data types including String, Integer, Double, Float, Boolean,
     * Long, Byte, Short, and List of Strings.
     *
     * @param key The key for the NBT data
     * @param value The value to store
     * @return This ItemBuilder for method chaining
     * @throws IllegalArgumentException if an unsupported data type is provided
     */
    public ItemBuilder addNBT(String key, Object value) {
        NBTItem nbtItem = new NBTItem(itemStack);
        NBTCompound compound = nbtItem.getOrCreateCompound("customData");

        // Handle different types of NBT data
        if (value instanceof String) {
            compound.setString(key, (String) value);
        } else if (value instanceof Integer) {
            compound.setInteger(key, (Integer) value);
        } else if (value instanceof Double) {
            compound.setDouble(key, (Double) value);
        } else if (value instanceof Float) {
            compound.setFloat(key, (Float) value);
        } else if (value instanceof Boolean) {
            compound.setBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            compound.setLong(key, (Long) value);
        } else if (value instanceof Byte) {
            compound.setByte(key, (Byte) value);
        } else if (value instanceof Short) {
            compound.setShort(key, (Short) value);
        } else if (value instanceof List<?> list && !list.isEmpty() && list.get(0) instanceof String) {
            @SuppressWarnings("unchecked")
            List<String> stringList = (List<String>) list;
            compound.getStringList(key).addAll(stringList);
        } else {
            throw new IllegalArgumentException("Unsupported NBT type: " + value.getClass().getSimpleName());
        }

        this.itemStack.setItemMeta(nbtItem.getItem().getItemMeta());
        return this;
    }

    /**
     * Finalizes and returns the constructed ItemStack.
     *
     * @return The fully configured ItemStack
     */
    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return new NBTItem(itemStack).getItem();
    }

    /**
     * Static factory method for creating an ItemBuilder.
     *
     * @param material The material to create an ItemBuilder for
     * @return A new ItemBuilder instance
     */
    public static ItemBuilder create(Material material) {
        return new ItemBuilder(material);
    }

}