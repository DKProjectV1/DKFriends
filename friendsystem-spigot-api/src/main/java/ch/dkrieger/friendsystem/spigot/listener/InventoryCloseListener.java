package ch.dkrieger.friendsystem.spigot.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 16.11.18 15:47
 *
 */

import ch.dkrieger.friendsystem.spigot.api.inventory.GUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler(priority= EventPriority.HIGH)
    public void onInventoryClose(InventoryCloseEvent event){
        if(event.getInventory() == null || event.getInventory().getHolder() == null) return;
        if(event.getInventory().getHolder() instanceof GUI) ((GUI)event.getInventory().getHolder()).handleClose(event);
    }
}