package ch.dkrieger.friendsystem.spigot.inventories;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 15:24
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.gui.PrivateGUI;
import com.google.gson.reflect.TypeToken;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.List;
import java.util.UUID;

public class FriendOptionsPage extends PrivateGUI {

    public FriendOptionsPage(Player owner, Friend friend) {
        super("friendOptions", (friend == null || !friend.isOnline() ? "friendOffline" : "friendOnline"), owner, friend);
        System.out.println(friend == null);
    }

    @Override
    protected void onOpen(InventoryOpenEvent event) {

    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> {
            if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            NBTItem nbtItem = new NBTItem(event.getCurrentItem());
            if(nbtItem.hasKey("listeners") && nbtItem.getString("listeners") != null) {
                List<Listener> listeners = GeneralUtil.GSON_NOT_PRETTY.fromJson(nbtItem.getString("listeners"), new TypeToken<List<Listener>>(){}.getType());
                for(Listener listener : listeners) if(listener.getEvent().equalsIgnoreCase(Listener.DefaultEvent.CLICK.getName())) {
                    if(nbtItem.hasKey("friend")) listener.execute(player, nbtItem.getString("friend"));
                    else listener.execute(player);
                }
            }
        });
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {

    }
}