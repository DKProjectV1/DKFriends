package ch.dkrieger.friendsystem.spigot.listener;

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.GUI;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConditionInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.Inventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.itemstack.ItemStack;
import ch.dkrieger.friendsystem.spigot.api.inventory.itemstack.ItemStackListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:46
 *
 */

public class InventoryClickListener implements Listener {

    @EventHandler(priority= EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player)) return;
        if(event.getInventory() == null) return;
        if(event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof GUI) ((GUI)event.getInventory().getHolder()).handleClick(event);
        final Player player = (Player)event.getWhoClicked();
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), () -> {
            Inventory inventory = SpigotFriendSystemBootstrap.getInstance().getInventory(event.getInventory().getName());
            if(inventory != null) {
                ItemStack itemStack = inventory.getItem(event.getCurrentItem());
                if(itemStack != null) {
                    for(Map.Entry<String, ItemStackListener> entry : itemStack.getListeners().entrySet()) {
                        if(entry.getKey().equals("click")) {
                            ItemStackListener listener = entry.getValue();
                            if(listener.getConsoleSender() == ItemStackListener.ConsoleSender.CONSOLE) Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), listener.getCommand());
                            else player.chat("/" + listener.getCommand());
                            break;
                        }
                    }
                }
                for(ConditionInventory conditionInventory : inventory.getConditionInventories().values()) {
                    ItemStack itemStack1 = conditionInventory.getItem(event.getCurrentItem());
                    if(itemStack1 != null) {
                        for(Map.Entry<String, ItemStackListener> entry : itemStack1.getListeners().entrySet()) {
                            if(entry.getKey().equals("click")) {
                                ItemStackListener listener = entry.getValue();
                                if(listener.getConsoleSender() == ItemStackListener.ConsoleSender.CONSOLE) Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), listener.getCommand());
                                else player.chat("/" + listener.getCommand());
                                break;
                            }
                        }
                    }
                }
            }
        });
    }
}