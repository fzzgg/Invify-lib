package net.invifi.inventory.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public final class InvifiInventoryHolder implements InventoryHolder {

    private final InvifiInventory invifiInventory;

    public InvifiInventoryHolder(InvifiInventory invifiInventory) {
        this.invifiInventory = invifiInventory;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return invifiInventory.getInventory();
    }

    public InvifiInventory getInvifiInventory() {
        return invifiInventory;
    }
}
