package ch.dkrieger.friendsystem.spigot.listener;

import ch.dkrieger.friendsystem.spigot.api.inventory.GUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:46
 *
 */

public class InventoryCloseListener implements Listener {

    @EventHandler(priority= EventPriority.HIGH)
    public void onInventoryClose(InventoryCloseEvent event){
        if(event.getInventory() == null || event.getInventory().getHolder() == null) return;
        if(event.getInventory().getHolder() instanceof GUI) ((GUI)event.getInventory().getHolder()).handleClose(event);
    }
}