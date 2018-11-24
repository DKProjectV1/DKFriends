package ch.dkrieger.friendsystem.spigot.listener;

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.gui.GUI;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConfigInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryCloseEvent;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:46
 *
 */

public class InventoryCloseListener implements org.bukkit.event.Listener {

    @EventHandler(priority= EventPriority.HIGH)
    public void onInventoryClose(InventoryCloseEvent event){
        if(event.getInventory() == null) return;
        if(event.getInventory().getHolder() == null && event.getInventory().getHolder() instanceof GUI) ((GUI)event.getInventory().getHolder()).handleClose(event);
        final Player player = (Player)event.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), () -> {
            ConfigInventory inventory = SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(event.getInventory().getName());
            if(inventory != null) {
                for(Listener listener : inventory.getListeners(Listener.DefaultEvent.INVENTORY_CLOSE)) {
                    listener.execute(player);
                }
            }
        });
    }
}