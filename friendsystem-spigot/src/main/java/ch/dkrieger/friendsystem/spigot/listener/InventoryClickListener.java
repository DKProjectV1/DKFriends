package ch.dkrieger.friendsystem.spigot.listener;

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.GUI;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConditionInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.MainInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.itemstack.ItemStack;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:46
 *
 */

public class InventoryClickListener implements org.bukkit.event.Listener {

    @EventHandler(priority= EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player)) return;
        if(event.getInventory() == null) return;
        if(event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof GUI) ((GUI)event.getInventory().getHolder()).handleClick(event);
        final Player player = (Player)event.getWhoClicked();
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), () -> {
            MainInventory mainInventory = SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(event.getInventory().getName());
            if(mainInventory != null) {
                for(ConditionInventory conditionInventory : mainInventory.getConditionInventories()) {
                    ItemStack itemStack1 = conditionInventory.getItem(event.getCurrentItem());
                    if(itemStack1 != null) {
                        for(Listener listener : itemStack1.getListeners()) {
                            if(listener.getEvent().equalsIgnoreCase(Listener.DefaultEvent.CLICK.getName())) {
                                if(listener.getCommandRunner() == Listener.CommandRunner.CONSOLE) Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), listener.getCommand());
                                else player.chat("/" + listener.getCommand());
                                return;
                            }
                        }
                    }
                }
                ItemStack itemStack = mainInventory.getItem(event.getCurrentItem());
                if(itemStack != null) {
                    for(Listener listener : itemStack.getListeners()) {
                        if(listener.getEvent().equalsIgnoreCase(Listener.DefaultEvent.CLICK.getName())) {
                            if(listener.getCommandRunner() == Listener.CommandRunner.CONSOLE) Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), listener.getCommand());
                            else player.chat("/" + listener.getCommand());
                            break;
                        }
                    }
                }
            }
        });
    }
}