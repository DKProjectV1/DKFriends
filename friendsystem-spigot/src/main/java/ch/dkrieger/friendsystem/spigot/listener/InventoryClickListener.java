package ch.dkrieger.friendsystem.spigot.listener;

import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.gui.GUI;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConfigInventory;
import com.google.gson.reflect.TypeToken;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:46
 *
 */

public class InventoryClickListener implements org.bukkit.event.Listener {

    @EventHandler(priority= EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) return;
        if(event.getInventory() == null) return;
        if(event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof GUI) ((GUI)event.getInventory().getHolder()).handleClick(event);
        final Player player = (Player)event.getWhoClicked();
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), () -> {
            ConfigInventory inventory = SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(event.getInventory().getName());
            if(inventory != null) {
                if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
                NBTItem nbtItem = new NBTItem(event.getCurrentItem());
                if(nbtItem.hasKey("listeners") && nbtItem.getString("listeners") != null) {
                    List<Listener> listeners = GeneralUtil.GSON_NOT_PRETTY.fromJson(nbtItem.getString("listeners"), new TypeToken<List<Listener>>(){}.getType());
                    for(Listener listener : listeners) if(listener.getEvent().equalsIgnoreCase(Listener.DefaultEvent.CLICK.getName())) {
                        if(nbtItem.hasKey("friend")) listener.execute(player, nbtItem.getString("friend"));
                        else listener.execute(player);
                    }
                }
            }
        });
    }
}