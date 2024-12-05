package net.Invify.inventory.inventory.type;

import lombok.Getter;
import net.Invify.inventory.InvifyAPI;
import net.Invify.inventory.api.item.CustomItem;
import net.Invify.inventory.api.size.InvifyInventorySize;
import net.Invify.inventory.inventory.InvifyInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

@Getter
public abstract class RefreshableInventory<T extends InvifyAPI> extends InvifyInventory {

    // The plugin instance that will be used to run tasks
    protected final T plugin;

    // Delay between inventory updates in milliseconds
    private final long delay;

    // Bukkit task used to schedule the inventory updates
    private BukkitTask updateTask;

    /**
     * Constructor that initializes the inventory with a plugin and a delay.
     *
     * @param plugin The plugin instance (used to run repeating tasks)
     * @param delay  The delay between inventory updates in milliseconds
     */
    public RefreshableInventory(T plugin, long delay) {
        super();
        this.plugin = plugin;
        this.delay = delay;
    }

    /**
     * Constructor that initializes the inventory with a plugin, display name, size, and delay.
     *
     * @param plugin      The plugin instance (used to run repeating tasks)
     * @param displayName The name displayed for the inventory
     * @param size        The size of the inventory
     * @param delay       The delay between inventory updates in milliseconds
     */
    public RefreshableInventory(T plugin, String displayName, InvifyInventorySize size, long delay) {
        super(size, displayName);
        this.plugin = plugin;
        this.delay = delay;
    }

    /**
     * Abstract method to perform a soft update on the inventory for a player.
     * This method will be implemented by the subclass to define how the inventory is updated.
     *
     * @param player The player whose inventory will be updated
     */
    protected abstract void softUpdate(Player player);

    /**
     * Opens the inventory for the player and starts the repeating task to update it periodically.
     *
     * @param player The player who is opening the inventory
     */
    @Override
    public void openInventory(Player player) {
        super.openInventory(player);
        // Start a repeating task to update the inventory at the specified interval (delay)
        this.updateTask = this.plugin.runRepeatingSync(() -> {
            if (player == null || !player.isOnline()) {
                return; // If player is null or not online, stop the update task
            }
            softUpdate(player); // Perform a soft update on the player's inventory
        }, 1L, this.delay, TimeUnit.MILLISECONDS); // Delay is set in milliseconds
    }

    /**
     * Cancels the update task when the inventory is closed.
     *
     * @param player The player who closed the inventory
     */
    @Override
    public void onInventoryClose(Player player) {
        // Cancel the repeating task if it exists when the inventory is closed
        if (updateTask != null) this.updateTask.cancel();
    }

    @Override
    public Inventory getInventory() {
        return super.getInventory();
    }

    @Override
    public CustomItem getItem(int slot) {
        return super.getItem(slot);
    }

    @Override
    public CustomItem[] getItems() {
        return super.getItems();
    }

    @Override
    public InvifyInventorySize getInvifiInventorySize() {
        return super.getInvifiInventorySize();
    }

    @Override
    public void setItem(int slot, CustomItem item) {
        super.setItem(slot, item);
    }
}
