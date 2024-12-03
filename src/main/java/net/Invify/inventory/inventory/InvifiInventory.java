package net.Invify.inventory.inventory;

import net.Invify.inventory.InvifyAPI;
import net.Invify.inventory.api.info.InventoryInfo;
import net.Invify.inventory.api.item.CustomItem;
import net.Invify.inventory.api.size.InvifiInventorySize;
import net.Invify.inventory.util.AdventureUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public abstract class InvifiInventory {

    private final Inventory inventory;

    private final InvifiInventorySize invifiInventorySize;

    /*
        Variable to control whether clicking in the inventory should be canceled
     */
    private final boolean cancelClickEvent;

    private CustomItem[] items;

    public InvifiInventory() {
        InventoryInfo inventoryInfo = getClass().getAnnotation(InventoryInfo.class);
        if (inventoryInfo == null) {
            throw new IllegalArgumentException("Inventory \"" + getClass().getName() + "\" is not annotated with @InventoryInfo");
        }

        // Create the inventory based on the size and name provided in the annotation
        this.inventory = Bukkit.createInventory(new InvifiInventoryHolder(this), inventoryInfo.size().getSize(), AdventureUtil.colorize(inventoryInfo.displayName()
                .replace(">>", "»")
                .replace("<<", "«")));

        this.invifiInventorySize = inventoryInfo.size();
        this.cancelClickEvent = inventoryInfo.cancelClickEvent();

        this.items = new CustomItem[inventoryInfo.size().getSize()];
    }

    public InvifiInventory(InvifiInventorySize invifiInventorySize, String displayName) {
        // Create the inventory with custom size and display name
        this.inventory = Bukkit.createInventory(new InvifiInventoryHolder(this), invifiInventorySize.getSize(), AdventureUtil.colorize(displayName
                .replace(">>", "»")
                .replace("<<", "«")));

        this.items = new CustomItem[invifiInventorySize.getSize()];
        this.invifiInventorySize = invifiInventorySize;

        InventoryInfo inventoryInfo = getClass().getAnnotation(InventoryInfo.class);
        if (inventoryInfo != null) {
            this.cancelClickEvent = inventoryInfo.cancelClickEvent();
        } else {
            this.cancelClickEvent = false;  // Default value if no annotation is found
        }
    }

    /**
     * Clears the inventory and resets the items array.
     */
    public void clear() {
        this.inventory.clear();
        this.items = new CustomItem[this.invifiInventorySize.getSize()];
    }

    /*
        Abstract method for updating the inventory contents
     */
    public abstract void updateInventory();

    /**
     * Gets the item in the specified slot.
     *
     * @param slot The slot to retrieve the item from.
     * @return The item at the specified slot, or null if the slot is empty.
     */
    public CustomItem getItem(int slot) {
        try {
            return items[slot];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;  // Return null if the slot is out of bounds
        }
    }

    /**
     * Sets an item in the specified slot.
     *
     * @param slot The slot to place the item in.
     * @param item The item to place in the slot.
     */
    public void setItem(int slot, CustomItem item) {
        this.items[slot] = item;
        this.inventory.setItem(slot, item.getItemStack());  // Update the inventory's content
    }

    /**
     * Adds an item to the first available empty slot in the inventory.
     *
     * @param item The item to add.
     */
    public void addItem(CustomItem item) {
        AtomicBoolean found = new AtomicBoolean();
        IntStream.range(0, inventory.getSize()).forEach(i -> {
            if (found.get() || inventory.getItem(i) != null) {
                return;  // Skip if the slot is already occupied
            }

            found.set(true);
            items[i] = item;  // Assign the item to the first empty slot
        });

        inventory.addItem(item.getItemStack());  // Add the item to the inventory
    }

    /**
     * Opens the inventory for the specified player.
     *
     * @param player The player who will see the inventory.
     */
    public void openInventory(Player player) {
        // Check if the required plugin is installed
        if (InvifyAPI.getInstance().getServer().getPluginManager().getPlugin("invifi-lib") == null) {
            player.kick(AdventureUtil.colorize("<red>Invifi-Lib not found!"));
        }

        // Open the inventory for the player
        player.openInventory(inventory);

        // Add the player to the list of players who have this inventory open
        InvifyAPI.getInstance().getOpenedInventories().computeIfAbsent(this, gui -> new HashSet<>())
                .add(player.getUniqueId());
    }

    /**
     * Called when the player closes the inventory. Currently, does nothing.
     *
     * @param player The player who closed the inventory.
     */
    public void onInventoryClose(Player player) {
        // Optionally, handle actions when the inventory is closed
    }

    /**
     * Checks if the specified player is currently viewing this inventory.
     *
     * @param player The player to check.
     * @return True if the player is viewing this inventory, false otherwise.
     */
    public boolean isViewing(Player player) {
        return InvifyAPI.getInstance().getOpenedInventories().computeIfAbsent(this, gui -> new HashSet<>())
                .contains(player.getUniqueId());
    }

    public boolean isCancelClickEvent() {
        return cancelClickEvent;
    }

    public CustomItem[] getItems() {
        return items;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public InvifiInventorySize getInvifiInventorySize() {
        return invifiInventorySize;
    }
}
