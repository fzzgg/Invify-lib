package net.invifi.inventory.listener;

import net.invifi.inventory.api.event.ItemClickEvent;
import net.invifi.inventory.api.item.CustomItem;
import net.invifi.inventory.inventory.InvifiInventory;
import net.invifi.inventory.inventory.InvifiInventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public final class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (!(event.getWhoClicked() instanceof Player player) || !(inventory.getHolder() instanceof InvifiInventoryHolder)) return;

        InvifiInventory gui = ((InvifiInventoryHolder) inventory.getHolder()).getInvifiInventory();
        CustomItem item = gui.getItem(event.getRawSlot());

        if ((inventory.getType() != InventoryType.CHEST || (event.getRawSlot() < 0
                || event.getRawSlot() >= gui.getInventory().getSize())) && item == null) return;

        if (item == null) {
            if (!event.isCancelled()) event.setCancelled(gui.isCancelClickEvent());

            return;
        }

        ItemClickEvent itemClickEvent = new ItemClickEvent(gui, player, event.getCurrentItem(), event.getClick(), event.getSlot());
        item.onClick(itemClickEvent);

        event.setCancelled(itemClickEvent.isWillCancelEvent());
        if (itemClickEvent.isWillUpdate()) {
            gui.updateInventory();
        } else if (itemClickEvent.isWillClose()) {
            player.closeInventory();
        }
    }

}