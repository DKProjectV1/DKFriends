package ch.dkrieger.friendsystem.spigot.listener;

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.event.*;
import ch.dkrieger.friendsystem.spigot.inventories.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.function.BiConsumer;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 26.11.18 17:51
 *
 */

public class FriendListener implements Listener {

    @EventHandler
    public void onBukkitFriendPlayerColorSet(BukkitFriendPlayerColorSetEvent event) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().updateFriendPages();
    }

    @EventHandler
    public void onBukkitFriendPlayerLogout(BukkitFriendPlayerLogoutEvent event) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().updateFriendPages();
    }

    @EventHandler
    public void onBukkitFriendPlayerUpdate(BukkitFriendPlayerUpdateEvent event) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().updateFriendPages();
    }

    @EventHandler
    public void onBukkitOnlineFriendPlayerUpdate(BukkitOnlineFriendPlayerUpdateEvent event) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().updateFriendPages();
    }

    @EventHandler
    public void onBukkitPartyUpdate(BukkitPartyUpdateEvent event) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().updatePartyPages();
    }
}