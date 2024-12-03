package net.Invify.inventory.api.item;

import net.Invify.inventory.api.event.ItemClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class CustomItem {

    // The ItemStack representing this custom item.
    private final ItemStack itemStack;

    protected CustomItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * This method is called when the item is clicked by the player.
     * Subclasses can override this method to define custom behavior when the item is clicked.
     *
     * @param event The event representing the click action.
     */
    public void onClick(ItemClickEvent event) {
        // Default implementation is empty, can be overridden by subclasses.
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
