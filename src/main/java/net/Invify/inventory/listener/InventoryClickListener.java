package net.Invify.inventory.listener;

import net.Invify.inventory.api.event.ItemClickEvent;
import net.Invify.inventory.api.item.CustomItem;
import net.Invify.inventory.inventory.InvifyInventory;
import net.Invify.inventory.inventory.InvifyInventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public final class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (!(event.getWhoClicked() instanceof Player player) || !(inventory.getHolder() instanceof InvifyInventoryHolder invifyInventoryHolder)) return;

        InvifyInventory gui = invifyInventoryHolder.getInvifiInventory();
        if (gui == null) return;

        CustomItem item = gui.getItem(event.getRawSlot());
        Inventory invifyInventory = gui.getInventory();

        if (event.getClickedInventory() instanceof PlayerInventory && inventory.getHolder() instanceof InvifyInventoryHolder) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }

        if ((inventory.getType() != InventoryType.CHEST || (event.getRawSlot() < 0
                || event.getRawSlot() >= gui.getInventory().getSize())) && item == null) return;

        if (item == null) {
            if (!event.isCancelled()) event.setCancelled(gui.isCancelClickEvent()); return;
        }

        ItemClickEvent itemClickEvent = new ItemClickEvent(gui, player, event.getCurrentItem(), event.getClick(), event.getSlot());
        item.onClick(itemClickEvent);

        event.setCancelled(itemClickEvent.isWillCancelClickEvent());
        event.setResult(itemClickEvent.isWillCancelClickEvent() ? Event.Result.ALLOW : Event.Result.DENY);

        if (itemClickEvent.isWillUpdateInventory()) {
            gui.updateInventory();
        } else if (itemClickEvent.isWillCloseInventory()) {
            player.closeInventory();
        }
    }

}