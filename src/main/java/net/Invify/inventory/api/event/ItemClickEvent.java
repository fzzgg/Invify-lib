package net.Invify.inventory.api.event;

import lombok.Getter;
import lombok.Setter;
import net.Invify.inventory.inventory.InvifyInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public final class ItemClickEvent {

    private final InvifyInventory inventory;
    private final Player player;
    private final ItemStack itemStack;
    private final ClickType clickType;
    private final int slot;

    private boolean willCancelClickEvent = true;
    private boolean willUpdateInventory = true;
    private boolean willCloseInventory = false;

    public ItemClickEvent(InvifyInventory inventory, Player player, ItemStack itemStack, ClickType clickType, int slot) {
        this.inventory = inventory;
        this.player = player;
        this.itemStack = itemStack;
        this.clickType = clickType;
        this.slot = slot;
    }

    @SuppressWarnings("unchecked")
    public <T extends InvifyInventory> T getInventory() {
        return (T) inventory;
    }

}