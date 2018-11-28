package ch.dkrieger.friendsystem.spigot.inventories.settings;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 28.11.18 13:58
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.gui.PrivateGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class FriendSettingsPage extends PrivateGUI {

    public FriendSettingsPage(Player owner) {
        super("friendSettings", owner);
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