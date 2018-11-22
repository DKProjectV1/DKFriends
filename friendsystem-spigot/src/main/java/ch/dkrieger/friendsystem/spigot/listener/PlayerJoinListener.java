package ch.dkrieger.friendsystem.spigot.listener;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 15:40
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.inventories.FriendPage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().createProfile(event.getPlayer());
        Bukkit.getScheduler().runTaskLater(SpigotFriendSystemBootstrap.getInstance(), ()-> {
            SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getProfile(event.getPlayer()).getFriendPage().open();
        }, 20*5);
    }
}