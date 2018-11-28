package ch.dkrieger.friendsystem.spigot.inventories.settings;

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.gui.PrivateGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 28.11.18 13:56
 *
 */

public class SettingsPage extends PrivateGUI {

    public SettingsPage(Player owner) {
        super("settings", owner);
        for(Map.Entry<Integer, ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack> entry : SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getDefaultInventoryItems().entrySet()) {
            getInventory().setItem(entry.getKey(), entry.getValue().toBukkitItemStack());
        }
    }

    @Override
    protected void onOpen(InventoryOpenEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> Listener.execute(Listener.DefaultEvent.INVENTORY_OPEN, (Player) event.getPlayer(), getConfigInventory()));
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> Listener.execute(Listener.DefaultEvent.CLICK, (Player) event.getWhoClicked(), event.getCurrentItem()));
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> Listener.execute(Listener.DefaultEvent.INVENTORY_CLOSE, (Player) event.getPlayer(), getConfigInventory()));
    }
}