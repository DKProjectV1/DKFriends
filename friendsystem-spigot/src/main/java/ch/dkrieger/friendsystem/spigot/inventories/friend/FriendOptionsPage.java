package ch.dkrieger.friendsystem.spigot.inventories.friend;

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

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 28.11.18 13:56
 *
 */

public class FriendOptionsPage extends PrivateGUI {

    public FriendOptionsPage(Player owner, Friend friend) {
        super("friendOptions", owner, friend, (friend == null || !friend.isOnline() ? "friendOffline" : "friendOnline"));
    }

    @Override
    protected void onOpen(InventoryOpenEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> Listener.execute(Listener.DefaultEvent.INVENTORY_OPEN, (Player) event.getPlayer(), getConfigInventory()));
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> Listener.execute(Listener.DefaultEvent.CLICK, (Player) event.getWhoClicked(), event.getCurrentItem()));
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> Listener.execute(Listener.DefaultEvent.INVENTORY_CLOSE, (Player) event.getPlayer(), getConfigInventory()));
    }
}