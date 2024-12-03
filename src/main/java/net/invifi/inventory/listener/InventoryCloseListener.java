package net.invifi.inventory.listener;

import net.invifi.inventory.InvifiAPI;
import net.invifi.inventory.inventory.InvifiInventory;
import net.invifi.inventory.inventory.InvifiInventoryHolder;
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
        Set<UUID> viewers = InvifiAPI.getInstance().getOpenedInventories().get(gameHostGui);
        if (viewers == null) return;

        viewers.remove(player.getUniqueId());
        gameHostGui.onInventoryClose(player);
    }

}