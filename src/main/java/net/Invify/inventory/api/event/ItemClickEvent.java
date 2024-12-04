package net.Invify.inventory.api.event;

import net.Invify.inventory.inventory.InvifyInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public final class ItemClickEvent {

    private final InvifyInventory inventory;
    private final Player player;
    private final ItemStack itemStack;
    private final ClickType clickType;
    private final int slot;

    private boolean willCancelEvent = true;
    private boolean willUpdate = true;
    private boolean willClose = false;

    public ItemClickEvent(InvifyInventory inventory, Player player, ItemStack itemStack, ClickType clickType, int slot) {
        this.inventory = inventory;
        this.player = player;
        this.itemStack = itemStack;
        this.clickType = clickType;
        this.slot = slot;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ClickType getClickType() {
        return clickType;
    }

    public int getSlot() {
        return slot;
    }

    public boolean isWillCancelEvent() {
        return willCancelEvent;
    }

    public void setWillCancelEvent(boolean willCancelEvent) {
        this.willCancelEvent = willCancelEvent;
    }

    public boolean isWillUpdate() {
        return willUpdate;
    }

    public void setWillUpdate(boolean willUpdate) {
        this.willUpdate = willUpdate;
    }

    public boolean isWillClose() {
        return willClose;
    }

    public void setWillClose(boolean willClose) {
        this.willClose = willClose;
    }

    @SuppressWarnings("unchecked")
    public <T extends InvifyInventory> T getInventory() {
        return (T) inventory;
    }

}