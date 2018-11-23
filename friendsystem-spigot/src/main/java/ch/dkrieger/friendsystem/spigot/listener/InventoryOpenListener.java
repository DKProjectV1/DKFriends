package ch.dkrieger.friendsystem.spigot.listener;

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import ch.dkrieger.friendsystem.spigot.api.inventory.GUI;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.MainInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryOpenEvent;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:46
 *
 */

public class InventoryOpenListener implements org.bukkit.event.Listener {

    @EventHandler(priority= EventPriority.HIGH)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if(event.getInventory() == null) return;
        if(event.getInventory().getHolder() == null && event.getInventory().getHolder() instanceof GUI) ((GUI)event.getInventory().getHolder()).handleOpen(event);
        final Player player = (Player)event.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), () -> {
            MainInventory mainInventory = SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(event.getInventory().getName());
            if(mainInventory != null) {
                for(Listener listener : mainInventory.getListeners()) {
                    if(listener.getEvent().equalsIgnoreCase(Listener.DefaultEvent.CLICK.getName())) {
                        if(listener.getCommand() != null && listener.getCommandRunner() != null) {
                            if(listener.getCommandRunner() == Listener.CommandRunner.CONSOLE) Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), listener.getCommand());
                            else player.chat("/" + listener.getCommand());
                        }
                        if(listener.getAdapter() != null) {
                            Adapter adapter = SpigotFriendSystemBootstrap.getInstance().getAdapter(listener.getAdapter());
                            if(adapter instanceof FriendAdapter) ((FriendAdapter) adapter).execute(player);
                        }
                        return;
                    }
                }
            }
        });
    }
}