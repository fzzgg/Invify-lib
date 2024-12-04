package net.Invify.inventory.inventory.type;

import net.Invify.inventory.api.item.CustomItem;
import net.Invify.inventory.api.size.InvifyInventorySize;
import net.Invify.inventory.inventory.InvifyInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class SharedInventory extends InvifyInventory {

    // A set of all players who have this GUI open
    private final Set<Player> openPlayers;
    private final Inventory inventory;

    // An array of locks for synchronizing access to slots
    private final Lock[] slotLocks;

    public SharedInventory(String displayName, InvifyInventorySize size) {
        super(size, displayName);
        this.openPlayers = new HashSet<>();
        this.inventory = this.getInventory();

        // Creating an array of locks, one for each slot in the GUI
        this.slotLocks = new Lock[size.getSize()];
        for (int i = 0; i < size.getSize(); i++) {
            this.slotLocks[i] = new ReentrantLock();
        }
    }

    /**
     * Opens the shared GUI for a player.
     *
     * @param player The player for whom the GUI will be opened.
     */
    @Override
    public void openInventory(Player player) {
        super.openInventory(player);
        this.openPlayers.add(player);  // Add the player to the list of players who have opened the GUI.
        syncInventoryForAllPlayers();  // Synchronize the inventory state for all players.
    }

    /**
     * Synchronizes the content of the GUI for all players.
     */
    private void syncInventoryForAllPlayers() {
        for (Player player : this.openPlayers) {
            if (player.isOnline()) {
                updateInventoryForPlayer(player);  // Update the GUI content for each player.
            }
        }
    }

    /**
     * Updates the content of the GUI for a specific player.
     *
     * @param player The player whose GUI is being updated.
     */
    private void updateInventoryForPlayer(Player player) {
        Inventory inv = player.getOpenInventory().getTopInventory();
        inv.setContents(this.inventory.getContents());  // Synchronize the content of the GUI with the current state.
    }

    /**
     * Adds an item to the shared GUI and synchronizes the changes for all players.
     *
     * @param item The item to be added.
     */
    public void addItem(CustomItem item) {
        this.inventory.addItem(item.getItemStack());
        syncInventoryForAllPlayers();  // Synchronize the changes for all players.
    }

    /**
     * Removes an item from the GUI and synchronizes the changes.
     *
     * @param item The item to be removed.
     */
    public void removeItem(CustomItem item) {
        ItemStack[] items = inventory.getContents();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].isSimilar(item.getItemStack())) {
                inventory.clear(i);  // Remove the item from the given slot.
                break;
            }
        }
        syncInventoryForAllPlayers();  // Synchronize the changes for all players.
    }

    /**
     * A player attempts to take an item from the GUI.
     *
     * @param player The player who is attempting to take the item.
     * @param slot   The slot from which the item will be taken.
     */
    public void takeItem(Player player, int slot) {
        if (inventory.getItem(slot) != null) {
            slotLocks[slot].lock();  // Lock the slot to prevent other players from accessing it simultaneously
            try {
                if (inventory.getItem(slot) != null) {
                    inventory.clear(slot);  // Remove the item from the GUI
                    syncInventoryForAllPlayers();  // Synchronize the changes for all players.
                }
            } finally {
                slotLocks[slot].unlock();  // Release the lock, allowing other players to update the slot
            }
        }
    }

    /**
     * Closes the GUI for a player and removes them from the list of players who have opened the GUI.
     *
     * @param player The player who is closing the GUI.
     */
    @Override
    public void onInventoryClose(Player player) {
        openPlayers.remove(player);  // Remove the player from the list of players who have opened the GUI.
    }

    @Override
    public InvifyInventorySize getInvifiInventorySize() {
        return super.getInvifiInventorySize();
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public CustomItem getItem(int slot) {
        return super.getItem(slot);
    }

    @Override
    public CustomItem[] getItems() {
        return super.getItems();
    }

    public Lock[] getSlotLocks() {
        return slotLocks;
    }

    public Set<Player> getOpenPlayers() {
        return openPlayers;
    }

    @Override
    public void setItem(int slot, CustomItem item) {
        super.setItem(slot, item);
    }
}
