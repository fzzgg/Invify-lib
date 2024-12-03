package net.invifi.inventory;

import net.invifi.inventory.api.adventure.AdventureLegacyColorPostProcessor;
import net.invifi.inventory.api.adventure.AdventureLegacyColorPreProcessor;
import net.invifi.inventory.inventory.InvifiInventory;
import net.invifi.inventory.listener.InventoryClickListener;
import net.invifi.inventory.listener.InventoryCloseListener;
import net.invifi.inventory.util.TimeUtil;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public final class InvifyAPI extends JavaPlugin {

    public static InvifyAPI instance; // Singleton instance of the plugin

    private Map<InvifiInventory, Set<UUID>> openedInventories; // Maps inventories to the list of players that have them opened

    private MiniMessage minimessage; // Minimessage instance for handling text components

    public static InvifyAPI getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;

        this.openedInventories = new ConcurrentHashMap<>(); // Initialize the map to track opened inventories
    }

    @Override
    public void onEnable() {
        // Set up the MiniMessage instance with processors and decoration
        this.minimessage = MiniMessage.builder()
                .postProcessor(new AdventureLegacyColorPostProcessor()) // Processor to handle legacy color codes
                .preProcessor(new AdventureLegacyColorPreProcessor()) // Pre-processor for legacy color codes
                .postProcessor(component -> component.decoration(TextDecoration.ITALIC, false)) // Disable italic decoration globally
                .tags(TagResolver.standard()) // Standard tag resolver
                .build();

        // Register listeners for inventory click and close events
        this.registerListeners(new InventoryClickListener(), new InventoryCloseListener());
    }

    /**
     * Schedules a repeating task.
     *
     * @param runnable Task to run.
     * @param value    Time value for the delay.
     * @param timeUnit Time unit for the delay (e.g., seconds, minutes).
     */
    public void scheduleTask(Runnable runnable, int value, TimeUnit timeUnit) {
        final long ticks = timeUnit.toSeconds(value) * 20L; // Convert time to ticks (20 ticks per second)
        getServer().getScheduler().runTaskTimerAsynchronously(this, runnable, ticks, ticks); // Schedule task asynchronously
    }

    /**
     * Runs a task synchronously (on the main server thread).
     *
     * @param runnable Task to run.
     * @return The scheduled BukkitTask.
     */
    public BukkitTask runSync(Runnable runnable) {
        return getServer().getScheduler().runTask(this, runnable); // Schedules a task to run synchronously
    }

    /**
     * Runs a task asynchronously (on a separate thread).
     *
     * @param runnable Task to run.
     * @return The scheduled BukkitTask.
     */
    public BukkitTask runAsync(Runnable runnable) {
        return getServer().getScheduler().runTaskAsynchronously(this, runnable); // Schedules a task to run asynchronously
    }

    /**
     * Runs a task with a delay, synchronously.
     *
     * @param runnable Task to run.
     * @param time     Delay time.
     * @param unit     Time unit for the delay.
     * @return The scheduled BukkitTask.
     */
    public BukkitTask runLaterSync(Runnable runnable, long time, TimeUnit unit) {
        return getServer().getScheduler().runTaskLater(this, runnable, TimeUtil.toTicks(time, unit)); // Delay task synchronously
    }

    /**
     * Runs a task with a delay, asynchronously.
     *
     * @param runnable Task to run.
     * @param time     Delay time.
     * @param unit     Time unit for the delay.
     * @return The scheduled BukkitTask.
     */
    public BukkitTask runLaterAsync(Runnable runnable, long time, TimeUnit unit) {
        return getServer().getScheduler().runTaskLaterAsynchronously(this, runnable, TimeUtil.toTicks(time, unit)); // Delay task asynchronously
    }

    /**
     * Runs a repeating task synchronously.
     *
     * @param runnable     Task to run.
     * @param initialDelay Initial delay before the first run.
     * @param periodDelay  Time interval between runs.
     * @param unit         Time unit for delay and period.
     * @return The scheduled BukkitTask.
     */
    public BukkitTask runRepeatingSync(Runnable runnable, long initialDelay, long periodDelay, TimeUnit unit) {
        return getServer().getScheduler().runTaskTimer(this, runnable, TimeUtil.toTicks(initialDelay, unit), TimeUtil.toTicks(periodDelay, unit)); // Repeating task synchronously
    }

    /**
     * Runs a repeating task asynchronously.
     *
     * @param runnable     Task to run.
     * @param initialDelay Initial delay before the first run.
     * @param periodDelay  Time interval between runs.
     * @param unit         Time unit for delay and period.
     * @return The scheduled BukkitTask.
     */
    public BukkitTask runRepeatingAsync(Runnable runnable, long initialDelay, long periodDelay, TimeUnit unit) {
        return getServer().getScheduler().runTaskTimerAsynchronously(this, runnable, TimeUtil.toTicks(initialDelay, unit), TimeUtil.toTicks(periodDelay, unit)); // Repeating task asynchronously
    }

    /**
     * Registers a single event listener.
     *
     * @param listener The listener to register.
     */
    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this); // Register the listener with the plugin manager
    }

    /**
     * Registers multiple event listeners.
     *
     * @param listeners The listeners to register.
     */
    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            registerListener(listener); // Register each listener
        }
    }

    /**
     * Registers multiple event listeners from a set.
     *
     * @param listeners The listeners to register.
     */
    public void registerListeners(Set<Listener> listeners) {
        for (Listener listener : listeners) {
            registerListener(listener); // Register each listener
        }
    }

    public Map<InvifiInventory, Set<UUID>> getOpenedInventories() {
        return openedInventories;
    }

    public MiniMessage getMinimessage() {
        return minimessage;
    }
}
