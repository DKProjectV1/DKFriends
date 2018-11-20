package ch.dkrieger.friendsystem.spigot.listener;

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.GUI;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.MainInventory;
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
            MainInventory mainInventory = SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(event.getInventory().getName());
            if(mainInventory != null) {
                for(Listener listener : mainInventory.getListeners()) {
                    if(listener.getEvent().equalsIgnoreCase(Listener.DefaultEvent.INVENTORY_CLOSE.getName())) {
                        if(listener.getCommandRunner() == Listener.CommandRunner.CONSOLE) Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), listener.getCommand());
                        else player.chat("/" + listener.getCommand());
                        break;
                    }
                }
            }
        });
    }
}