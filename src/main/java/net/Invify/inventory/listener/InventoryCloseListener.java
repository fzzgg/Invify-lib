package net.Invify.inventory.listener;

import net.Invify.inventory.InvifyAPI;
import net.Invify.inventory.inventory.InvifiInventory;
import net.Invify.inventory.inventory.InvifiInventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Set;
import java.util.UUID;

public final class InventoryCloseListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player) || !(event.getInventory().getHolder() instanceof InvifiInventoryHolder guiHolder)) return;

        InvifiInventory gameHostGui = guiHolder.getInvifiInventory();
        Set<UUID> viewers = InvifyAPI.getInstance().getOpenedInventories().get(gameHostGui);
        if (viewers == null) return;

        viewers.remove(player.getUniqueId());
        gameHostGui.onInventoryClose(player);
    }

}