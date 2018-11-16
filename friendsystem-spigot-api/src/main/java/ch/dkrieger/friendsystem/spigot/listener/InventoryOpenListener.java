package ch.dkrieger.friendsystem.spigot.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 16.11.18 15:51
 *
 */

import ch.dkrieger.friendsystem.spigot.api.inventory.GUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryOpenListener implements Listener {

    @EventHandler(priority= EventPriority.HIGH)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if(event.getInventory() == null || event.getInventory().getHolder() == null) return;
        if(event.getInventory().getHolder() instanceof GUI) ((GUI)event.getInventory().getHolder()).handleOpen(event);
    }
}