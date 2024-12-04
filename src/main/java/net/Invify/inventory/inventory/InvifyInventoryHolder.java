package net.Invify.inventory.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public final class InvifyInventoryHolder implements InventoryHolder {

    private final InvifyInventory invifyInventory;

    public InvifyInventoryHolder(InvifyInventory invifyInventory) {
        this.invifyInventory = invifyInventory;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return invifyInventory.getInventory();
    }

    public InvifyInventory getInvifiInventory() {
        return invifyInventory;
    }
}
